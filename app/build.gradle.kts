import co.esekiels.cinelex.Configuration

plugins {
    id("esekiels.cinelex.android.application")
    id("esekiels.cinelex.android.application.compose")
    id("esekiels.cinelex.android.hilt")
}

android {
    namespace = "co.esekiels.cinelex"

    defaultConfig {
        applicationId = "co.esekiels.cinelex"
        versionCode = Configuration.versionCode
        versionName = Configuration.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    // cores
    implementation(projects.core.design)
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.core.navigation)

    // features
    implementation(projects.feature.home)
    implementation(projects.feature.details)
    implementation(projects.feature.search)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.androidx.activity.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
