# Sound Effects & Haptic Feedback Implementation Guide

## 📚 Overview
Panduan lengkap implementasi SFX dan haptic feedback untuk Hero Quest mini-games. Includes step-by-step code, asset sourcing, dan performance optimization.

---

## 🔊 Part 1: Audio Implementation (SFX)

### 1.1 Setup Folder Structure

```
app/
├── src/main/res/
│   ├── raw/ (NEW FOLDER)
│   │   ├── sfx_correct.wav      (200ms, beep/ding sound)
│   │   ├── sfx_wrong.wav        (150ms, buzz/negative sound)
│   │   ├── sfx_combo.wav        (300ms, chime/bell sound)
│   │   ├── sfx_game_over.wav    (500ms, dramatic sound)
│   │   ├── sfx_click.wav        (100ms, UI tap feedback)
│   │   └── sfx_success.wav      (250ms, victory/unlock sound - optional)
│   └── ...
└── ...
```

### 1.2 Create SoundManager Utility

**File**: `app/src/main/java/com/example/luminasdgs/utils/SoundManager.kt`

```kotlin
package com.example.luminasdgs.utils

import android.content.Context
import android.media.MediaPlayer
import android.util.Log

/**
 * Singleton manager untuk playback SFX dengan pooling & lifecycle management.
 * 
 * Features:
 * - Cache MediaPlayer instances untuk performa
 * - Volume control
 * - Async cleanup untuk avoid resource leak
 * - Test-friendly (dapat di-mock)
 */
class SoundManager(private val context: Context) {
    
    private val mediaPlayers = mutableMapOf<String, MediaPlayer?>()
    private var isSoundEnabled = true
    
    companion object {
        private var instance: SoundManager? = null
        
        fun getInstance(context: Context): SoundManager {
            return instance ?: synchronized(this) {
                instance ?: SoundManager(context.applicationContext).also { instance = it }
            }
        }
    }
    
    enum class SFXType(val resId: Int, val duration: Long) {
        CORRECT(com.example.luminasdgs.R.raw.sfx_correct, 200),
        WRONG(com.example.luminasdgs.R.raw.sfx_wrong, 150),
        COMBO(com.example.luminasdgs.R.raw.sfx_combo, 300),
        GAME_OVER(com.example.luminasdgs.R.raw.sfx_game_over, 500),
        CLICK(com.example.luminasdgs.R.raw.sfx_click, 100),
        SUCCESS(com.example.luminasdgs.R.raw.sfx_success, 250)
    }
    
    /**
     * Main playback method.
     * 
     * Usage:
     *   SoundManager.getInstance(context).playSound(SFXType.CORRECT)
     * 
     * Thread-safe: dapat dipanggil dari UI thread atau background.
     * Non-blocking: async cleanup tidak memblok caller.
     */
    fun playSound(type: SFXType, volume: Float = 1f) {
        if (!isSoundEnabled) return
        
        try {
            // Cleanup old player jika ada
            mediaPlayers[type.name]?.release()
            
            // Create new player dari raw resource
            val player = MediaPlayer.create(context, type.resId)
            player?.apply {
                setVolume(volume, volume) // Left & right channel
                start()
                
                // Auto-cleanup setelah selesai (async)
                setOnCompletionListener {
                    release()
                    mediaPlayers[type.name] = null
                }
            }
            
            mediaPlayers[type.name] = player
            
        } catch (e: Exception) {
            Log.e("SoundManager", "Error playing sound ${type.name}: ${e.message}")
        }
    }
    
    /**
     * Set master sound toggle.
     * 
     * Usage:
     *   SoundManager.getInstance(context).setSoundEnabled(false)
     */
    fun setSoundEnabled(enabled: Boolean) {
        isSoundEnabled = enabled
    }
    
    /**
     * Get current sound state.
     */
    fun isSoundEnabled(): Boolean = isSoundEnabled
    
    /**
     * Release semua resources (call di Activity.onDestroy).
     */
    fun release() {
        mediaPlayers.values.forEach { it?.release() }
        mediaPlayers.clear()
    }
}
```

### 1.3 Create HapticManager Utility

**File**: `app/src/main/java/com/example/luminasdgs/utils/HapticManager.kt`

