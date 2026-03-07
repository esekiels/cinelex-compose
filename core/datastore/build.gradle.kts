plugins {
	id("esekiels.cinelex.android.library")
	id("esekiels.cinelex.android.hilt")
	alias(libs.plugins.wire)
}

android {
	namespace = "co.esekiels.cinelex.core.datastore"
}

wire {
	kotlin {}
}

dependencies {
	implementation(projects.core.model)

	api(libs.androidx.dataStore)
	api(libs.wire.runtime)

	testImplementation(libs.junit)
	testImplementation(libs.kotlinx.coroutines.test)
}
