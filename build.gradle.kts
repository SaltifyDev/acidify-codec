import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform") version "2.3.0"
}

group = "org.ntqqrev"
version = "0.1.0"

repositories {
    mavenCentral()
}

val interopDir = file("src/nativeInterop")
fun libraryPath(target: String) = interopDir.resolve("lib/$target")

kotlin {
    jvm()
    mingwX64()
    linuxX64()
    linuxArm64()
    macosX64()
    macosArm64()

    sourceSets {
        commonMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-io-core:0.8.2")
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        jvmMain.dependencies {
            implementation("net.java.dev.jna:jna:5.18.1")
        }
    }

    targets.withType<KotlinNativeTarget> {
        val main by compilations.getting
        val nativeInterop by main.cinterops.creating {
            definitionFile.set(project.file("src/nativeInterop/interop.def"))
            extraOpts("-libraryPath", libraryPath(targetName))
        }
    }

    mingwX64 {
        binaries.all {
            linkerOpts(
                "-Wl,-Bstatic",
                "-lstdc++",
                "-lgcc",
                "-Wl,-Bdynamic"
            )
        }
    }

    jvmToolchain(25)
}