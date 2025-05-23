import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.publish)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Core"
            isStatic = true
        }
    }
    
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
        }
    }
}

android {
    namespace = "io.github.zheniaregbl.zephyr.core"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

mavenPublishing {
    coordinates(
        groupId = "io.github.zheniaregbl",
        artifactId = "zephyr.core",
        version = "1.0.0-alpha02"
    )
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    pom {
        name.set("Zephyr UI Core")
        description.set("Core module of Zephyr Compose Multiplatform")
        inceptionYear.set("2025")
        url.set("https://github.com/ZephyrUI/zephyr-compose-multiplatfrom")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
                id.set("zheniaregbl")
                name.set("Prokhnitsky Evgeniy")
            }
        }
        scm {
            url.set("https://github.com/ZephyrUI/zephyr-compose-multiplatfrom")
            connection.set("scm:git:git://github.com/ZephyrUI/zephyr-compose-multiplatfrom.git")
            developerConnection.set("scm:git:ssh://git@github.com/ZephyrUI/zephyr-compose-multiplatfrom.git")
        }
    }
}

tasks.register<Zip>("sourceZip") {
    archiveFileName.set("zephyr-core-${rootProject.version}.zip")
    destinationDirectory.set(layout.buildDirectory.dir("dist"))
    from(projectDir) {
        include("src/**")
        include("README.md", "LICENSE")
        exclude("**/build/**", "**/.idea/**", "**/.git/**")
    }
}

tasks.register<Tar>("sourceTarGz") {
    archiveFileName.set("zephyr-core-${rootProject.version}.tar.gz")
    destinationDirectory.set(layout.buildDirectory.dir("dist"))
    compression = Compression.GZIP
    from(projectDir) {
        include("src/**")
        include("README.md", "LICENSE")
        exclude("**/build/**", "**/.idea/**", "**/.git/**")
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}