plugins {
    alias(libs.plugins.androidApp)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "ademar.yaaa"
    compileSdk = 33

    defaultConfig {
        applicationId = "ademar.yaaa"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.bundles.ktx)
    implementation(libs.appCompat)
    implementation(libs.material)
    implementation(libs.constraintLayout)
    testImplementation(libs.junit)
}
