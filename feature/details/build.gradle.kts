plugins {
	id("esekiels.cinelex.android.feature")
	id("esekiels.cinelex.android.hilt")
}

android {
	namespace = "co.esekiels.cinelex.feature.details"
}

dependencies {
	testImplementation(projects.core.testing)
	testImplementation(libs.junit)
	testImplementation(libs.kotlinx.coroutines.test)
	testImplementation(libs.mockito.kotlin)
}
