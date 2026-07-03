# AGENTS.md — BeerAirB

## Project Overview

BeerAirB is an Android app for discovering and booking craft beer experiences (Airbnb-style). Built with Jetpack Compose and MVVM architecture.

## Tech Stack & Versions

| Component | Version |
|-----------|---------|
| Gradle | 9.3.1 |
| Android Gradle Plugin (AGP) | 9.1.1 |
| Kotlin | 2.2.10 |
| Compose BOM | 2026.02.01 |
| compileSdk / targetSdk | 37 (ApiLevel 37.1) |
| minSdk | 24 |
| JDK toolchain | 21 |
| Application ID | `com.mx.beerairb` |

### Libraries

| Library | Version | Usage |
|---------|---------|-------|
| `androidx.core:core-ktx` | 1.19.0 | Core Kotlin extensions |
| `androidx.lifecycle:lifecycle-runtime-ktx` | 2.11.0 | Lifecycle-aware components |
| `androidx.activity:activity-compose` | 1.13.0 | Activity Compose integration |
| `androidx.compose:compose-bom` | 2026.02.01 | Compose Bill of Materials |
| `androidx.compose.ui:ui` | BOM managed | Compose UI toolkit |
| `androidx.compose.ui:ui-graphics` | BOM managed | Compose graphics |
| `androidx.compose.material3:material3` | BOM managed | Material 3 design system |
| `androidx.compose.material:material-icons-core` | BOM managed | Material Icons |
| `androidx.compose.ui:ui-tooling-preview` | BOM managed | Preview support |
| `androidx.compose.ui:ui-tooling` (debug) | BOM managed | Layout inspector |
| `androidx.compose.ui:ui-test-manifest` (debug) | BOM managed | Test manifest |
| `androidx.compose.ui:ui-test-junit4` (test + androidTest) | BOM managed | UI testing |
| `androidx.navigation:navigation-compose` | 2.9.0 | Compose Navigation |
| `androidx.lifecycle:lifecycle-viewmodel-compose` | 2.11.0 | ViewModel in Compose |
| `junit:junit` | 4.13.2 | Unit testing (test) |
| `androidx.test.ext:junit` | 1.3.0 | AndroidX JUnit (test + androidTest) |
| `androidx.test.espresso:espresso-core` | 3.7.0 | UI testing (androidTest) |
| `io.github.takahirom.roborazzi:roborazzi` | 1.64.0 | Screenshot testing |
| `io.github.takahirom.roborazzi:roborazzi-compose` | 1.64.0 | Compose screenshot testing |
| `io.github.takahirom.roborazzi:roborazzi-junit-rule` | 1.64.0 | Roborazzi JUnit rule |
| `org.robolectric:robolectric` | 4.16.1 | Android framework mocking |

### Plugins

| Plugin | Version | ID |
|--------|---------|----|
| Android Application | 9.1.1 | `com.android.application` |
| Kotlin Compose Compiler | 2.2.10 | `org.jetbrains.kotlin.plugin.compose` |
| Roborazzi | 1.64.0 | `io.github.takahirom.roborazzi` |

## Architecture

- **Pattern**: MVVM + Repository
- **UI**: Jetpack Compose + Material 3
- **Navigation**: Navigation Compose with sealed class routes
- **State Management**: `StateFlow` in ViewModels, `collectAsState()` in Composables
- **Data Layer**: Repository interface with `MockBeerRepository` (synchronous, in-memory)
- **DI**: Manual constructor injection (no Hilt/Dagger)
- **Database**: None (mock data only)
- **Activity**: Single-activity architecture

## Package Structure

```
com.mx.beerairb/
├── MainActivity.kt
├── data/
│   ├── model/
│   │   └── BeerExperience.kt
│   └── repository/
│       ├── BeerRepository.kt          (interface)
│       └── MockBeerRepository.kt      (implementation)
└── ui/
    ├── home/
    │   ├── HomeScreen.kt
    │   └── HomeViewModel.kt
    ├── detail/
    │   ├── DetailScreen.kt
    │   └── DetailViewModel.kt
    ├── navigation/
    │   └── NavGraph.kt
    └── theme/
        ├── Color.kt
        ├── Theme.kt
        └── Type.kt
```

## Coding Conventions

- **Language**: Kotlin
- **Style**: Official Kotlin code style
- **Naming**: CamelCase for classes, camelCase for functions/variables
- **Compose**: State hoisting; ViewModels passed via default parameters
- **Imports**: Explicit imports (no wildcards)
- **Strings**: Hardcoded in Spanish (app target: Mexican market)
- **Colors**: Defined in `Color.kt`; dynamic colors preferred (Android 12+)

## Build & Run

```bash
./gradlew assembleDebug            # Build debug APK
./gradlew test                     # Run all unit tests (including screenshot)
./gradlew recordRoborazziDebug     # Record screenshot baselines
./gradlew verifyRoborazziDebug     # Verify screenshots against baselines
./gradlew connectedAndroidTest     # Run instrumented tests
```

## Commit Convention

Each commit message should explain what changed and why. Use imperative mood.

Format:
```
<type>: <short description>

<optional detailed explanation>
```

Types: `feat`, `fix`, `refactor`, `docs`, `chore`, `style`, `test`

## Workflow Notes

- This file should be updated whenever dependencies, architecture, or conventions change.
- The app targets the Mexican market — UI strings are in Spanish.
- No CI/CD is configured yet.
- Push after every commit to keep the remote in sync: `git push`
