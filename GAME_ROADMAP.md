# Hero Quest Mini-Games Development Roadmap (2 Minggu)

## Overview
Roadmap ini fokus pada stabilisasi core gameplay loop, persistensi data, dan polishing UX untuk meningkatkan retention dan user satisfaction. Diurutkan berdasarkan impact vs effort.

---

## 🎯 Minggu 1: Foundation & Persistence

### 1.1 Data Persistence Layer (Priority: CRITICAL)
**Target**: Sempurna simpan & restore progress pemain lintas session.

**Scope**:
- Buat data class untuk game state: `GameProgress.kt`
  - Level per game
  - High score per game
  - Total XP, HK, badges collected
  - Last played timestamp
  - Unlock status per game

- Implementasi Room database atau shared preferences:
  - `GameProgressDao.kt` untuk CRUD
  - `GameProgressRepository.kt` wrapper untuk ViewModel

- Update semua game ViewModel untuk call repository on score change:
  - Setiap kali game berakhir, save ke database
  - Load state saat ViewModel init

- Update Home/Profile screen untuk baca high score dari database

**Files to Create/Modify**:
```
app/src/main/java/com/example/luminasdgs/
├── data/
│   ├── model/GameProgress.kt (NEW)
│   ├── dao/GameProgressDao.kt (NEW)
│   └── repository/GameProgressRepository.kt (NEW)
├── database/AppDatabase.kt (NEW - jika Room dipakai)
└── viewmodel/
    ├── QuizViewModel.kt (UPDATE)
    ├── TrashSortViewModel.kt (UPDATE)
    ├── MatchCardViewModel.kt (UPDATE)
    └── CleanRiverViewModel.kt (UPDATE)
```

**Effort**: 4-5 hari | **Impact**: ⭐⭐⭐⭐⭐ (tanpa ini semua progress hilang)

---

### 1.2 Difficulty Scaling & Balancing (Priority: HIGH)
**Target**: Game tidak terlalu mudah di awal, tidak frustasi di tengah.

**Scope**:
- Tambah enum `Difficulty` ke setiap game (EASY, NORMAL, HARD)
- Sesuaikan parameter per level:
  - **Quiz**: waktu/soal per difficulty
  - **Trash Sort**: jumlah item & speed trash
  - **Match Card**: grid size & time limit
  - **Clean River**: spawn rate & penalty

- Implementasi unlock progression:
  - EASY terbuka default
  - NORMAL unlock setelah first EASY completion
  - HARD unlock setelah 3x NORMAL completion

- Update GameProgressViewModel untuk track completed levels & difficulty unlock

**Files to Create/Modify**:
```
app/src/main/java/com/example/luminasdgs/
├── model/Difficulty.kt (NEW)
├── viewmodel/
│   ├── QuizViewModel.kt (UPDATE)
│   ├── TrashSortViewModel.kt (UPDATE)
│   ├── MatchCardViewModel.kt (UPDATE)
│   └── CleanRiverViewModel.kt (UPDATE)
└── ui/screens/game/GameScreen.kt (UPDATE - show unlock badge)
```

**Effort**: 3 hari | **Impact**: ⭐⭐⭐⭐ (engagement, retention, fairness)

---

### 1.3 Optimized Fail/Retry UX (Priority: HIGH)
**Target**: Kurangi friction saat game over → retry.

**Scope**:
- Tambah `Game Over Modal` reusable:
  - Show: final score, XP earned, compare to high score
  - Tombol: "Try Again" (instan jump), "Back to Hub" (with confirm)
  - Countdown 3-2-1 sebelum auto-restart (optional)

- Implementasi instan restart logic di setiap game ViewModel:
  - Reset game state tanpa recreate ViewModel
  - Kurangi recomposition

- Test: dari game over modal ke ready play harus < 500ms

**Files to Create/Modify**:
```
app/src/main/java/com/example/luminasdgs/
├── ui/components/GameOverModal.kt (NEW)
├── ui/screens/game/
│   ├── quiz/QuizScreen.kt (UPDATE)
│   ├── trashsort/TrashSortScreen.kt (UPDATE)
│   ├── matchcard/MatchCardScreen.kt (UPDATE)
│   └── river/CleanRiverScreen.kt (UPDATE)
└── viewmodel/ (UPDATE all game VMs - add reset() function)
```

**Effort**: 2-3 hari | **Impact**: ⭐⭐⭐⭐ (UX, retry rate, session length)

---

### 1.4 Daily Missions / Goals (Priority: MEDIUM)
**Target**: Player punya objective harian yang terarah + habit loop.

**Scope**:
- Buat `DailyMission` data class:
  ```kotlin
  data class DailyMission(
      val id: Int,
      val title: String,      // e.g., "Complete 2 games"
      val description: String,
      val targetCount: Int,   // e.g., 2
      val progress: Int,
      val reward: RewardData, // XP + HK
      val isCompleted: Boolean
  )
  ```

