# BeerAirB - MVP Plan

## Concept
Airbnb-style app but for **cerveza artesanal experiences**: users can browse, view details, and "rent" beer experiences (taprooms, brewery tours, tasting flights, etc.).

## Architecture
- **Pattern**: MVVM + Repository
- **UI**: Jetpack Compose + Material3
- **Navigation**: Jetpack Navigation Compose
- **State**: ViewModel + StateFlow
- **Backend**: Mock repository (in-memory data)

## Package Structure
```
com.mx.beerairb/
├── data/
│   ├── model/
│   │   └── BeerExperience.kt        # Data class
│   └── repository/
│       └── BeerRepository.kt         # Interface + Mock impl
├── ui/
│   ├── theme/                        # Already exists
│   ├── navigation/
│   │   └── NavGraph.kt               # Navigation routes
│   ├── home/
│   │   ├── HomeScreen.kt             # Listing screen
│   │   └── HomeViewModel.kt          # State for listing
│   └── detail/
│       ├── DetailScreen.kt           # Detail screen
│       └── DetailViewModel.kt        # State for detail
└── MainActivity.kt                   # Updated entry point
```

## Step-by-step Implementation

### 1. Add dependencies to `libs.versions.toml`
- `androidx-navigation-compose` for navigation
- `androidx-lifecycle-viewmodel-compose` for ViewModel in Compose

### 2. Add deps to `app/build.gradle.kts`
- implementation(libs.androidx.navigation.compose)
- implementation(libs.androidx.lifecycle.viewmodel.compose)

### 3. Create `BeerExperience` data class
Fields:
- `id: String`
- `title: String`
- `hostName: String`
- `description: String`
- `pricePerPerson: Double`
- `rating: Double`
- `imageRes: Int` (drawable resource placeholder)
- `location: String`
- `duration: String`
- `category: String` (e.g. "Taproom", "Brewery Tour", "Tasting")

### 4. Create `BeerRepository` interface + `MockBeerRepository`
- Interface with `getAllExperiences(): List<BeerExperience>`
  and `getExperienceById(id: String): BeerExperience?`
- Mock impl returns ~6 hardcoded beer experiences with placeholder drawable names

### 5. Create placeholder drawables
Since we cannot use real images, use solid color drawable XML files:
- `ic_beer_1.xml` through `ic_beer_6.xml` in `res/drawable/`
  (simple vector drawables with different colors)

### 6. Create Navigation setup
- Define sealed class `Screen` with routes: `Home`, `Detail/{id}`
- Create `NavGraph.kt` composable connecting both screens

### 7. Create `HomeViewModel`
- Holds `List<BeerExperience>` state loaded from repository
- Exposes `StateFlow<List<BeerExperience>>`
- Search/filter by text query

### 8. Create `HomeScreen`
- Search bar at top
- LazyColumn of beer experience cards
- Each card shows: image placeholder, title, host, rating, price, category chip
- Card click navigates to Detail screen

### 9. Create `DetailViewModel`
- Takes `experienceId` via SavedStateHandle
- Loads single experience from repository
- Exposes `StateFlow<BeerExperience?>`

### 10. Create `DetailScreen`
- Hero image placeholder
- Title, host name, location, rating
- Description section
- Price and duration info
- Category chip
- "Reservar ahora" button (non-functional, just UI)

### 11. Update `MainActivity.kt`
- Replace Greeting composable with `BeerAirBApp()` entry point
- Set up NavHost with NavGraph

### 12. Update theme colors (optional)
- Add beer-themed colors (amber, brown tones) for a more relevant look

## Key Design Decisions
- No Hilt/Dagger — keeping it simple for MVP. ViewModels instantiated manually with repository passed via factory or default.
- No Room — mock data only.
- Single Activity architecture.
- All Compose — no XML layouts beyond the theme.

## Files to Create (12)
1. `data/model/BeerExperience.kt`
2. `data/repository/BeerRepository.kt`
3. `data/repository/MockBeerRepository.kt`
4. `ui/navigation/NavGraph.kt`
5. `ui/home/HomeViewModel.kt`
6. `ui/home/HomeScreen.kt`
7. `ui/detail/DetailViewModel.kt`
8. `ui/detail/DetailScreen.kt`
9. `res/drawable/ic_beer_1.xml`
10. `res/drawable/ic_beer_2.xml`
11. `res/drawable/ic_beer_3.xml`
12. `res/drawable/ic_beer_4.xml`

## Files to Modify (4)
1. `gradle/libs.versions.toml` — add navigation + viewmodel deps
2. `app/build.gradle.kts` — add implementation lines
3. `app/src/main/java/com/mx/beerairb/MainActivity.kt` — update content
4. `app/src/main/java/com/mx/beerairb/ui/theme/Color.kt` — add beer colors

## Validation
- Build: `./gradlew assembleDebug`
- No tests for MVP (keeping scope lean)
