package co.esekiels.cinelex

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.model.KotlinAndroidExtension

internal fun Project.configureKotlinAndroid(
	commonExtension: CommonExtension,
) {
	commonExtension.apply {
		compileSdk = 36
		defaultConfig.minSdk = 24
		compileOptions.apply {
			sourceCompatibility = JavaVersion.VERSION_17
			targetCompatibility = JavaVersion.VERSION_17
		}
		lint.apply {
			warningsAsErrors = true
			abortOnError = true
			disable.add("GradleDependency")
		}
	}
}

internal fun Project.configureKotlinAndroid(
	extension: KotlinAndroidProjectExtension
) {
	extension.apply {
		compilerOptions {
			freeCompilerArgs.set(
				freeCompilerArgs.getOrElse(emptyList()) + listOf(
					// Enable experimental coroutines APIs, including Flow
					"-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
					// Enable experimental compose APIs
					"-Xopt-in=androidx.compose.material3.ExperimentalMaterial3Api"
				)
			)
			
			jvmTarget.set(JvmTarget.JVM_17)
		}
	}
}
