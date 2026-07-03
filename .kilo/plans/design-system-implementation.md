# Design System Implementation Plan — BeerAirB v0.1.0

This plan covers the full redesign to match the spec: new amber/golden color palette, Montserrat font, restructured Home and Detail screens with category filters, horizontal scroll sections, bottom navigation, and amenity badges.

---

## 1. Design System — Theme Overhaul

### 1.1 Color.kt — Replace palette
- **Primary (accent)**: `AmberPrimary = Color(0xFFE67E22)`, `AmberLight = Color(0xFFF39C12)`
- **Background**: `White = Color(0xFFFFFFFF)`, `CreamBg = Color(0xFFF9F6F0)`
- **Text main**: `DarkToasted = Color(0xFF1A1105)`
- **Text secondary**: `ClayGray = Color(0xFF786E64)`
- **Badge colors**:
  - `CreamSoft = Color(0xFFFDF2E9)` (maridaje/comida)
  - `GreenLight = Color(0xFFE8F8F5)` (pet friendly)
  - `GoldPale = Color(0xFFFEF9E7)` (barril/tipo cerveza)
  - `BlueGray = Color(0xFFEAECEE)` (wifi/trabajo)
- Remove old `Purple80`, `PurpleGrey80`, `Pink80`, `Purple40`, `PurpleGrey40`, `Pink40`

### 1.2 Theme.kt — Update color schemes
- Replace `primary`, `secondary`, `tertiary`, `background`, `surface`, `onPrimary`, etc. with amber/golden scheme
- Keep dynamic color fallback but use custom scheme as default
- Disable dynamic color by default (set `dynamicColor = false`) to enforce brand palette

### 1.3 Type.kt — Add Montserrat font
- Add Montserrat TTF files to `res/font/` (montserrat_regular.ttf, montserrat_bold.ttf, montserrat_semibold.ttf)
- Define `MontserratFontFamily = FontFamily(...)`
- Update `Typography` to use Montserrat for all text styles
- Set fallback to `FontFamily.Default`

---

## 2. Model Updates

### 2.1 BeerExperience.kt — Add new fields
Add fields required by the spec:
- `distanceKm: Double` — distance from user
- `dateRange: String` — e.g. "7-12 Oct"
- `imageUrl: String` — URL string (for now placeholder)
- `isFavorite: Boolean` — favorite toggle
- `amenities: List<BeerAmenity>` — list of included services

### 2.2 New data class: BeerAmenity.kt
```
data class BeerAmenity(
    val icon: ImageVector,       // Material icon
    val label: String,           // e.g. "Welcome tasting", "On-site tap", "Accommodation"
    val badgeColor: Color        // references the pastel badge colors
)
```

### 2.3 New data class: BeerCategory.kt
```
enum class BeerCategoryType(val displayName: String, val icon: ImageVector) {
    MICROBREWERIES("Microbreweries", Icons.Default.Factory),
    BEER_GLAMPING("Beer Glamping", Icons.Default.Hotel),
    TAPROOMS("Taprooms", Icons.Default.LocalBar),
    HOP_FARMS("Hop Farms", Icons.Default.Park)
}
```

### 2.4 MockBeerRepository.kt — Update mock data
- Replace existing 6 entries with new ones that include `distanceKm`, `dateRange`, `imageUrl`, `isFavorite`, `amenities`
- Add realistic data for distance (km), date ranges, and amenity lists per experience

---

## 3. Home Screen — Full Restructure

### 3.1 HomeScreen.kt — Replace layout
Current: single `LazyColumn` with `OutlinedTextField` + `ExperienceCard` list.

New layout (scrollable, not `LazyColumn` for sections flexibility):

```
Column {
    // 3.1.1 Search Bar
    SearchBar(
        placeholder = "¿Qué estilo de cerveza quieres descubrir hoy?"
    )

    // 3.1.2 Category Selector (horizontal scroll)
    CategorySelector(
        categories = BeerCategoryType.entries,
        onCategorySelected = { ... }
    )

    // 3.1.3 Section "Cerca de ti / Nearby Taprooms" (horizontal LazyRow)
    SectionHeader(title = "Nearby Taprooms / Experiences")
    NearbyTaproomsRow(
        experiences = nearbyExperiences,
        onFavoriteToggle = { ... },
        onTap = { onExperienceClick(it) }
    )

    // 3.1.4 Bottom promo banner
    ExploreBanner(
        onStartClick = { ... }
    )
}
```

