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
            baseName = "Foundation"
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

            implementation(projects.core)
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "io.github.zheniaregbl.zephyr.foundation.resources"
    generateResClass = always
}

android {
    namespace = "io.github.zheniaregbl.zephyr.foundation"
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
        artifactId = "zephyr.foundation",
        version = "1.0.0-alpha02"
    )
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    pom {
        name.set("Zephyr UI Foundation")
        description.set("A Compose Multiplatform UI Kit for foundational components")
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

dependencies {
    debugImplementation(compose.uiTooling)
}