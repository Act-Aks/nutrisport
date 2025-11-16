import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.serialization)
}

// --------------------------
// Custom Gradle Task (configuration-cache safe)
// --------------------------
abstract class GenerateConstants : DefaultTask() {

    @get:Input
    abstract val googleClientId: Property<String>

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @TaskAction
    fun generate() {
        val output = outputDir.get().asFile
        val pkg = "com.actaks.nutrisport.shared"
        val dir = File(output, pkg.replace(".", "/"))
        dir.mkdirs()

        val file = File(dir, "Constants.kt")
        val content = """
            // Auto-generated file. Do not modify manually.

            package $pkg

            object Constants {
                const val GOOGLE_WEB_CLIENT_ID: String = "${googleClientId.get()}"
            }
        """.trimIndent()

        file.writeText(content)
        println("✅ Generated Constants.kt at: ${file.path}")
    }
}

// --------------------------
// Task Registration
// --------------------------
val localProperties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

val generateConstants = tasks.register<GenerateConstants>("generateConstants") {
    group = "build setup"
    description = "Generates Constants.kt file for shared module"
    val outputPath = layout.buildDirectory.dir("generated/source/constants/kotlin")
    outputDir.set(outputPath)
    googleClientId.set(localProperties["GOOGLE_WEB_CLIENT_ID"]?.toString() ?: "")
}

// --------------------------
// Kotlin Multiplatform Setup
// --------------------------
kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            // This ensures IDE and build tooling recognize generated sources
            kotlin.srcDir(generateConstants.map { it.outputDir })
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation(libs.kotlinx.serialization.json)
        }
    }
}

// --------------------------
// Android Library Config
// --------------------------
android {
    namespace = "com.actaks.nutrisport.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

// --------------------------
// Ensure generated task runs before compilation
// --------------------------
tasks.withType<KotlinCompile>().configureEach {
    dependsOn(generateConstants)
}

// --------------------------
// Optional (ensures IDE picks up generated sources ASAP in some setups)
// --------------------------
tasks.named("preBuild").configure {
    dependsOn(generateConstants)
}
