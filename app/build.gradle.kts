plugins {
    alias(libs.plugins.androidApp)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.hilt)
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

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$rootDir/assets"
            }
        }
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

kapt {
    correctErrorTypes = true
}

dependencies {
    kapt(libs.hiltCompiler)
    kapt(libs.roomCompiler)

    implementation(libs.bundles.ktx)
    implementation(libs.bundles.lifeCycle)
    implementation(libs.bundles.log)
    implementation(libs.bundles.room)

    implementation(libs.appCompat)
    implementation(libs.constraintLayout)
    implementation(libs.coroutines)
    implementation(libs.hiltAndroid)
    implementation(libs.material)

    testImplementation(libs.bundles.unitTest)
}

configurations.testImplementation {
    exclude(module = "logback-android")
}