### 3.2 New composables

#### SearchBar
- `OutlinedTextField` with amber accent color, rounded shape
- Placeholder text: "¿Qué estilo de cerveza quieres descubrir hoy?"
- Search icon at start

#### CategorySelector
- Horizontal `LazyRow` of chips/pills
- Each chip has icon + label, rounded shape, amber selected state
- Categories: Microbreweries, Beer Glamping, Taprooms, Hop Farms

#### NearbyTaproomCard (replaces old ExperienceCard)
- Image header with arched top cut (use `ClipPath` or custom shape)
- Favorite icon overlay (heart, toggles `isFavorite`)
- Title, distance subtitle, date range + price subtitle
- Rounded corners, elevation shadow

#### ExploreBanner
- Small container card at bottom
- Text: "EXPLORE THE BEER WORLD. Sumérgete en culturas cerveceras de forma responsable."
- Amber pill button: "Let's Start"
- Circular image cutout on left side

### 3.3 HomeViewModel.kt — Update
- Add `selectedCategory: StateFlow<BeerCategoryType?>` 
- Add `nearbyExperiences` filtered list
- Add `onCategorySelected(category)` and `onFavoriteToggle(id)` methods
- Keep existing search functionality but adapt to new model fields

---

## 4. Detail Screen — Full Restructure

### 4.1 DetailScreen.kt — Replace layout
Current: `Scaffold` + `TopAppBar` + scrollable `DetailContent`.

New layout:
```
Scaffold(// ... no top bar — full-bleed image header) {
    Column {
        // 4.1.1 Full-width hero image with floating back + fav buttons
        HeroImageHeader(
            imageRes = experience.imageRes,
            onBackClick = onBackClick,
            isFavorite = experience.isFavorite,
            onFavoriteToggle = { ... }
        )

        // 4.1.2 Info block
        TitleRatingBlock(
            title = experience.title,
            rating = experience.rating,
            reviewCount = 304, // mock
            description = experience.description
        )

        // 4.1.3 Amenities ("Including") — 4 badges
        AmenitiesRow(amenities = experience.amenities)

        // 4.1.4 Date picker calendar widget (amber accent)
        DateSelector()

        // 4.1.5 Bottom price + CTA
        BookingBar(price = experience.pricePerPerson)
    }
}
```

### 4.2 New composables

#### HeroImageHeader
- Full-width image (height ~300dp)
- Floating circular white buttons: back arrow (top-left), fav heart (top-right)
- Curved bottom edge (use custom `Shape`)

#### TitleRatingBlock
- Title text (DarkToasted, headline, bold)
- Rating row: stars + "4.5 ⭐ 304 Reviews de otros beer lovers"
- Description with "Read More..." amber link

#### AmenitiesRow
- Horizontal row of 4 amenity badges
- Each badge: icon (from `BeerAmenity`) with pastel colored background
- Labels: "Cata de bienvenida", "Grifo propio", "Hospedaje incluido", "WiFi / Pet Friendly"

#### DateSelector
- Clean date range selector (simplified calendar widget)
- Two buttons: Check-in / Check-out with amber accent when selected
- Shows selected range

#### BookingBar
- Fixed bottom card (or inside scroll)
- Shows total price
- "Reservar ahora" amber button

### 4.3 DetailViewModel.kt — Update
- Add `favoriteExperiences` state
- Add `toggleFavorite(id)` method
- No other structural changes needed

---

## 5. Bottom Navigation Bar

### 5.1 New file: MainScaffold.kt
Wrap `NavGraph` in a `Scaffold` with `NavigationBar`:

