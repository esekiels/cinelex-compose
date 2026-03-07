plugins {
    id("esekiels.cinelex.android.library")
	id("esekiels.cinelex.android.hilt")
	alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "co.esekiels.cinelex.core.network"
	
	defaultConfig {
		buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
		buildConfigField("String", "TOKEN", "\"***REMOVED***\"")
	}
	
	buildFeatures {
		buildConfig = true
	}
}

dependencies {
	implementation(projects.core.common)
	implementation(projects.core.model)
	testImplementation(projects.core.testing)
	
	// coroutines
	implementation(libs.kotlinx.coroutines.android)
	testImplementation(libs.kotlinx.coroutines.test)
	
	// network
	implementation(platform(libs.retrofit.bom))
	implementation(platform(libs.okhttp.bom))
	implementation(libs.bundles.retrofitBundle)
	testImplementation(platform(libs.okhttp.bom))
	testImplementation(libs.okhttp.mockwebserver)
	testImplementation(libs.androidx.arch.core.testing)
	
	// json parser
	implementation(libs.kotlinx.serialization.json)
}
