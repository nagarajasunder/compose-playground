plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.geekydroid.playwithcompose"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.geekydroid.playwithcompose"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation ("androidx.core:core-ktx:1.9.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")

    val composeBom = platform("androidx.compose:compose-bom:2023.10.01")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation ("androidx.compose.runtime:runtime")
    implementation ("androidx.compose.ui:ui")
    implementation ("androidx.compose.foundation:foundation")
    implementation ("androidx.compose.foundation:foundation-layout")
    implementation ("androidx.compose.material:material")
    implementation ("androidx.compose.material:material")
    implementation ("androidx.compose.runtime:runtime-livedata")
    implementation ("androidx.compose.ui:ui-tooling-preview")
    implementation ("androidx.compose.material:material-icons-extended")
    implementation ("androidx.activity:activity-compose:1.8.1")
    debugImplementation ("androidx.compose.ui:ui-tooling")

    // Compose Navigation
    implementation ("androidx.navigation:navigation-compose:2.6.0-alpha01")
    implementation("androidx.compose.material:material-icons-extended:1.6.5")



    // ViewModel + LiveData
    val lifecycle_version = "2.5.1"
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version")



    // Compose testing dependencies
    androidTestImplementation ("androidx.compose.ui:ui-test")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4")
    debugImplementation ("androidx.compose.ui:ui-test-manifest")

    // Architecture Components
    testImplementation ("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation ("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation ("androidx.test.espresso:espresso-contrib:3.5.1")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    // Truth
    testImplementation ("com.google.truth:truth:1.1.3")
    testImplementation ("com.google.truth.extensions:truth-java8-extension:1.1.3")
}