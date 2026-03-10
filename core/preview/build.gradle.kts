plugins {
    id("esekiels.cinelex.android.library")
}

android {
    namespace = "co.esekiels.cinelex.core.preview"
}

dependencies {
    implementation(project(":core:model"))
}
