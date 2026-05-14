# Hero Quest - Feature Development Checklist

## ✅ Already Implemented

- [x] 4 mini-games (Quiz, Trash Sort, Match Card, Clean River)
- [x] Immersive game HUD (timer, lives, score, target, progress)
- [x] Real countdown timer + timeout handling
- [x] Dynamic lives system + game-over state
- [x] Visual feedback (toast rewards, combo animations)
- [x] Navigation & routing (splash, onboarding, 5 main screens)
- [x] Bottom navigation bar with proper route filtering
- [x] Stitch design adaptation to Compose

---

## 🎯 WEEK 1 (Critical Path)

### 1. Data Persistence (CRITICAL)

- [ ] Create GameProgress data model
- [ ] Setup Room database + DAO
- [ ] Implement GameProgressRepository
- [ ] Update all game ViewModels to save/load state
- [ ] Test: Close app mid-game, verify progress restored

**Owner**: [TODO]  
**Target**: Wed May 8

### 2. Difficulty Scaling (HIGH)

- [ ] Create Difficulty enum (EASY, NORMAL, HARD)
- [ ] Adjust game parameters per difficulty
- [ ] Implement unlock progression logic
- [ ] Update GameScreen to show unlock badges
- [ ] Balance testing on each game

**Owner**: [TODO]  
**Target**: Fri May 10

### 3. Fail/Retry UX (HIGH)

- [ ] Create reusable GameOverModal component
- [ ] Implement instant reset logic in all game VMs
- [ ] Add "Try Again" button with sub-500ms latency
- [ ] Test on low-end device performance

**Owner**: [TODO]  
**Target**: Wed May 8

### 4. Daily Missions (MEDIUM)

- [ ] Create DailyMission data model & DAO
- [ ] Implement DailyMissionViewModel
- [ ] Design mission generation algorithm
- [ ] Add Daily Missions section to Home
- [ ] Setup timezone-aware reset

**Owner**: [TODO]  
**Target**: Thu May 9

---

## 🎮 WEEK 2 (Polish & Features)

### 5. Game Tutorials (MEDIUM)

- [ ] Create OnboardingGameScreen component
- [ ] Design tutorial flow for each game (3-4 slides)
- [ ] Implement first-time flag tracking
- [ ] Add "How to Play" button to game hub
- [ ] Test completion time < 1 min per tutorial

**Owner**: [TODO]  
**Target**: Mon May 13

### 6. Sound & Haptic (MEDIUM)

- [ ] Source/create 5 SFX assets (correct, wrong, combo, game-over, click)
- [ ] Create SoundManager utility
- [ ] Create HapticManager utility
- [ ] Integrate into all game screens at key events
- [ ] Add sound/haptic toggle in Settings

**Owner**: [TODO]  
**Target**: Tue May 14

### 7. Anti-Cheat & Reward Cap (MEDIUM)

- [ ] Setup daily reward cap (e.g., 500 XP, 200 HK)
- [ ] Create RewardValidator utility
- [ ] Implement reward log table for audit
- [ ] Add cooldown between game plays
- [ ] Test cap + warning UI

**Owner**: [TODO]  
**Target**: Wed May 15

### 8. Analytics Tracking (LOW-MEDIUM)

- [ ] Create AnalyticsEvent data model
- [ ] Setup local SQLite analytics table
- [ ] Implement tracking in all game VMs + navigation
- [ ] Create AnalyticsManager for batch logging
- [ ] Add basic stats display in Profile (optional)

**Owner**: [TODO]  
**Target**: Thu May 16

### 9. Accessibility (MEDIUM)

- [ ] Add text size preference (small/normal/large)
- [ ] Audit color contrast (WCAG AA)
- [ ] Ensure touch targets >= 48dp
- [ ] Add contentDescription to all icons/buttons
- [ ] Test with TalkBack

**Owner**: [TODO]  
**Target**: Fri May 17

---

## 📊 Definition of Done

For each feature, verify:

- [ ] Code compiles without errors/warnings
- [ ] Unit tests pass (if applicable)
- [ ] Manual testing on device (API 28+)
- [ ] PR reviewed + approved
- [ ] Merged to main branch
- [ ] Metrics/analytics confirm expected behavior
- [ ] No regression in other features

---

## 🔍 Testing Checklist

### Manual Testing (Per Feature)

- [ ] Happy path (normal game flow)
- [ ] Error path (timeout, no lives, out of cap)
- [ ] Edge cases (rapid clicks, airplane mode, low battery)
- [ ] Performance (< 16ms frame time, minimal GC)
- [ ] Low-end device (API 28, 2GB RAM)

### Automated Testing (Recommended)

- [ ] Database layer: DAO CRUD tests
- [ ] ViewModel: state management tests
- [ ] Reward validator: cap logic tests
- [ ] Analytics: event logging tests

---

## 📱 Known Issues to Fix During Development

- [ ] QuizScreen: Verify animation doesn't drop frames at timer <5s
- [ ] TrashSortScreen: Test rapid item swiping doesn't crash
- [ ] MatchCardScreen: Check grid layout responsive at all screen sizes
- [ ] CleanRiverScreen: Verify combo animation doesn't interfere with gameplay
- [ ] HUD: Ensure stays visible during all game states

---

## 📞 Dependencies & Blockers

- **Design Assets**: Finalize tutorial mockups (blocking feature 5)
- **Backend API**: If analytics needs server sync (blocking feature 8)
- **Sound Assets**: Need SFX licensing approval (blocking feature 6)
- **Database Schema**: Finalize with BE team (blocking feature 1)

---

## 🚀 Post-Week-2 Roadmap

1. **Multiplayer** (Week 3): Leaderboard + friend challenges
2. **Battle Pass** (Week 4): Seasonal progression + cosmetics
3. **Events** (Week 5): Limited-time game variants
4. **Social** (Week 6): Share score + invitations

---

**Last Updated**: May 3, 2026  
**Next Review**: May 10, 2026