```kotlin
package com.example.luminasdgs.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresApi

/**
 * Haptic feedback manager untuk Android 26+.
 * 
 * API Level Support:
 * - API 26-30: VibrationEffect dengan Vibrator
 * - API 31+: VibratorManager (preferred)
 * 
 * Usage:
 *   HapticManager.getInstance(context).pulse(HapticPattern.CORRECT)
 */
class HapticManager(private val context: Context) {
    
    private val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        // API 31+: VibratorManager
        (context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager)?.defaultVibrator
    } else {
        // API 26-30: Vibrator
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }
    
    private var isHapticEnabled = true
    
    companion object {
        private var instance: HapticManager? = null
        
        fun getInstance(context: Context): HapticManager {
            return instance ?: synchronized(this) {
                instance ?: HapticManager(context.applicationContext).also { instance = it }
            }
        }
    }
    
    enum class HapticPattern(val duration: Long, val amplitude: Int) {
        // duration in ms, amplitude 0-255 (0=off, 255=max)
        CORRECT(50, 200),          // Single short pulse, medium strength
        WRONG(30, 150),            // Quick buzz (usually 2x rapid via pattern)
        COMBO(100, 220),           // Longer pulse for success
        GAME_OVER(200, 100),       // Slow rumble
        CLICK(20, 100)             // Minimal UI feedback
    }
    
    /**
     * Play single pulse haptic.
     * 
     * Usage:
     *   HapticManager.getInstance(context).pulse(HapticPattern.CORRECT)
     */
    fun pulse(pattern: HapticPattern) {
        if (!isHapticEnabled || vibrator == null || !vibrator.hasVibrator()) return
        
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val effect = VibrationEffect.createOneShot(
                    pattern.duration,
                    pattern.amplitude
                )
                vibrator.vibrate(effect)
            } else {
                // Fallback untuk API < 26 (deprecated tapi work)
                @Suppress("DEPRECATION")
                vibrator.vibrate(pattern.duration)
            }
        } catch (e: Exception) {
            // Device tidak support vibration, silent fail
        }
    }
    
    /**
     * Play pattern: multiple pulses untuk dramatic effect.
     * 
     * Contoh: WRONG dengan 2x rapid pulse
     * Timings: [delay, duration, delay, duration, ...]
     * 
     * Usage:
     *   HapticManager.getInstance(context).pattern(longArrayOf(0, 30, 50, 30))
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun pattern(timings: LongArray) {
        if (!isHapticEnabled || vibrator == null || !vibrator.hasVibrator()) return
        
        try {
            val amplitudes = IntArray(timings.size) { if (it % 2 == 0) 0 else 180 }
            val effect = VibrationEffect.createWaveform(timings, amplitudes)
            vibrator.vibrate(effect)
        } catch (e: Exception) {
            // Silent fail
        }
    }
    
    fun setHapticEnabled(enabled: Boolean) {
        isHapticEnabled = enabled
    }
    
    fun isHapticEnabled(): Boolean = isHapticEnabled
}
```

### 1.4 Integration into Game Screens

**Example: Update QuizScreen.kt**

```kotlin
// Add to imports
import com.example.luminasdgs.utils.SoundManager
import com.example.luminasdgs.utils.HapticManager

// Inside QuizScreen @Composable function
@Composable
fun QuizScreen(navController: NavController, viewModel: QuizViewModel = viewModel()) {
    val context = LocalContext.current
    val soundManager = remember { SoundManager.getInstance(context) }
    val hapticManager = remember { HapticManager.getInstance(context) }
    
    // ... existing code ...
    
    // Saat jawaban benar
    LaunchedEffect(viewModel.isLastAnswerCorrect) {
        if (viewModel.isLastAnswerCorrect == true) {
            soundManager.playSound(SoundManager.SFXType.CORRECT, volume = 0.8f)
            hapticManager.pulse(HapticManager.HapticPattern.CORRECT)
        } else if (viewModel.isLastAnswerCorrect == false) {
            soundManager.playSound(SoundManager.SFXType.WRONG, volume = 0.7f)
            hapticManager.pattern(longArrayOf(0, 30, 50, 30)) // 2x pulse
        }
    }
    
    // Saat game over
    if (viewModel.isGameOver) {
        LaunchedEffect(Unit) {
            soundManager.playSound(SoundManager.SFXType.GAME_OVER, volume = 1f)
            hapticManager.pulse(HapticManager.HapticPattern.GAME_OVER)
        }
    }
    
    // Saat UI click (tombol)
    Button(
        onClick = {
            soundManager.playSound(SoundManager.SFXType.CLICK, volume = 0.5f)
            hapticManager.pulse(HapticManager.HapticPattern.CLICK)
            // action...
        }
    ) {
        Text("Next Question")
    }
}

// Cleanup saat screen disposed
DisposableEffect(Unit) {
    onDispose {
        soundManager.release()
    }
}
```

