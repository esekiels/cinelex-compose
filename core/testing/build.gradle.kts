plugins {
    id("esekiels.cinelex.android.library")
    id("esekiels.cinelex.android.library.compose")
}

android {
    namespace = "co.esekiels.cinelex.core.testing"
}

dependencies {
    implementation(project(":core:model"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui.tooling.preview)

    implementation(libs.junit)
    implementation(libs.kotlinx.coroutines.test)
}
