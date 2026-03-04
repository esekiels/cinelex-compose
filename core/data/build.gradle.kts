plugins {
    id("esekiels.cinelex.android.library")
	id("esekiels.cinelex.android.hilt")
}

android {
    namespace = "co.esekiels.cinelex.core.data"
}

dependencies {
	
	api(projects.core.common)
	api(projects.core.model)
	
	implementation(projects.core.network)
	implementation(projects.core.database)
	implementation(projects.core.datastore)
	
	// coroutines
	implementation(libs.kotlinx.coroutines.android)
	
}
