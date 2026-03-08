plugins {
    id("esekiels.cinelex.android.library")
	alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "co.esekiels.cinelex.core.navigation"
}

dependencies {
	
	// navigation 3
	api(libs.androidx.navigation3.runtime)
	api(libs.androidx.navigation3.ui)
	api(libs.androidx.lifecycle.viewmodel.navigation3)
	
	// json parser
	implementation(libs.kotlinx.serialization.json)
}
