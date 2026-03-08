import co.esekiels.cinelex.configureAndroidCompose
import co.esekiels.cinelex.configureKotlinAndroid
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension


/*
 * Cinelex
 *
 * Created by Esekiel Surbakti on 01/03/26
 */

class AndroidFeatureConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.library")
            }

            dependencies {
                add("implementation", project(":core:design"))
                add("implementation", project(":core:data"))
                add("implementation", project(":core:navigation"))
                add("debugImplementation", project(":core:testing"))
            }

            extensions.configure<LibraryExtension>() {
                configureKotlinAndroid(this)
                configureAndroidCompose(this)
            }
            
            extensions.getByType<KotlinAndroidProjectExtension>().apply {
                configureKotlinAndroid(this)
            }
            
        }
    }
}
