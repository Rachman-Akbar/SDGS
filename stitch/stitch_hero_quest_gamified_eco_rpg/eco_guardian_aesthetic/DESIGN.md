---
name: Eco-Guardian Aesthetic
colors:
  surface: '#fdf8f6'
  surface-dim: '#ddd9d7'
  surface-bright: '#fdf8f6'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f7f3f1'
  surface-container: '#f1edeb'
  surface-container-high: '#ebe7e5'
  surface-container-highest: '#e5e2e0'
  on-surface: '#1c1b1b'
  on-surface-variant: '#40493d'
  inverse-surface: '#31302f'
  inverse-on-surface: '#f4f0ee'
  outline: '#707a6c'
  outline-variant: '#bfcaba'
  surface-tint: '#1b6d24'
  primary: '#0d631b'
  on-primary: '#ffffff'
  primary-container: '#2e7d32'
  on-primary-container: '#cbffc2'
  inverse-primary: '#88d982'
  secondary: '#006a60'
  on-secondary: '#ffffff'
  secondary-container: '#85f6e5'
  on-secondary-container: '#007166'
  tertiary: '#6e5100'
  on-tertiary: '#ffffff'
  tertiary-container: '#8c6800'
  on-tertiary-container: '#ffefd7'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#a3f69c'
  primary-fixed-dim: '#88d982'
  on-primary-fixed: '#002204'
  on-primary-fixed-variant: '#005312'
  secondary-fixed: '#85f6e5'
  secondary-fixed-dim: '#67d9c9'
  on-secondary-fixed: '#00201c'
  on-secondary-fixed-variant: '#005048'
  tertiary-fixed: '#ffdfa0'
  tertiary-fixed-dim: '#f8bd2a'
  on-tertiary-fixed: '#261a00'
  on-tertiary-fixed-variant: '#5c4300'
  background: '#fdf8f6'
  on-background: '#1c1b1b'
  surface-variant: '#e5e2e0'
typography:
  display-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 40px
    fontWeight: '800'
    lineHeight: 48px
    letterSpacing: -0.02em
  headline-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 24px
    fontWeight: '700'
    lineHeight: 32px
  title-lg:
    fontFamily: Lexend
    fontSize: 20px
    fontWeight: '600'
    lineHeight: 28px
  body-md:
    fontFamily: Lexend
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
  label-caps:
    fontFamily: Lexend
    fontSize: 12px
    fontWeight: '700'
    lineHeight: 16px
    letterSpacing: 0.05em
rounded:
  sm: 0.5rem
  DEFAULT: 1rem
  md: 1.5rem
  lg: 2rem
  xl: 3rem
  full: 9999px
spacing:
  base: 8px
  margin-page: 24px
  gutter-card: 16px
  padding-pill: 12px 24px
  stack-sm: 12px
  stack-md: 24px
  stack-lg: 40px
---

## Brand & Style

The design system is built to inspire action through a sense of heroic purpose and environmental stewardship. It targets Gen Z and Millennial users who value sustainability but seek the dopamine loops of mobile gaming. The visual style merges the structured reliability of **Google Material 3** with a **Playful, Eco-Friendly twist**, utilizing tactile elements that feel organic yet digital-first.

The emotional response should be one of "Empowered Wellness"—where every interaction feels like a contribution to a larger global mission. We avoid the "preachy" tone often found in sustainability apps, opting instead for a premium, polished game-like environment that celebrates every micro-win with vibrant feedback and soft, approachable forms.

## Colors

The color strategy uses nature-inspired hues with high saturation to maintain a "gaming" energy. 
- **Emerald Green** serves as the primary driver for progress and "Heroic" actions.
- **Fresh Teal** and **Sky Blue** are used for secondary navigation and informational states, representing water and air.
- **Sunny Yellow** is the high-visibility accent reserved for rewards, currency (Hero Koins), and "Level Up" moments.
- **Earth Tones** replace harsh whites for backgrounds to reduce eye strain and reinforce the organic narrative.

Use high-contrast pairings (e.g., Emerald on Earth) to ensure accessibility while maintaining a lush, vibrant atmosphere.

## Typography

This design system utilizes **Plus Jakarta Sans** for headlines to provide a modern, geometric, and friendly "hero" feel. For body copy and interface labels, **Lexend** is selected specifically for its pedagogical roots and high readability, which supports the educational aspect of the SDGs.

Typography should follow a clear hierarchy:
- Use **Display** styles for XP gains and major achievements.
- Use **Lexend Bold** for "Hero Koin" counts and level indicators.
- Maintain generous line heights to ensure the content feels breezy and approachable, never cluttered.

## Layout & Spacing

The layout philosophy follows a **Fluid Grid** with generous safe-area margins (24px) to create a "premium" feel. Elements are arranged in a vertical stack of cards, emphasizing a single-column focus for mobile ease-of-use. 

Spacing is based on an 8px rhythmic scale. Use "Stack" spacing (24px) between distinct content cards and "Gutter" spacing (16px) for nested elements within cards. The generous white space (or "earth space") is intentional, reflecting a clean, unpolluted environment.

## Elevation & Depth

Hierarchy is achieved through **Ambient Shadows** and **Tonal Layering**. Unlike standard Material 3 which uses flat tonal shifts, this design system uses soft, diffused shadows with a slight Emerald-tinted umbra to make cards feel like they are floating over the earth-toned background.

- **Level 0:** Earth-tone background (#EFEBE9).
- **Level 1:** White surface cards with 15% opacity primary-color shadows (Blur: 20px, Y: 8px).
- **Level 2:** Elevated buttons and progress rings that appear tactile and "clickable."
- **Level 3:** Animated toast popups and reward overlays, using a higher elevation and a subtle backdrop blur (Glassmorphism) to dim the background.

## Shapes

The shape language is defined by **Extreme Roundedness**. A core radius of **24px** is applied to all primary cards to evoke a friendly, non-threatening, and organic feel. 

- **Buttons:** Fully pill-shaped (rounded-full) to signify playfulness.
- **Progress Bars:** Fully rounded ends to mimic liquid filling a container.
- **Icons:** Enclosed in circular or "squircle" containers to maintain the soft-edged visual theme.
- **Selection States:** Use thick, rounded borders (3px) rather than sharp lines.

## Components

### Navigation
The **Bottom Navigation** bar features 5 oversized icons: Home, Actions, Games, Rewards, and Profile. The active state is indicated by a "blob" or pill-shaped background highlight in Fresh Teal, with the icon bouncing slightly upon selection.

### Buttons
- **Primary:** Pill-shaped, high-contrast Emerald Green with white text. Apply a subtle gradient to give it a 3D "squishy" feel.
- **Secondary:** Outlined with a 2px Emerald stroke and Lexend Semibold text.
- **Floating Action Button (FAB):** Sunny Yellow, containing the "Quick Action" for logging a sustainability task.

### Progress & Gamification
- **XP Bars:** Dual-tone tracks (Light Teal background, Emerald foreground) with a glowing tip.
- **Hero Koins:** Circular icons in Sunny Yellow with a stamped "H" glyph, using a slight inner-bevel to appear like physical currency.
- **Level Tags:** Small, high-contrast badges (Sky Blue) pinned to the corner of user avatars.

### Cards & Feedback
- **Task Cards:** High-elevation white cards with a "Action" button and a vibrant SDG-specific icon.
- **Toasts:** "Level Up" notifications should use "Animated-style" popups that scale from 0% to 100% with a spring bounce, featuring Sunny Yellow confetti-style accents.
- **Progress Rings:** Large-stroke circular indicators for daily goals, using a gradient from Teal to Emerald.