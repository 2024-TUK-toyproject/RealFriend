plugins {
    id("daeyoung.plugin.application")
    id("daeyoung.plugin.application.compose")
//    alias(libs.plugins.google.services)
    id("daeyoung.plugin.hilt")
//    id("com.google.android.gms.oss-licenses-plugin")
}

android {
    namespace = "com.example.connex"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.connex"
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
            isMinifyEnabled = true // 코드 최적화
            isShrinkResources = true // 리소스 최적화
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.bundles.androidx.test)

    // compose-keyboard-controller
    implementation(libs.compose.keyboard.controller)

    // compose-collapsingtoolbar
    implementation(libs.compose.collapsingtoolbar)

}