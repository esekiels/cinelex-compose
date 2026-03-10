plugins {
    id("esekiels.cinelex.android.library")
	id("esekiels.cinelex.android.hilt")
	alias(libs.plugins.ksp)
}

android {
    namespace = "co.esekiels.cinelex.core.database"
}

dependencies {

	implementation(projects.core.model)
	testImplementation(projects.core.testing)

	// coroutines
	implementation(libs.kotlinx.coroutines.android)
	testImplementation(libs.kotlinx.coroutines.test)

	// database
	implementation(libs.androidx.room.runtime)
	implementation(libs.androidx.room.ktx)
	ksp(libs.androidx.room.compiler)
	testImplementation(libs.androidx.arch.core.testing)

	// json parsing
	implementation(libs.kotlinx.serialization.json)

	// unit test
	testImplementation(libs.junit)
	testImplementation(libs.androidx.test.core)
	testImplementation(libs.robolectric)
}
