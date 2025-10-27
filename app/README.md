# Chicken Farm Management - Android App

Aplikasi Android untuk manajemen breeding ayam dengan Kotlin dan Jetpack Compose. Database menggunakan Google Sheets yang sama dengan versi website.

## Fitur

- **Kelola Ayam Indukan** - Tambah, edit, hapus data ayam pejantan dan betina
- **Kelola Breeding** - Catat data perkawinan dan hasil breeding
- **Kelola Ayam Anakan** - Pantau perkembangan ayam anakan
- **Tanpa Login** - Langsung CRUD data tanpa autentikasi
- **Database Google Sheets** - Sinkronisasi dengan database spreadsheet yang sama dengan website
- **UI Modern** - Material Design 3 dengan Jetpack Compose

## Teknologi

- **Kotlin** - Programming language
- **Jetpack Compose** - Modern UI toolkit
- **Material Design 3** - Design system
- **Retrofit** - HTTP client untuk API calls
- **Coroutines & Flow** - Asynchronous programming
- **ViewModel** - State management
- **Navigation Component** - Screen navigation
- **MVVM Architecture** - Clean architecture pattern

## Setup

### 1. Prerequisites

- Android Studio Hedgehog atau lebih baru
- JDK 11 atau lebih tinggi
- Android SDK API Level 24+ (Android 7.0)

### 2. Konfigurasi Google Apps Script

Aplikasi ini menggunakan backend Google Apps Script yang sama dengan website. Pastikan Anda sudah setup backend dengan mengikuti panduan di root project.

### 3. Update API URL

Edit file `app/src/main/java/com/example/my_chicken_farm_android/data/api/RetrofitInstance.kt`:

```kotlin
private const val BASE_URL = "https://script.google.com/macros/s/YOUR_SCRIPT_ID/"
```

Ganti `YOUR_SCRIPT_ID` dengan deployment URL Google Apps Script Anda.

### 4. Build & Run

1. Open project di Android Studio
2. Sync Gradle
3. Run aplikasi (Shift + F10)

## Struktur Project

```
app/src/main/java/com/example/my_chicken_farm_android/
├── data/
│   ├── api/
│   │   ├── ApiService.kt           # Retrofit API interface
│   │   └── RetrofitInstance.kt     # Retrofit setup
│   ├── model/
│   │   ├── AyamIndukan.kt          # Data model ayam indukan
│   │   ├── Breeding.kt             # Data model breeding
│   │   ├── AyamAnakan.kt           # Data model ayam anakan
│   │   └── ApiResponse.kt          # API response wrapper
│   └── repository/
│       └── ChickenRepository.kt    # Data repository
├── viewmodel/
│   ├── AyamIndukanViewModel.kt     # ViewModel ayam indukan
│   ├── BreedingViewModel.kt        # ViewModel breeding
│   └── AyamAnakanViewModel.kt      # ViewModel ayam anakan
├── ui/
│   ├── screens/
│   │   ├── DashboardScreen.kt      # Dashboard utama
│   │   ├── AyamIndukanListScreen.kt
│   │   ├── AyamIndukanFormScreen.kt
│   │   ├── BreedingListScreen.kt
│   │   ├── BreedingFormScreen.kt
│   │   ├── AyamAnakanListScreen.kt
│   │   └── AyamAnakanFormScreen.kt
│   ├── navigation/
│   │   ├── Screen.kt               # Navigation routes
│   │   └── NavGraph.kt             # Navigation graph
│   └── theme/                      # Material Design theme
└── MainActivity.kt                 # Main entry point
```

## Dependencies yang Perlu Ditambahkan

Tambahkan dependencies berikut ke `app/build.gradle.kts`:

```kotlin
dependencies {
    // ... existing dependencies

    // Networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.gson)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Image Loading (optional, untuk future features)
    implementation(libs.coil.compose)

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
}
```

Dependencies sudah didefinisikan di `gradle/libs.versions.toml`.

## API Endpoints

Aplikasi menggunakan endpoints Google Apps Script:

### GET Requests
- `GET /exec?path=ayam_induk&userEmail=android@app.com`
- `GET /exec?path=breeding&userEmail=android@app.com`
- `GET /exec?path=ayam_anakan&userEmail=android@app.com`

### POST Requests
- `POST /exec` dengan body:
  - `action: "add_ayam_induk"` - Tambah ayam indukan
  - `action: "update_ayam_induk"` - Update ayam indukan
  - `action: "delete_ayam_induk"` - Hapus ayam indukan
  - `action: "add_breeding"` - Tambah breeding
  - `action: "update_breeding"` - Update breeding
  - `action: "delete_breeding"` - Hapus breeding
  - `action: "add_ayam_anakan"` - Tambah ayam anakan
  - `action: "update_ayam_anakan"` - Update ayam anakan
  - `action: "delete_ayam_anakan"` - Hapus ayam anakan

## User Email

Aplikasi menggunakan email default `android@app.com` untuk semua operasi. Ini untuk membedakan data dari Android app dengan data dari website. Anda bisa mengubahnya di:

```kotlin
// app/src/main/java/com/example/my_chicken_farm_android/data/repository/ChickenRepository.kt
private val userEmail = "android@app.com"  // Ganti sesuai kebutuhan
```

## Build APK

### Debug APK
```bash
./gradlew assembleDebug
```

APK akan tersimpan di: `app/build/outputs/apk/debug/app-debug.apk`

### Release APK
```bash
./gradlew assembleRelease
```

APK akan tersimpan di: `app/build/outputs/apk/release/app-release.apk`

## Troubleshooting

### Error: "Unable to resolve dependency"
- Pastikan Gradle sync berhasil
- Check koneksi internet
- Clean & rebuild project: Build > Clean Project, Build > Rebuild Project

### Error: "Failed to connect to Google Sheets"
- Pastikan URL Google Apps Script sudah benar
- Pastikan Google Apps Script sudah di-deploy dengan akses "Anyone"
- Check internet permission di AndroidManifest.xml
- Test URL di browser atau Postman

### Error: "Data tidak muncul"
- Check Logcat untuk error messages
- Pastikan response format dari Google Sheets sesuai dengan data model
- Test API endpoint dengan Postman

## Testing

Untuk testing API connection, Anda bisa menggunakan Logcat dengan filter:
```
tag:ChickenRepository
```

Atau gunakan Postman untuk test API endpoint:
```
GET https://script.google.com/macros/s/YOUR_SCRIPT_ID/exec?path=ayam_induk&userEmail=android@app.com
```

## Future Improvements

- [ ] Add data validation
- [ ] Add loading states dengan shimmer effect
- [ ] Add pull-to-refresh
- [ ] Add search & filter functionality
- [ ] Add offline mode dengan local database (Room)
- [ ] Add image upload untuk foto ayam
- [ ] Add multi-user support dengan login
- [ ] Add export data to PDF/Excel
- [ ] Add push notifications
- [ ] Add dark mode support

## License

MIT License - Bebas digunakan untuk proyek pribadi maupun komersial.
