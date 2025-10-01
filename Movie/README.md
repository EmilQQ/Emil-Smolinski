Film library is a simple Android application built with **Kotlin** and **Jetpack Compose** that allows users to manage their personal collection of movies, TV shows, and documentaries.

Features:
-  **Film list view** with:
  - Title, release date, category, status (watched / not watched)
  - Poster thumbnail (chosen from gallery)
  - Automatic sorting by release date
  - Filtering by category and status
  - Item removal on long-press
  - Summary counter of displayed items

-  **Add / Edit film**
  - Title, release date (with date picker)
  - Category selection (Movie, Series, Documentary)
  - Poster selection from device gallery
  - Validation rules (e.g. title cannot be empty, release date not too far in the future, rating required if marked as watched)

-  **Details view**
  - Displays full film details
  - Option to open edit mode if film is not watched yet

-  **Mark as watched**
  - Change film status to "watched"
  - Add rating and optional comment

-  **Modern UI**
  - Built with Jetpack Compose
  - Responsive, clean layout
  - Icons and intuitive navigation
  - Strings extracted into `strings.xml` for localization

Tech Stack:
- **Language:** Kotlin
- **UI:** Jetpack Compose (Material 3)
- **State management:** `mutableStateOf`, `StateFlow`
- **Navigation:** Navigation Compose
- **Image loading:** Coil (`rememberAsyncImagePainter`)
- **Architecture:** MVVM
