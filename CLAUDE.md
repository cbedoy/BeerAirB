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
- Composable functions are `@Composable` with `@OptIn(ExperimentalMaterial3Api::class)` where needed
- Material 3 components: `Scaffold`, `TopAppBar`, `Card`, `Surface`, `Button`, `OutlinedTextField`, `Icon`, `Text`, `CircularProgressIndicator`
- Layout: `LazyColumn` + `items()`, `Column`, `Row`, `Box`

## Theme
- `BeerAirBTheme` wraps Material 3 theming with dynamic colors (Android 12+)
- Fallback: `Purple80`/`Purple40` dark/light color schemes
- Custom beer-themed colors: `Amber80`, `Amber40`, `DarkBrown`, `WarmBeige`
- `Typography` uses `FontFamily.Default` with 16sp `bodyLarge`

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
- Unit tests in `src/test/` (JUnit 4)
- Instrumented tests in `src/androidTest/` (AndroidX Test + Espresso + Compose Test)
- `ExampleUnitTest.kt` and `ExampleInstrumentedTest.kt` exist as placeholders

## Resources
- Drawables: `ic_beer_1` through `ic_beer_6` in `res/drawable/`
- Strings: in Spanish, defined in `res/values/strings.xml` and hardcoded in Composables
- Icons: Material Icons (`Icons.AutoMirrored.Filled.ArrowBack`)

## Common Tasks

### Adding a new screen
1. Create ViewModel in `ui/<feature>/` with `StateFlow` state
2. Create Composable screen function accepting navigation callbacks
3. Add route to `Screen` sealed class in `NavGraph.kt`
4. Add `composable()` block in `NavGraph`

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
| `MainActivity.kt` | Entry point, theme + nav setup |
| `ui/navigation/NavGraph.kt` | Navigation routes and graph |
| `data/repository/BeerRepository.kt` | Repository interface |
| `data/repository/MockBeerRepository.kt` | Mock data (6 experiences) |
| `data/model/BeerExperience.kt` | Beer experience data model |
| `ui/home/HomeScreen.kt` | Home list with search |
| `ui/home/HomeViewModel.kt` | Home state and search logic |
| `ui/detail/DetailScreen.kt` | Detail view with booking CTA |
| `ui/detail/DetailViewModel.kt` | Detail state loading |
| `ui/theme/Color.kt` | Color definitions |
| `ui/theme/Theme.kt` | Material 3 theme |
| `ui/theme/Type.kt` | Typography |

## Git Notes

- Push after every commit to keep the remote in sync: `git push`
- Commit messages should explain what changed and why
- Keep commits focused on single logical changes
