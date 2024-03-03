plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    alias(libs.plugins.org.jetbrains.kotlin.parcelize)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.navigation.safe.args)
}

android {
    namespace = "io.tanlnm.my.weather"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.tanlnm.my.weather"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField(type = "String", name = "API_KEY", value = "\"4d0cfc63d420549403c4cdb5758374a9\"")
        buildConfigField(type = "String", name = "BASE_API_URL", value = "\"https://api.openweathermap.org/\"")
    }

    buildTypes {
        debug {
            isMinifyEnabled= false
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.bundles.android.extension) // Android Extensions
    implementation(libs.bundles.android.support) // Android Support libraries
    implementation(libs.bundles.lifecycle) // App lifecycle
    kapt(libs.lifecycle.compiler)

    implementation(libs.bundles.work)

    implementation(libs.bundles.room) // Room database to save data
    ksp(libs.room.compiler)

    implementation(libs.bundles.navigation) // Jetpack Navigation

    implementation(libs.bundles.hilt) // Hilt Dependency Injection
    kapt(libs.bundles.hilt.compiler)

    implementation(libs.bundles.retrofit) // Retrofit & OkHTTP Network Call
    val okhttpBOM = platform(libs.okhttp.bom)
    implementation(okhttpBOM)
    implementation(libs.bundles.okhttp)

    implementation(libs.bundles.coil) // Coil Image loader
    implementation(libs.gson) // Gson to parse json to object
    implementation(libs.timber) // Logger
    implementation(libs.insertter) // Support edge to edge screen
    implementation(libs.lottie) // Load Animation json file

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}