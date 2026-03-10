plugins {
    id("esekiels.cinelex.android.library")
	alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "co.esekiels.cinelex.core.model"
}

dependencies {
	compileOnly(platform(libs.androidx.compose.bom))
	compileOnly(libs.androidx.compose.runtime)
	
	implementation(libs.kotlinx.serialization.json)
}
