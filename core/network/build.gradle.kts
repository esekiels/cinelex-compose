plugins {
    id("esekiels.cinelex.android.library")
	id("esekiels.cinelex.android.hilt")
	alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "co.esekiels.cinelex.core.network"
	
	defaultConfig {
		buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
		buildConfigField("String", "TOKEN", "")
	}
	
	buildFeatures {
		buildConfig = true
	}
}

dependencies {
	
	implementation(projects.core.common)
	implementation(projects.core.model)
	
	// coroutines
	implementation(libs.kotlinx.coroutines.android)
	
	// network
	implementation(platform(libs.retrofit.bom))
	implementation(platform(libs.okhttp.bom))
	implementation(libs.bundles.retrofitBundle)
	
	// json parser
	implementation(libs.kotlinx.serialization.json)
}
