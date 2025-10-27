# Android App Setup Guide

Panduan lengkap untuk setup aplikasi Android Chicken Farm Management.

## Prerequisites

- Android Studio Hedgehog (2023.1.1) atau lebih baru
- JDK 11 atau lebih tinggi
- Android SDK API Level 24+ (Android 7.0 Nougat)
- Google Apps Script backend sudah di-deploy

## Step 1: Open Project

1. Open Android Studio
2. File > Open > Pilih root folder project
3. Wait for Gradle sync

## Step 2: Tambah Dependencies

**PENTING**: Anda perlu menambahkan dependencies ke file `app/build.gradle.kts`

Buka file `app/build.gradle.kts` dan pastikan bagian `dependencies` terlihat seperti ini:

```kotlin
dependencies {
    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // ✅ TAMBAHKAN BAGIAN INI ✅
    // Networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.gson)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Image Loading
    implementation(libs.coil.compose)

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    // ✅ SAMPAI SINI ✅

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
```

**Note**: File `gradle/libs.versions.toml` sudah saya update dengan semua library versions yang diperlukan.

## Step 3: Konfigurasi Google Apps Script URL

Buka file `app/src/main/java/com/example/my_chicken_farm_android/data/api/RetrofitInstance.kt`

Ubah baris:
```kotlin
private const val BASE_URL = "https://script.google.com/macros/s/YOUR_SCRIPT_ID/"
```

Ganti `YOUR_SCRIPT_ID` dengan URL deployment Google Apps Script Anda.

**Cara mendapatkan URL:**
1. Buka Google Apps Script editor
2. Klik Deploy > Manage deployments
3. Copy Web App URL (contoh: `https://script.google.com/macros/s/AKfycbx.../exec`)
4. Ambil bagian setelah `/s/` dan sebelum `/exec`
5. Paste di `BASE_URL`

Contoh lengkap:
```kotlin
private const val BASE_URL = "https://script.google.com/macros/s/AKfycbxABC123xyz456/"
```

## Step 4: Sync Gradle

1. Klik "Sync Now" yang muncul di banner atas
2. Atau: File > Sync Project with Gradle Files
3. Wait hingga selesai (biasanya 1-3 menit untuk pertama kali)

Jika ada error:
- Check koneksi internet
- Build > Clean Project
- Build > Rebuild Project
- Invalidate Caches: File > Invalidate Caches / Restart

## Step 5: Run Aplikasi

### Run di Emulator
1. Tools > Device Manager
2. Create Virtual Device (pilih Pixel 5 atau lebih baru)
3. Download system image jika belum ada
4. Start emulator
5. Klik Run (hijau) atau tekan Shift + F10

### Run di Device Fisik
1. Enable Developer Options di Android device:
   - Settings > About Phone
   - Tap "Build Number" 7 kali
2. Enable USB Debugging:
   - Settings > Developer Options > USB Debugging
3. Connect device via USB
4. Allow USB debugging pada popup
5. Pilih device di Android Studio
6. Klik Run

## Step 6: Test Aplikasi

1. Aplikasi akan membuka Dashboard
2. Test navigasi ke setiap menu:
   - Ayam Indukan
   - Breeding
   - Ayam Anakan
3. Test CRUD operations:
   - Tambah data baru
   - Edit data
   - Hapus data
4. Check apakah data tersimpan di Google Sheets

## Troubleshooting

### Error: "Unresolved reference"
**Solusi**: Pastikan semua dependencies sudah ditambahkan di `app/build.gradle.kts` dan Gradle sync berhasil.

### Error: "Failed to load data"
**Solusi**:
- Check Google Apps Script URL sudah benar
- Check internet connection
- Check Logcat untuk error details:
  - View > Tool Windows > Logcat
  - Filter: `tag:Retrofit` atau `tag:ChickenRepository`
- Test URL di browser atau Postman

### Error: "Unable to resolve dependency"
**Solusi**:
- Check koneksi internet
- Check versi library di `gradle/libs.versions.toml`
- Try sync Gradle lagi
- Clean & Rebuild project

### Build Failed
**Solusi**:
- Build > Clean Project
- Build > Rebuild Project
- File > Invalidate Caches / Restart
- Delete `.gradle` folder dan sync lagi

### App Crashes pada Launch
**Solusi**:
- Check Logcat untuk stacktrace
- Pastikan INTERNET permission ada di AndroidManifest.xml
- Pastikan semua screens sudah di-import dengan benar di NavGraph.kt

## Build APK for Release

