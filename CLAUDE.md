# CLAUDE.md — BeerAirB

## Project Context

BeerAirB is a single-activity Android application built entirely with Jetpack Compose and Material 3. It follows MVVM + Repository architecture with manual dependency injection (no Hilt/Dagger). The app displays craft beer experiences via mock data and allows navigation from a home list to a detail screen with search and filtering.

## Architecture Guidelines

### MVVM Pattern
- **Model**: `BeerExperience` data class in `data/model/`
- **Repository**: Interface + mock implementation in `data/repository/`
- **ViewModel**: Per-screen ViewModels using `StateFlow` for state
- **View**: Stateless Composables consuming state via `collectAsState()`

### Navigation
- Routes are defined as `sealed class Screen` in `NavGraph.kt`
- Navigation Compose with string arguments passed via route
- `NavHost` with two destinations: `home` and `detail/{id}`

### State Management
- ViewModels expose `StateFlow` (private `MutableStateFlow` + public `asStateFlow()`)
- Composables collect state with `val state by viewModel.state.collectAsState()`
- ViewModel scope launches for async work via `viewModelScope.launch`
- `SavedStateHandle` reads navigation arguments (`savedStateHandle.get<String>("id")`)

### Data Layer
- `BeerRepository` interface defines `getAllExperiences()` and `getExperienceById()`
- `MockBeerRepository` returns hardcoded list of 6 experiences
- Currently synchronous (List return type, not Flow)

### Dependency Injection
- Manual constructor injection with default parameters
- `HomeViewModel(repository = MockBeerRepository())`
- `DetailViewModel(savedStateHandle, repository = MockBeerRepository())`
- ViewModels instantiated via `viewModel()` composable function

### Composable Structure
- Screens accept navigation callbacks as lambda parameters
- `viewModel` parameter has a default of `viewModel()` for testability
- Material 3 components: `Scaffold`, `NavigationBar`, `Card`, `Surface`, `Button`, `OutlinedTextField`, `Icon`, `Text`, `CircularProgressIndicator`
- Layout: `Column` with `verticalScroll`, `LazyRow` for horizontal sections, `Row`, `Box`
- Bottom navigation: `MainScaffold` wraps `NavGraph` in a `Scaffold` with `NavigationBar`
- Home: scrollable `Column` with `SearchBar`, `CategorySelector` (chips), `LazyRow` of `NearbyTaproomCard`, `ExploreBanner`
- Detail: scrollable `Column` with `HeroImageHeader`, `TitleRatingBlock`, `AmenitiesRow`, `DateSelector`, `BookingBar`

