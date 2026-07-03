# Detail Screen Parallax + Expand Animation Plan — v0.3.0

## Overview

Three improvements to the DetailScreen:
1. **Shared element expand animation** — when tapping a NearbyTaproomCard on Home, the image hero expands into the detail hero
2. **Parallax collapsing header** — as the user scrolls down, the hero image collapses with a parallax effect
3. **Alignment polish** — fix padding/layout consistency across detail sections

---

## 1. Shared Element Expand Animation (Card → Detail Hero)

### The challenge
Compose has no built-in shared element transition. We can't easily animate one composable from one screen to another across a navigation boundary without a library.

### Approach: Custom enter transition + hero scale-up
Instead of a true shared element transition (which requires `Modifier.shareElement()` or a library like `compose-shared-element`), we do:

1. **Add a crossfade + scale enter transition** on the Detail composable route in NavHost
2. **HeroImageHeader starts small and scales up** using `AnimatedVisibility` or `animateContentSize`
3. The card image on Home gets a brief highlight before navigation

**Implementation for NavGraph.kt:**
```kotlin
composable(
    route = Screen.Detail.route,
    arguments = listOf(navArgument("id") { type = NavType.StringType }),
    enterTransition = {
        slideInHorizontally(initialOffsetX = { it }) + fadeIn(animationSpec = tween(300))
    },
    exitTransition = {
        slideOutHorizontally(targetOffsetX = { -it / 3 }) + fadeOut(animationSpec = tween(200))
    }
)
```

**Implementation for HeroImageHeader.kt:**
- Wrap the image in `AnimatedVisibility(visible = true, enter = scaleIn + fadeIn)`
- Use `scaleIn(animationSpec = tween(400, delayMillis = 50), initialScale = 0.85f)` + `fadeIn(tween(300))`

**Implementation for NearbyTaproomCard.kt:**
- Add a brief scale-down flash on click using `Modifier.pointerInput`
- Simple: use `Modifier.graphicsLayer { alpha = 0.9f; scaleX = 0.97f; scaleY = 0.97f }` while pressed

### Files to modify:
- `NavGraph.kt` — add `enterTransition`/`exitTransition` to the Detail composable
- `HeroImageHeader.kt` — wrap content in `AnimatedVisibility` with `scaleIn` + `fadeIn`
- `NearbyTaproomCard.kt` — add click press feedback via `interactionSource`

---

## 2. Parallax Collapsing Header

### Implementation: Custom scroll-based header height
Use `nestedScroll` and `Box` to create a parallax effect where:
- Hero image height starts at 300dp
- As the user scrolls down, the image shrinks (min 120dp)
- The image translates slower than the scroll (parallax factor 0.5x)
- Content scrolls normally below

### How it works:
- Replace `DetailContent`'s `Column + verticalScroll` with a custom layout using `nestedScrollConnection`
- The `HeroImageHeader` lives outside the scroll (in a parent `Box`)
- The scrollable content has a `topPadding` equal to the hero height
- A `NestedScrollConnection` tracks scroll offset and adjusts hero height + translation

### Implementation for DetailScreen.kt:
```kotlin
@Composable
fun DetailContent(...) {
    // State
    var headerHeight by remember { mutableFloatStateOf(300f) }
    val minHeaderHeight = 120f
    val maxHeaderHeight = 300f
    val collapseRange = maxHeaderHeight - minHeaderHeight
    
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // When scrolling up (negative y), expand header
                // When scrolling down (positive y), collapse header
                val delta = available.y
                val newHeight = (headerHeight - delta).coerceIn(minHeaderHeight, maxHeaderHeight)
                val consumed = headerHeight - newHeight
                headerHeight = newHeight
                return Offset(0f, consumed)
            }
            
            override suspend fun onPreFling(available: Velocity): Velocity {
                // Snap to min or max based on velocity
                if (headerHeight > (maxHeaderHeight + minHeaderHeight) / 2) {
                    headerHeight = maxHeaderHeight
                } else {
                    headerHeight = minHeaderHeight
                }
                return Velocity.Zero
            }
        }
    }
    
    Box(modifier = modifier.fillMaxSize()) {
        // Hero image (fixed, outside scroll)
        HeroImageHeader(
            imageUrl = ...,
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight.dp),
            ...
        )
        
        // Scrollable content (overlaps hero when collapsed)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .nestedScroll(nestedScrollConnection)
                .padding(top = headerHeight.dp) // push content below hero
        ) {
            // Spacer for initial offset
            Spacer(modifier = Modifier.height((maxHeaderHeight - headerHeight).dp))
            
            TitleRatingBlock(...)
            AmenitiesRow(...)
            DateSelector(...)
            BookingBar(...)
        }
    }
}
```

### Additional visual polish:
- As header collapses, the title text fades into the collapsed toolbar area (simplified: keep title in content, just collapse image)
- Floating back/fav buttons stay visible but adjust position slightly

---

## 3. Alignment Polish

| Component | Current | Fix |
|-----------|---------|-----|
| TitleRatingBlock | `padding(horizontal = 20.dp)` | Change to `24.dp` for consistency |
| AmenitiesRow | `padding(horizontal = 20.dp)` | Change to `24.dp` |
| DateSelector | `padding(horizontal = 20.dp)` | Change to `24.dp` |
| BookingBar | `padding(horizontal = 20.dp)` | Change to `24.dp` |
| Amenities badge text | 2-line max with truncation | Increase badge `labelMedium` font, reduce `maxLines` to 2 |
| DateSelector cards | Equal weight | Keep weight, add minimum height |

---

## 4. Files Modified

| File | Changes |
|------|---------|
| `NavGraph.kt` | Add `enterTransition`/`exitTransition` to Detail composable |
| `HeroImageHeader.kt` | Wrap in `AnimatedVisibility` with `scaleIn + fadeIn` |
| `DetailScreen.kt` | Restructure to `Box` with parallax `NestedScrollConnection`, manage header height state |
| `TitleRatingBlock.kt` | `padding(horizontal = 24.dp)` |
| `AmenitiesRow.kt` | `padding(horizontal = 24.dp)`, adjust badge label font |
| `DateSelector.kt` | `padding(horizontal = 24.dp)` |
| `BookingBar.kt` | `padding(horizontal = 24.dp)` |

No new files needed.

---

## 5. Testing

- Update `DetailScreenScreenshotTest` — `DetailContent` signature changes (needs `imageUrl` passed correctly). The test uses `DetailContent(experience = mockExperience)` which will need adjusting if the composable signature changes.
- Record new screenshot baselines under `screenshots/v0.3.0/`

---

## 6. Versioning

This is a `feat` (new animation + parallax) → bump **minor** from `0.2.2` → `0.3.0`.

Tag: `v0.3.0`
