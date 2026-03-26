plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.potaninpm.navigation"
    compileSdk = 36

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Internal
    implementation(projects.sources.common.uikit)
    implementation(projects.sources.features.featureMyRequests)
    implementation(projects.sources.features.featureLoansList)
    implementation(projects.sources.features.featureLoanApplication)
    implementation(projects.sources.features.featureLoanDetails)
    implementation(projects.sources.features.featureApplicationDetails)
    implementation(projects.sources.features.featureProfile)
    implementation(projects.sources.features.featureEditProfile)
    implementation(projects.sources.features.featureApplicationPreview)
    implementation(projects.sources.features.featureAuth)
    implementation(projects.sources.common.commonDi)
    implementation(projects.sources.common.commonNetwork)
    implementation(projects.sources.core.coreUtils)
    implementation(projects.sources.core.coreAuth)

    // Compose Navigation
    implementation(libs.androidx.navigation.compose)

    // Blur
    implementation(libs.haze)

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

    // Core
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.material)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.foundation)
}