## Theme
- `BeerAirBTheme` uses a fixed brand color scheme (no dynamic color, no dark theme)
- Primary accent: `AmberPrimary` (#E67E22), background: `CreamBg` (#F9F6F0)
- Text: `DarkToasted` (#1A1105) for titles, `ClayGray` (#786E64) for secondary
- Badge colors: `CreamSoft`, `GreenLight`, `GoldPale`, `BlueGray` (pastel tones for amenities)
- `Typography` uses `FontFamily.SansSerif` styled as Montserrat with full hierarchy
- Font files expected in `res/font/montserrat.xml`

## Code Style Rules

### Kotlin
- No semicolons
- Explicit types on public API, inferred on private
- `data class` for models
- `sealed class` for navigation routes with `data object` for leaf routes
- `private val _state = MutableStateFlow(...)` + `val state = _state.asStateFlow()`

### Compose
- Keep Composables stateless when possible; hoist state to ViewModel
- Use `Modifier` parameter for layout customization
- Private composable functions for internal UI decomposition (e.g., `DetailContent`)
- Use `key` parameter in `items()` for stable recomposition

### Testing
- Unit tests in `src/test/` (JUnit 4 + Robolectric)
- Instrumented tests in `src/androidTest/` (AndroidX Test + Espresso + Compose Test)
- Screenshot tests in `src/test/java/.../screenshot/` using Roborazzi + Robolectric
  - `HomeScreenScreenshotTest` — captures `NearbyTaproomCard` composable
  - `DetailScreenScreenshotTest` — captures `DetailContent` composable
- Record baselines: `./gradlew recordRoborazziDebug`
- Verify against baselines: `./gradlew verifyRoborazziDebug`
- Screenshots are stored in `build/outputs/roborazzi/` and copied to `screenshots/v<version>/` for README
- Screenshots directory is organized by version tag: `screenshots/v0.0.1/`, `screenshots/v0.1.0/`, etc.
- `@GraphicsMode(GraphicsMode.Mode.NATIVE)` and `@Config(sdk = [34])` required on test classes

## Resources
- Drawables: `ic_beer_1` through `ic_beer_6` in `res/drawable/`
- Strings: in Spanish, defined in `res/values/strings.xml` and hardcoded in Composables
- Icons: Material Icons (core + extended — `Icons.AutoMirrored.Filled.ArrowBack`, `Icons.Default.Business`, `Icons.Default.Cabin`, `Icons.Default.LocalBar`, `Icons.Default.Nature`, `Icons.Default.ChatBubble`, etc.)
- Font: `res/font/montserrat.xml` referencing system sans-serif (Montserrat-style)
- Colors: Defined in `ui/theme/Color.kt` — all amber/golden craft beer palette

## Common Tasks

### Adding a new screen
1. Create ViewModel in `ui/<feature>/` with `StateFlow` state
2. Create Composable screen function accepting navigation callbacks
3. Add route entry to `Screen` sealed class in `ui/navigation/Screen.kt`
4. Add `composable()` block in `ui/navigation/NavGraph.kt`
5. If it's a bottom nav tab, add `BottomNavItem` entry in `MainScaffold.kt`

### Adding a new repository
1. Define interface in `data/repository/`
2. Implement in same package
3. Inject via constructor default parameter in ViewModel

### Adding a new model
1. Add `data class` in `data/model/`
2. Update repository interface if needed
3. Update mock data in `MockBeerRepository.kt`

## Key Files Reference

| File | Purpose |
|------|---------|
| `app/build.gradle.kts` | Module dependencies |
| `gradle/libs.versions.toml` | Version catalog (single source of truth for versions) |
| `settings.gradle.kts` | Project settings and repositories |
| `app/src/main/AndroidManifest.xml` | Manifest |
| `MainActivity.kt` | Entry point, sets content to MainScaffold |
| `ui/navigation/MainScaffold.kt` | Scaffold with bottom navigation bar (5 items) |
| `ui/navigation/Screen.kt` | Sealed class route definitions |
| `ui/navigation/NavGraph.kt` | Navigation graph with composable destinations |
| `data/repository/BeerRepository.kt` | Repository interface |
| `data/repository/MockBeerRepository.kt` | Mock data (6 experiences with amenities) |
| `data/model/BeerExperience.kt` | Beer experience data model (extended fields) |
| `data/model/BeerAmenity.kt` | Amenity data model |
| `ui/home/HomeScreen.kt` | Home screen with search, categories, cards, banner |
| `ui/home/HomeViewModel.kt` | Home state, search, category filter, favorites |
| `ui/home/SearchBar.kt` | Search text field component |
| `ui/home/CategorySelector.kt` | Horizontal category chip selector (4 categories) |
| `ui/home/NearbyTaproomCard.kt` | Experience card with image, distance, dates, fav |
| `ui/home/ExploreBanner.kt` | Promotional banner with CTA |
| `ui/detail/DetailScreen.kt` | Detail view with all sections |
| `ui/detail/DetailViewModel.kt` | Detail state loading with favorites |
| `ui/detail/HeroImageHeader.kt` | Full-width image with floating back/fav buttons |
| `ui/detail/TitleRatingBlock.kt` | Title, rating, description with Read More |
| `ui/detail/AmenitiesRow.kt` | 4 pastel amenity badges |
| `ui/detail/DateSelector.kt` | Check-in / Check-out date chips (amber) |
| `ui/detail/BookingBar.kt` | Price display + amber Reserve button |
| `ui/theme/Color.kt` | Color definitions (amber palette) |
| `ui/theme/Theme.kt` | Material 3 theme (brand colors, no dynamic) |
| `ui/theme/Type.kt` | Typography (Montserrat-based) |
| `ui/favorites/FavoritesScreen.kt` | Placeholder favorites screen |
| `ui/messages/MessagesScreen.kt` | Placeholder messages screen |
| `ui/profile/ProfileScreen.kt` | Placeholder profile screen |

## Git Notes

- Push after every commit to keep the remote in sync: `git push`
- Tags must also be pushed separately: `git push --tags`
- Commit messages should explain what changed and why
- Keep commits focused on single logical changes
- Tag releases following semantic versioning:
  - `feat` → minor bump (`0.1.0` → `0.2.0`)
  - `fix` → patch bump (`0.1.0` → `0.1.1`)
  - `docs`/`chore`/`style` → patch bump
  - Breaking changes → major bump (`0.x.y` → `1.0.0`)
- Format: `git tag -a "v<version>" -m "v<version> — <description>" && git push --tags`
