Diary App is a mobile application built with Kotlin and Jetpack Compose, designed as a personal diary where users can create, edit, and manage their entries.

Each entry can include:
- Title and description
- Location (with optional automatic GPS autofill)
- Photo (taken from gallery or placeholder image)
- Audio (optional voice note attachment)

Features
- Add and edit diary entries with title, description, and location
- Attach photos from device gallery
- Optional audio recordings (voice notes)
- Automatic location suggestion using GPS and Geocoder
- Clean and reactive UI built fully with Jetpack Compose
- MVVM architecture for state management
- Dependency Injection with Hilt for cleaner code and testability
- Room Database (SQLite) planned for data persistence

Tech Stack
- Language: Kotlin
- UI Toolkit: Jetpack Compose (Material 3)
- Architecture: MVVM
- Dependency Injection: Hilt
- Image loading: Coil
- Location services: FusedLocationProviderClient + Geocoder
- Database: Room (SQLite)