- Buat `DailyMissionViewModel`:
  - Generate 3-5 misi random setiap hari (server atau local algo)
  - Track progress real-time saat game berakhir
  - Claim reward saat mission complete

- Update Home screen:
  - Buat section "Daily Missions" di bawah featured goal
  - Show progress bar, claim button

- Implementasi timezone-aware reset (setiap hari UTC 00:00)

**Files to Create/Modify**:
```
app/src/main/java/com/example/luminasdgs/
├── data/model/
│   ├── DailyMission.kt (NEW)
│   └── RewardData.kt (NEW)
├── data/dao/DailyMissionDao.kt (NEW)
├── viewmodel/DailyMissionViewModel.kt (NEW)
├── ui/screens/home/HomeScreen.kt (UPDATE - add missions section)
└── ui/components/DailyMissionCard.kt (NEW)
```

**Effort**: 3 hari | **Impact**: ⭐⭐⭐⭐ (retention, DAU, habit)

---

## 🎮 Minggu 2: Polish & Advanced Features

### 2.1 Tutorial Per Mini-Game (Priority: MEDIUM)
**Target**: Pemain baru tahu cara main sebelum first play.

**Scope**:
- Buat `OnboardingGameScreen` reusable:
  - 3-4 slide per game (rules, contoh, tip)
  - Interactive demo (bukan static image)
  - "Got it" button untuk skip/lanjut

- Trigger logic:
  - Show saat first open game (cek flag di database)
  - Set flag "tutorial_seen_{gameName}" setelah complete
  - Allow replay via "How to Play" button di game hub

- Keep duration 20-40 detik agar tidak boring

**Files to Create/Modify**:
```
app/src/main/java/com/example/luminasdgs/
├── ui/screens/game/
│   ├── onboarding/GameOnboardingScreen.kt (NEW)
│   ├── quiz/QuizScreen.kt (UPDATE - trigger tutorial check)
│   ├── trashsort/TrashSortScreen.kt (UPDATE)
│   ├── matchcard/MatchCardScreen.kt (UPDATE)
│   └── river/CleanRiverScreen.kt (UPDATE)
└── data/model/GameOnboardingState.kt (NEW)
```

**Effort**: 2-3 hari | **Impact**: ⭐⭐⭐ (onboarding, drop-off reduction, confidence)

---

### 2.2 Sound & Haptic Feedback (Priority: MEDIUM)
**Target**: Game terasa lebih alive dan responsive.

**Scope**:
- Integrasikan media player untuk SFX:
  - res/raw/ folder dengan .wav/.mp3:
    - `correct.wav` (beep/ding positif, ~200ms)
    - `wrong.wav` (buzz negatif, ~150ms)
    - `combo.wav` (chime/bell, ~300ms)
    - `game_over.wav` (dramatic, ~500ms)
    - `click.wav` (UI tap feedback, ~100ms)

- Haptic trigger di event penting:
  - Correct: small vibration (50ms, medium amplitude)
  - Wrong: 2x quick pulse (30ms each)
  - Combo: longer vibration (100ms)
  - Game over: slow rumble (200ms)

- Implementasi volume control:
  - Settings toggle: "Sound ON/OFF", "Haptic ON/OFF"
  - Store preference di shared prefs

**Files to Create/Modify**:
```
app/src/main/java/com/example/luminasdgs/
├── res/raw/ (NEW - SFX assets)
├── utils/SoundManager.kt (NEW)
├── utils/HapticManager.kt (NEW)
├── ui/screens/game/
│   ├── quiz/QuizScreen.kt (UPDATE)
│   ├── trashsort/TrashSortScreen.kt (UPDATE)
│   ├── matchcard/MatchCardScreen.kt (UPDATE)
│   └── river/CleanRiverScreen.kt (UPDATE)
└── ui/screens/profile/ProfileScreen.kt (UPDATE - add settings)
```

**Effort**: 2 hari | **Impact**: ⭐⭐⭐⭐ (game feel, immersion, polish)

---

### 2.3 Anti-Cheat & Reward Cap (Priority: MEDIUM)
**Target**: Cegah farming abnormal, jaga ekonomi reward balanced.

**Scope**:
- Implementasi reward cap:
  - Max XP/HK per hari (e.g., 500 XP, 200 HK)
  - Track daily cumulative di database
  - Show warning saat mendekati cap
  - Disallow reward klaim jika sudah capai cap

- Validasi reward saat claim:
  - Check session duration (jangan < 5 detik benar-benar main)
  - Server-side validation (jika ada backend)
  - Flag suspicious pattern untuk review

- Cooldown per game:
  - Min 30 detik antar completion sebelum score dihitung
  - Skip detection: cegah rapid-fire sama game

- Database log setiap reward claim untuk audit

