plugins {
    kotlin("jvm")
}

dependencies {
    // Kotlin
    implementation(libs.bundles.kotlin)
    // Exposed
    implementation(libs.bundles.exposed)
    // AstraLibs
    implementation(libs.minecraft.astralibs.ktxcore)
    implementation(libs.minecraft.astralibs.orm)
    implementation(libs.klibs.kdi)
    // Test
    testImplementation(libs.bundles.testing.kotlin)
    testImplementation(libs.tests.kotlin.test)
    // Local
    implementation(projects.modules.services.dto)
}
