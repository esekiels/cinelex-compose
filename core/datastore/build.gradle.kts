plugins {
	id("esekiels.cinelex.android.library")
	id("esekiels.cinelex.android.hilt")
}

android {
	namespace = "co.esekiels.cinelex.core.datastore"
}

dependencies {
	implementation(projects.core.model)
	
	api(libs.androidx.dataStore.preferences)
}