### 1. Generate Signed APK

1. Build > Generate Signed Bundle / APK
2. Pilih APK
3. Create new keystore (simpan baik-baik!)
4. Fill keystore details:
   - Key store path: Pilih lokasi & nama file
   - Password: Buat password yang kuat
   - Alias: nama key (contoh: "chickenfarm")
   - Alias password: password untuk key
   - Validity: 25 tahun
   - Certificate details: Isi nama, organization, dll
5. Next
6. Pilih "release" build variant
7. Pilih V2 (Full APK Signature)
8. Finish

APK akan tersimpan di: `app/release/app-release.apk`

### 2. Build via Command Line

```bash
# Debug APK
./gradlew assembleDebug

# Release APK (perlu signing)
./gradlew assembleRelease
```

## Konfigurasi Lanjutan

### Ubah Package Name

1. Edit `app/build.gradle.kts`:
```kotlin
defaultConfig {
    applicationId = "com.yourcompany.chickenfarm"  // Ubah ini
    // ...
}
```

2. Refactor package name:
   - Right-click package di Android Studio
   - Refactor > Rename
   - Ubah nama package

### Ubah App Name

Edit `app/src/main/res/values/strings.xml`:
```xml
<resources>
    <string name="app_name">Chicken Farm</string>  <!-- Ubah ini -->
</resources>
```

### Ubah User Email

Edit `app/src/main/java/com/example/my_chicken_farm_android/data/repository/ChickenRepository.kt`:
```kotlin
private val userEmail = "your-custom-email@domain.com"  // Ubah ini
```

Dan juga di:
`app/src/main/java/com/example/my_chicken_farm_android/data/model/*.kt`
```kotlin
val pemilikEmail: String = "your-custom-email@domain.com"
```

## Testing dengan Postman

Test API endpoint sebelum run app:

### Test GET Ayam Indukan
```
GET https://script.google.com/macros/s/YOUR_SCRIPT_ID/exec?path=ayam_induk&userEmail=android@app.com
```

Expected Response:
```json
{
  "success": true,
  "data": [
    {
      "id": "...",
      "kode": "A001",
      "jenis_kelamin": "jantan",
      "ras": "Bangkok",
      "warna": "Merah",
      "tanggal_lahir": "2024-01-01",
      "pemilik_email": "android@app.com"
    }
  ]
}
```

### Test POST Tambah Ayam
```
POST https://script.google.com/macros/s/YOUR_SCRIPT_ID/exec
Content-Type: application/json

{
  "action": "add_ayam_induk",
  "userEmail": "android@app.com",
  "kode": "A001",
  "jenis_kelamin": "jantan",
  "ras": "Bangkok",
  "warna": "Merah",
  "tanggal_lahir": "2024-01-01"
}
```

## Database Schema

Pastikan Google Sheets memiliki struktur berikut:

### Sheet: ayam_induk
```
id | kode | jenis_kelamin | ras | warna | tanggal_lahir | pemilik_email
```

### Sheet: breeding
```
id | pejantan_id | betina_id | tanggal_kawin | tanggal_menetas | jumlah_anakan | pemilik_email
```

### Sheet: ayam_anakan
```
id | breeding_id | kode | jenis_kelamin | warna | status | pemilik_email
```

## Tips Development

1. **Enable Auto Import**: Settings > Editor > General > Auto Import > Enable auto-import
2. **Format Code**: Ctrl + Alt + L (Windows) atau Cmd + Option + L (Mac)
3. **Hot Reload**: Save file untuk apply changes tanpa restart app
4. **Debugging**: Set breakpoint dan run dengan Debug (Shift + F9)
5. **Logcat Filter**: Gunakan filter untuk melihat log specific:
   ```
   tag:Retrofit | tag:OkHttp | tag:ChickenRepository
   ```

## Resources

- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Material Design 3](https://m3.material.io/)
- [Retrofit Documentation](https://square.github.io/retrofit/)
- [Kotlin Coroutines Guide](https://kotlinlang.org/docs/coroutines-guide.html)

## Support

Jika ada masalah, check:
1. Logcat untuk error messages
2. Google Sheets untuk struktur data
3. Google Apps Script logs
4. Network inspector di Android Studio

## Next Steps

Setelah aplikasi berjalan, Anda bisa:
- Customize UI theme di `ui/theme/`
- Tambah validasi input
- Tambah loading states
- Tambah offline mode dengan Room Database
- Tambah fitur search & filter
- Tambah export to PDF
- Deploy ke Google Play Store
