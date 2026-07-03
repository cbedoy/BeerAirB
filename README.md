# BeerAirB 🍺

Discover and book craft beer experiences in Mexico City. An Airbnb-style marketplace for beer lovers.

## Features

- **Browse Experiences** — Scroll through a curated list of craft beer activities
- **Search & Filter** — Find experiences by title, location, category, or host
- **Detail View** — See full descriptions, pricing, ratings, and host info
- **Booking CTA** — Ready-to-implement reservation button

## Tech Stack

| Component | Technology |
|-----------|------------|
| Language | Kotlin 2.2.10 |
| UI | Jetpack Compose + Material 3 |
| Architecture | MVVM + Repository |
| Navigation | Navigation Compose 2.9.0 |
| State Management | StateFlow + collectAsState() |
| DI | Manual constructor injection |
| Min SDK | 24 |
| Target SDK | 37 |
| Build | Gradle 9.3.1 + AGP 9.1.1 |

## Getting Started

```bash
# Build debug APK
./gradlew assembleDebug

# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

## Project Structure

```
com.mx.beerairb/
├── MainActivity.kt              # Entry point
├── data/
│   ├── model/
│   │   └── BeerExperience.kt    # Data model
│   └── repository/
│       ├── BeerRepository.kt    # Repository interface
│       └── MockBeerRepository.kt # Mock data (6 experiences)
└── ui/
    ├── home/                    # Home list + search
    ├── detail/                  # Experience detail
    ├── navigation/              # NavGraph + routes
    └── theme/                   # Colors, typography, theme
```

## Target Market

The app is built for the Mexican market — all UI strings are in Spanish.