```
Scaffold(
    bottomBar = {
        NavigationBar(containerColor = CreamBg) {
            NavigationBarItem(icon = Home, label = "Explorar", selected, onClick)
            NavigationBarItem(icon = Favorites, label = "Favoritos", selected, onClick)
            // FAB-style center button (amber circle with beer icon)
            NavigationBarItem(icon = BeerIcon, label = "", ...)
            NavigationBarItem(icon = Messages, label = "Mensajes", ...)
            NavigationBarItem(icon = Profile, label = "Perfil", ...)
        }
    }
) {
    NavGraph(navController)
}
```

### 5.2 NavGraph.kt — Update
- Add routes for Favorites, Messages, Profile (placeholder screens for now)
- Selected state tracking for bottom nav

### 5.3 MainActivity.kt — Update
- Replace `Surface { NavGraph(...) }` with `MainScaffold`

---

## 6. New Files Summary

| File | Purpose |
|------|---------|
| `ui/home/CategorySelector.kt` | Horizontal category chip row |
| `ui/home/NearbyTaproomCard.kt` | Redesigned card with arched image, fav, distance |
| `ui/home/ExploreBanner.kt` | Bottom promo banner |
| `ui/detail/HeroImageHeader.kt` | Full-bleed image with floating buttons |
| `ui/detail/TitleRatingBlock.kt` | Title + rating + description block |
| `ui/detail/AmenitiesRow.kt` | 4 amenity badges |
| `ui/detail/DateSelector.kt` | Date range picker |
| `ui/detail/BookingBar.kt` | Price + CTA bar |
| `ui/navigation/MainScaffold.kt` | Scaffold with bottom nav bar |
| `ui/home/FavoritesScreen.kt` | Placeholder favorites screen |
| `ui/home/MessagesScreen.kt` | Placeholder messages screen |
| `data/model/BeerAmenity.kt` | Amenity data class |
| `res/font/montserrat_regular.ttf` | Montserrat font file |
| `res/font/montserrat_bold.ttf` | Montserrat bold font file |
| `res/font/montserrat_semibold.ttf` | Montserrat semibold font file |

---

## 7. Modified Files Summary

| File | Changes |
|------|---------|
| `ui/theme/Color.kt` | Complete color replacement (amber palette, badge colors) |
| `ui/theme/Theme.kt` | New color schemes, disable dynamic color |
| `ui/theme/Type.kt` | Montserrat font family, full Typography definition |
| `data/model/BeerExperience.kt` | Add distanceKm, dateRange, imageUrl, isFavorite, amenities |
| `data/repository/MockBeerRepository.kt` | Replace all mock data with new fields |
| `ui/home/HomeScreen.kt` | Full layout replacement |
| `ui/detail/DetailScreen.kt` | Full layout replacement |
| `ui/home/HomeViewModel.kt` | Add category filtering, favorites |
| `ui/detail/DetailViewModel.kt` | Add favorites support |
| `ui/navigation/NavGraph.kt` | Add placeholder routes |
| `MainActivity.kt` | Use MainScaffold instead of direct NavGraph |
| `AGENTS.md` | Update library versions |
| `CLAUDE.md` | Update architecture docs |

---

## 8. Implementation Order

1. **Theme**: Color.kt → Theme.kt → Type.kt + font assets (foundation layer)
2. **Model**: BeerAmenity.kt → BeerExperience.kt (new fields) → MockBeerRepository.kt (new data)
3. **Bottom Nav**: MainScaffold.kt → NavGraph.kt updates → MainActivity.kt
4. **Home components**: CategorySelector → NearbyTaproomCard → ExploreBanner → HomeScreen.kt → HomeViewModel.kt
5. **Detail components**: HeroImageHeader → TitleRatingBlock → AmenitiesRow → DateSelector → BookingBar → DetailScreen.kt → DetailViewModel.kt
6. **Screenshot tests**: Update existing tests, record new baselines
7. **Docs**: Update AGENTS.md, CLAUDE.md, README.md, tag v0.1.0

---

## 9. Versioning

This is a `feat` (major feature set) → bump **minor** from `0.0.2` → `0.1.0`.

Tag: `v0.1.0`