**Files to Create/Modify**:
```
app/src/main/java/com/example/luminasdgs/
├── data/model/RewardLog.kt (NEW)
├── data/dao/RewardLogDao.kt (NEW)
├── utils/RewardValidator.kt (NEW)
├── viewmodel/ (UPDATE all game VMs - validate before save)
└── ui/screens/game/components/ImmersiveGameHeader.kt (UPDATE - show cap progress)
```

**Effort**: 2 hari | **Impact**: ⭐⭐⭐ (economy stability, fairness)

---

### 2.4 Analytics Tracking (Priority: LOW-MEDIUM)
**Target**: Collect data untuk tuning difficulty, UX, retention.

**Scope**:
- Track key metrics:
  - Game start/completion/fail (per game, per difficulty)
  - Time to complete
  - Quit point (saat drop-off)
  - Session length, daily active users (DAU)
  - High score benchmark per game

- Log to local SQLite terlebih dahulu:
  - Batch send ke backend 1x per jam jika ada connectivity
  - Fallback store local kalau offline

- Dashboard simple di Profile (optional): show personal stats

**Files to Create/Modify**:
```
app/src/main/java/com/example/luminasdgs/
├── data/model/Analytics.kt (NEW)
├── data/dao/AnalyticsDao.kt (NEW)
├── utils/AnalyticsManager.kt (NEW)
├── service/AnalyticsUploadService.kt (NEW - background sync)
└── viewmodel/ (UPDATE all game VMs - track event)
```

**Effort**: 2-3 hari | **Impact**: ⭐⭐⭐ (data-driven tuning, product insight)

---

### 2.5 Accessibility Improvements (Priority: MEDIUM)
**Target**: Game inclusive untuk berbagai kemampuan pengguna.

**Scope**:
- Text size option:
  - Setting: small/normal/large
  - Apply ke seluruh text di game screens
  - Store di preferences

- Color contrast audit:
  - Review HUD, buttons, feedback text
  - WCAG AA minimum (4.5:1 untuk text)
  - Test mode: greyscale / high contrast simulator

- Touch target size:
  - Ensure semua button >= 48dp
  - Padding antar interactable element >= 12dp

- Screen reader compatible labels:
  - contentDescription di semua icon/button
  - TalkBack friendly
  - Semantic structure jelas

**Files to Create/Modify**:
```
app/src/main/java/com/example/luminasdgs/
├── utils/AccessibilityPreferences.kt (NEW)
├── ui/theme/Accessibility.kt (NEW)
├── ui/screens/game/ (UPDATE all - add contentDescription, adjust colors)
└── ui/screens/profile/ProfileScreen.kt (UPDATE - add text size setting)
```

**Effort**: 2-3 hari | **Impact**: ⭐⭐⭐ (inclusion, compliance, user satisfaction)

---

## 📊 Success Metrics (End of Week 2)

| Metrik | Target | Cara Ukur |
|--------|--------|-----------|
| Retention (D1) | > 60% | Analytics table |
| Avg Session Length | > 5 min | AnalyticsManager log |
| Game Completion Rate | > 70% | Game logs (complete vs fail ratio) |
| High Score Improvement | +30% | Compare week 1 vs week 2 |
| Crash Rate | < 0.1% | Crash logs, Firebase |
| Daily Mission Completion | > 50% | DailyMission table |

---

## 🚀 Nice-to-Have (Future Sprints)

1. **Multiplayer/Leaderboard**: Ranking global/friend
2. **Battle Pass**: Seasonal progression dengan unlock cosmetic
3. **Seasonal Events**: Limited-time game variant
4. **Social Sharing**: Share score + screenshot ke social media
5. **Push Notification**: Reminder daily mission, seasonal event
6. **A/B Testing**: Coba variable kesulitan, reward, UI layout
7. **Video Replay**: Record gameplay video untuk share/analysis
8. **Skin/Avatar Customization**: Equip cosmetic di profile

---

## 📅 Timeline Summary

```
Week 1:
  Mon-Tue: Persistence layer + database setup
  Wed-Thu: Difficulty scaling + unlock system
  Fri: Fail/retry UX + daily missions

Week 2:
  Mon-Tue: Game tutorial + sound/haptic
  Wed-Thu: Anti-cheat + analytics
  Fri: Accessibility + final polish & testing
```

---

## 🔗 Dependencies & Notes

- **Database**: Room (recommended) vs SharedPreferences (simpler)
- **Sound**: MediaPlayer (built-in) vs ExoPlayer (more advanced)
- **Haptic**: VibrationEffect API (min API 26)
- **Analytics**: Local SQLite + optional Firebase/Segment sync
- **Testing**: Unit test database layer + integration test gameplay flow

---

## ✅ Approval Checklist

- [ ] Week 1 milestones approved by product owner
- [ ] Week 2 scope finalized
- [ ] Design/mockup for tutorials reviewed
- [ ] Sound assets sourced/licensed
- [ ] Testing strategy defined (manual + automated)

---

**Last Updated**: May 3, 2026  
**Owner**: Hero Quest Development Team
