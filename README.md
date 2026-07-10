# GymShark Product Browser

Android take-home project that displays GymShark training products from a remote JSON endpoint, persists them locally, and supports product detail navigation.

## Features

- Product listing with image, title, colour, price, and labels
- Product detail screen with image carousel and description
- Pull-to-refresh support
- Offline-friendly local cache using Room
- Error handling for network, serialization, and local database failures

## Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- Hilt
- Retrofit and OkHttp
- Gson
- Room
- Kotlin Coroutines and Flow
- Coil
- Jetpack Navigation Compose
- JUnit, MockK, and Coroutines Test

## Architecture

The app uses a simple layered architecture:

- `ui`: Compose screens, UI models, ViewModels, and navigation
- `domain`: repository contract and domain models
- `data`: Retrofit API, DTOs, Room entities, DAO, data sources, and repository implementation
- `di`: Hilt modules
- `core`: shared result and error types

The product list observes Room as the source of truth. Refreshing fetches remote data, maps it into local entities, replaces the cached products, and lets the UI update through Flow.

## Data Flow

1. `ProductViewModel` observes `ProductRepository.observeProducts()`.
2. `ProductRepositoryImpl` maps Room entities to domain models.
3. On refresh, `RemoteDataSource` fetches products from the GymShark endpoint.
4. DTOs are mapped to Room entities.
5. Room emits the updated product list.
6. UI maps domain models into display-ready `ProductUIModel`s.

## Running the App

Requirements:

- Android Studio
- JDK 17+
- Android SDK installed

Commands:

```bash
./gradlew assembleDebug
./gradlew testDebugUnitTest
./gradlew lintDebug
```

To run the app, open the project in Android Studio and launch the `app` configuration on an emulator or physical device.

## Testing

Current test coverage focuses on the repository layer:

- observing product lists from local cache
- observing a single product
- refreshing from remote and saving locally
- remote failure handling
- local persistence failure handling

Useful future coverage:

- ViewModel state transitions
- DTO, entity, domain, and UI mapper edge cases
- Room DAO replacement behavior
- Compose UI states for loading, error, cached data, and empty results

## Error Handling

The app wraps operations in `DataResult` and maps failures into `AppError`.

Handled cases include:

- no internet
- timeout
- HTTP errors
- serialization errors
- database failures
- disk full errors

When refresh fails and cached products exist, the app keeps showing cached data and displays an error banner.

## Tradeoffs

- The app is single-module because the project scope is small. For a larger production app, this could be split into `core`, `data`, `domain`, and feature modules.
- Room currently replaces the full product table on refresh. This is simple and works for the current payload size, but a production app might use incremental sync or stale-cache metadata.
- Product detail data is loaded from the local cache rather than making a second network request. This keeps navigation fast and supports offline use.
- Formatting logic lives in a UI mapper so domain models remain display-agnostic.

## Known Limitations

- Database migrations should be made explicit before production use.
- Mapper behavior around malformed products can be made more resilient.
- Some UI strings should be moved into string resources.
- Test coverage should be expanded around ViewModels, Room, and Compose UI.
- The design is intentionally simple and could be polished further for accessibility, typography, and responsive layouts.

## What I Would Improve With More Time

- Add explicit Room migrations and schema export.
- Expand unit tests around ViewModel state transitions and mapper edge cases.
- Add DAO tests with an in-memory Room database.
- Add Compose UI tests for loading, error, cached-data, and empty states.
- Improve accessibility labels, string resources, and responsive layout polish.
- Make refresh handling more resilient when individual products in the payload are malformed.