### 1.5 Add to AndroidManifest.xml

```xml
<manifest ...>
    <!-- Permission untuk vibration -->
    <uses-permission android:name="android.permission.VIBRATE" />
    
    <!-- Optional: untuk battery optimization (API 33+) -->
    <uses-permission android:name="android.permission.ACCESS_NOTIFICATION_POLICY" />
    
    <application ...>
        ...
    </application>
</manifest>
```

---

## 🎵 Part 2: Asset Sourcing & Licensing

### 2.1 Where to Get SFX (Free & Licensed)

| Source | License | Quality | Notes |
|--------|---------|---------|-------|
| **Freesound.org** | CC0/CC-BY | High | Attribution required for CC-BY |
| **Zapsplat.com** | Free (personal use) | High | No attribution needed |
| **OpenGameArt.org** | CC0/CC-BY/CC-BY-SA | Varied | Open source audio |
| **Pixabay.com** | CC0 | Medium | Simple, clean sounds |
| **Free-Loops.com** | Free | Low-Medium | Retro game sounds |

### 2.2 Recommended SFX Specification

```
Format: WAV (PCM, 44.1kHz, 16-bit mono)
Size: Keep < 100KB per file for fast load
Duration:
  - CORRECT: 200ms
  - WRONG: 150ms
  - COMBO: 300ms
  - GAME_OVER: 500ms
  - CLICK: 100ms

Volume Normalization:
  - Peak: -3dB (leave headroom)
  - Avoid clipping
  - RMS around -15dB to -12dB
```

### 2.3 Audio Processing Workflow

1. **Download** SFX dari sumber licensed
2. **Edit** di Audacity (free):
   - Trim silence
   - Normalize volume (-3dB peak)
   - Export as WAV 44.1kHz, 16-bit, mono
3. **Optimize** di FFmpeg:
   ```bash
   ffmpeg -i input.wav -af "aformat=s16:44100" -c:a pcm_s16le output.wav
   ```
4. **Place** di `app/src/main/res/raw/`
5. **Verify** file size < 100KB total per game

---

## 🎮 Part 3: Settings Integration

### 3.1 Add Audio Settings to ProfileScreen

**Update ProfileScreen.kt**:

```kotlin
// Add at bottom of ProfileScreen LazyColumn

item {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "Audio & Haptic",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        
        val soundManager = remember { SoundManager.getInstance(context) }
        val hapticManager = remember { HapticManager.getInstance(context) }
        
        var soundEnabled by remember { mutableStateOf(soundManager.isSoundEnabled()) }
        var hapticEnabled by remember { mutableStateOf(hapticManager.isHapticEnabled()) }
        
        SettingsRow(
            label = "Sound Effects",
            isEnabled = soundEnabled,
            onToggle = {
                soundEnabled = it
                soundManager.setSoundEnabled(it)
                // Save to SharedPreferences
                context.getSharedPreferences("settings", Context.MODE_PRIVATE)
                    .edit().putBoolean("sound_enabled", it).apply()
            }
        )
        
        SettingsRow(
            label = "Haptic Feedback",
            isEnabled = hapticEnabled,
            onToggle = {
                hapticEnabled = it
                hapticManager.setHapticEnabled(it)
                // Save to SharedPreferences
                context.getSharedPreferences("settings", Context.MODE_PRIVATE)
                    .edit().putBoolean("haptic_enabled", it).apply()
            }
        )
    }
}
```

### 3.2 Create SettingsRow Composable

**New file: `ui/components/SettingsRow.kt`**:

