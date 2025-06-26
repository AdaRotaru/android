plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.relearn.app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.relearn.app"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
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

    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }

    kotlin {
        jvmToolchain(17)
    }
}

configurations.all {
    resolutionStrategy {
        force("androidx.activity:activity-compose:1.9.0")
        force("androidx.core:core-ktx:1.13.1")
        force("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    }
}

dependencies {
    // Compose & UI
    implementation(libs.ui)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    debugImplementation(libs.ui.tooling)

    // Lifecycle + ViewModel
    implementation(libs.androidx.lifecycle.runtime.ktx.v270)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Activity
    implementation(libs.androidx.activity.compose.v190)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.messaging.ktx)

    // Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Room
    implementation(libs.androidx.room.runtime.v272)
    implementation(libs.androidx.room.ktx.v261)
    kapt("androidx.room:room-compiler:2.7.2")
    kapt(libs.sqlite.jdbc)

    // ViewBinding in Compose
    implementation(libs.androidx.ui.viewbinding)

    // Extra UI
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.foundation)
    implementation(libs.accompanist.flowlayout)

    // RecyclerView + CardView
    implementation(libs.androidx.recyclerview.v131)
    implementation(libs.androidx.cardview)

    // Fragment & Material
    implementation(libs.fragment.ktx)
    implementation(libs.google.material)




}

kapt {
    arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
        arg("room.incremental", "true")
        arg("room.verifySchema", "false")
        arg("room.disableVerification", "true")
        arg("room.verifySchemaLocation", "false")
        arg("room.skipQueryVerification", "true")

    }
}
