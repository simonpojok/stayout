// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.kover)
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "dev.detekt")
    apply(plugin = "org.jetbrains.kotlinx.kover")

    val libs = rootProject.extensions.getByType<org.gradle.accessors.dm.LibrariesForLibs>()

    extensions.configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set("1.5.0")
        android.set(true)
        ignoreFailures.set(false)
        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
        }
    }

    extensions.configure<dev.detekt.gradle.extensions.DetektExtension> {
        toolVersion = libs.versions.detekt.get()
        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
        buildUponDefaultConfig = true
        baseline = file("$projectDir/detekt-baseline.xml")
        parallel = true
    }

    dependencies {
        add("ktlintRuleset", libs.compose.rules.ktlint)
        add("detektPlugins", libs.compose.rules.detekt)
    }
}

dependencies {
    kover(project(":app"))
    kover(project(":stayout-domain"))
    kover(project(":stayout-data"))
    kover(project(":stayout-presentation"))
}

kover {
    reports {
        filters {
            excludes {
                classes(
                    "*_Hilt*",
                    "Hilt_*",
                    "*_Factory",
                    "*_MembersInjector",
                    "*Module*",
                    "*_HiltModules*",
                    "*ComposableSingletons*",
                    "*Preview*",
                    "*.BuildConfig",
                )
                annotatedBy(
                    "com.example.stayout.presentation.theme.PreviewThemes",
                    "androidx.compose.ui.tooling.preview.Preview",
                )
                packages(
                    "com.example.stayout.*.di",
                    "com.example.stayscout.di",
                    "com.example.stayout.*.theme",
                    "com.example.stayscout.*.theme",
                )
            }
        }
    }
}