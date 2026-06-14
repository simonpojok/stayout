import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    // TODO: uncomment after adding google-services.json to app/:
    // alias(libs.plugins.google.services)
    // alias(libs.plugins.firebase.crashlytics.plugin)
}

fun loadProperties(filePath: String): Properties =
    Properties().apply {
        rootProject
            .file(filePath)
            .takeIf { it.exists() }
            ?.inputStream()
            ?.use { load(it) }
    }

val keystoreProps = loadProperties("config/keystore.properties")

android {
    namespace = "com.example.stayscout"
    compileSdk {
        version =
            release(36) {
                minorApiLevel = 1
            }
    }

    signingConfigs {
        create("release") {
            val keyFile = System.getenv("KEYSTORE_FILE_PATH") ?: keystoreProps["storeFile"] as String?
            if (keyFile != null) {
                storeFile = file(keyFile)
                storePassword = System.getenv("KEYSTORE_PASSWORD") ?: keystoreProps["storePassword"] as String
                keyAlias = System.getenv("KEY_ALIAS") ?: keystoreProps["keyAlias"] as String
                keyPassword = System.getenv("KEY_PASSWORD") ?: keystoreProps["keyPassword"] as String
            }
        }
    }

    defaultConfig {
        applicationId = "com.example.stayscout"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            abiFilters += listOf("armeabi-v7a", "arm64-v8a", "x86_64")
        }
        externalNativeBuild {
            cmake {
                cppFlags += "-std=c++17"
            }
        }
    }

    buildTypes {
        debug {
            val props = loadProperties("config/debug.properties")
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-Debug"
            isDebuggable = true
            buildConfigField("String", "BASE_URL", "\"${props["BASE_URL"]}\"")
            buildConfigField("String", "JSON_PLACEHOLDER_BASE_URL", "\"${props["JSON_PLACEHOLDER_BASE_URL"]}\"")
            resValue("string", "app_name", "StayScout Debug")
        }

        create("qa") {
            val props = loadProperties("config/qa.properties")
            applicationIdSuffix = ".qa"
            versionNameSuffix = "-QA"
            isDebuggable = false
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "BASE_URL", "\"${props["BASE_URL"]}\"")
            buildConfigField("String", "JSON_PLACEHOLDER_BASE_URL", "\"${props["JSON_PLACEHOLDER_BASE_URL"]}\"")
            resValue("string", "app_name", "StayScout QA")
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("debug", "release")
        }

        release {
            val props = loadProperties("config/release.properties")
            isMinifyEnabled = true
            signingConfigs.getByName("release").takeIf { it.storeFile != null }?.let {
                signingConfig = it
            }
            isShrinkResources = true
            buildConfigField("String", "BASE_URL", "\"${props["BASE_URL"]}\"")
            buildConfigField("String", "JSON_PLACEHOLDER_BASE_URL", "\"${props["JSON_PLACEHOLDER_BASE_URL"]}\"")
            resValue("string", "app_name", "StayScout")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true
    }

    kotlin {
        compilerOptions {
            jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
        resValues = true
    }
    ndkVersion = "27.0.12077973"
}

dependencies {
    implementation(project(":stayout-presentation"))
    implementation(project(":stayout-data"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.material)
    implementation(libs.hilt.android)
    implementation(libs.osmdroid)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    ksp(libs.hilt.compiler)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.timber)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    debugImplementation(libs.leakcanary)
    debugImplementation(libs.showkase.browser)
    kspDebug(libs.showkase.processor)
    "qaImplementation"(libs.showkase.browser)
    "kspQa"(libs.showkase.processor)
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
