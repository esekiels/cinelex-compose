plugins {
    id("esekiels.cinelex.android.library")
    id("esekiels.cinelex.android.library.compose")
}

android {
    namespace = "co.esekiels.cinelex.core.design"
}

dependencies {

    //splash screen
    api(libs.androidx.core.splashscreen)

    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.graphics)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.material.icons.extended)
    api(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.ui.tooling.preview)
}
