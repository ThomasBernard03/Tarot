plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.com.google.devtools.ksp)
}

android {
    namespace = "fr.thomasbernard03.tarot"
    compileSdk = 34

    defaultConfig {
        applicationId = "fr.thomasbernard03.tarot"
        minSdk = 26
        targetSdk = 34
        versionCode = 4
        versionName = "1.2.0"

        buildConfigField("String", "REPOSITORY_URL", "\"https://github.com/ThomasBernard03/Tarot\"")
        buildConfigField("String", "BUG_REPORT_URL", "\"https://github.com/ThomasBernard03/Tarot/issues/new\"")
        buildConfigField("String", "TAROT_OFFICIAL_RULES_URL", "\"https://www.fftarot.fr/assets/documents/R-RO201206.pdf\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

//    signingConfigs {
//        create("release") {
//            storeFile = file("../Keystore")
//            storePassword = System.getenv("KEYSTORE_PASSWORD")
//            keyAlias = System.getenv("KEYSTORE_ALIAS")
//            keyPassword = System.getenv("KEY_PASSWORD")
//        }
//    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            applicationIdSuffix = ".debug"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Navigation
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Room for database
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)
    implementation(libs.room.ktx)

    // Dependency injection with Koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)

    implementation(libs.lottie)
}
