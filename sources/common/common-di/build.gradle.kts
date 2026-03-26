plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}
android {
    namespace = "com.potaninpm.common.di"
    compileSdk = 36

    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}
dependencies {
    implementation(projects.sources.common.commonNetwork)
    implementation(projects.sources.core.coreUtils)
    implementation(projects.sources.core.coreAuth)
    implementation(projects.sources.common.commonDatabase)
    implementation(libs.androidx.core.ktx)
}
