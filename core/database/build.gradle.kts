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
	
	// coroutines
	implementation(libs.kotlinx.coroutines.android)
	
	// database
	implementation(libs.androidx.room.runtime)
	implementation(libs.androidx.room.ktx)
	ksp(libs.androidx.room.compiler)
}
