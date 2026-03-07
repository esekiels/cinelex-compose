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
	testImplementation(projects.core.testing)

	// coroutines
	implementation(libs.kotlinx.coroutines.android)
	testImplementation(libs.kotlinx.coroutines.test)

	// unit test
	testImplementation(libs.junit)
	testImplementation(libs.mockito.kotlin)
}
