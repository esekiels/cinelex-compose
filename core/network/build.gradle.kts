plugins {
    id("esekiels.cinelex.android.library")
	id("esekiels.cinelex.android.hilt")
	alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "co.esekiels.cinelex.core.network"
	
	defaultConfig {
		buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
		buildConfigField("String", "TOKEN", "\"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1YTQ2YmU1OWQwYWQwNTFhYzNmY2FjOGE4ODY4NjNlZiIsIm5iZiI6MTU2MTI3MjQxNC4zMzMsInN1YiI6IjVkMGYyMDVlMGUwYTI2MTA5MmNjMTY3MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.TfrjkpxcqPC9PU9tj0oMjXUnJZhsW0eIACfYUpUr3fI\"")
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
