plugins {
    id("esekiels.cinelex.android.library")
	id("esekiels.cinelex.android.hilt")
}

android {
    namespace = "co.esekiels.cinelex.core.common"
}

dependencies {
	implementation(libs.kotlinx.coroutines.core)
}