```kotlin
@Composable
fun SettingsRow(
    label: String,
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onToggle(!isEnabled) }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        Switch(checked = isEnabled, onCheckedChange = onToggle)
    }
}
```

---

## ⚡ Part 4: Performance Optimization

### 4.1 Best Practices

```kotlin
// ✅ DO: Play sound in background/async
LaunchedEffect(event) {
    soundManager.playSound(SoundManager.SFXType.CORRECT) // Non-blocking
}

// ❌ DON'T: Block UI thread
Button(onClick = {
    // This is fine (happens in coroutine context)
    soundManager.playSound(SoundManager.SFXType.CLICK)
})

// ✅ DO: Cleanup on dispose
DisposableEffect(Unit) {
    onDispose { soundManager.release() }
}

// ❌ DON'T: Leave players active indefinitely
// (will leak memory)
```

### 4.2 Memory Management

```kotlin
// SoundManager internally handles:
// 1. MediaPlayer pooling (reuse instances)
// 2. Auto-release after playback completes
// 3. Release all on explicit call

// Ensure cleanup in Activity/Screen lifecycle
class GameActivity : AppCompatActivity() {
    private val soundManager by lazy { SoundManager.getInstance(this) }
    
    override fun onDestroy() {
        soundManager.release() // Critical!
        super.onDestroy()
    }
}
```

### 4.3 Device Compatibility

```kotlin
// Android versions supported:
// - SoundManager: API 16+ (all versions)
// - HapticManager: API 26+ for best experience
//   (API < 26 fallback exists but deprecated)

// Device checks:
fun supportsHaptic(context: Context): Boolean {
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        (context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager)?.defaultVibrator
    } else {
        context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }
    return vibrator?.hasVibrator() == true
}
```

---

## 🧪 Part 5: Testing

### 5.1 Manual Testing Checklist

- [ ] Play SFX on game event (correct, wrong, combo, game over)
- [ ] Haptic pulse on event (correct, wrong)
- [ ] Settings toggle ON/OFF works
- [ ] Sound persists after app minimize/resume
- [ ] No memory leak after 50+ sound plays
- [ ] Works on low-end device (API 28, 2GB RAM)
- [ ] No ANR (App Not Responding) on rapid clicks

### 5.2 Unit Test Example

```kotlin
@RunWith(AndroidJUnit4::class)
class SoundManagerTest {
    
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    
    private lateinit var context: Context
    private lateinit var soundManager: SoundManager
    
    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        soundManager = SoundManager.getInstance(context)
    }
    
    @Test
    fun testPlaySound() {
        soundManager.playSound(SoundManager.SFXType.CORRECT)
        // Verify no crash, optionally check MediaPlayer state
    }
    
    @Test
    fun testSoundToggle() {
        soundManager.setSoundEnabled(false)
        assertFalse(soundManager.isSoundEnabled())
        
        soundManager.setSoundEnabled(true)
        assertTrue(soundManager.isSoundEnabled())
    }
    
    @After
    fun cleanup() {
        soundManager.release()
    }
}
```

---

## 📋 Implementation Checklist

- [ ] Create `res/raw/` folder
- [ ] Download/create 6 SFX assets, optimize, place in raw/
- [ ] Create `SoundManager.kt` utility
- [ ] Create `HapticManager.kt` utility
- [ ] Add VIBRATE permission to AndroidManifest
- [ ] Update QuizScreen with sound/haptic calls
- [ ] Update TrashSortScreen with sound/haptic calls
- [ ] Update MatchCardScreen with sound/haptic calls
- [ ] Update CleanRiverScreen with sound/haptic calls
- [ ] Create SettingsRow Composable
- [ ] Add audio settings to ProfileScreen
- [ ] Test on device (API 26+ for haptic, API 16+ for sound)
- [ ] Verify memory cleanup on dispose
- [ ] Load settings preference on app startup

---

## 🔗 Resources

- [Android MediaPlayer Docs](https://developer.android.com/reference/android/media/MediaPlayer)
- [Android VibrationEffect Docs](https://developer.android.com/reference/android/os/VibrationEffect)
- [Freesound.org](https://freesound.org/)
- [Audacity (free audio editor)](https://www.audacityteam.org/)

---

**Last Updated**: May 3, 2026
