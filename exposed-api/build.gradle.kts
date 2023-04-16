plugins {
    id("spigot-resource-processor")
    id("basic-java")
}

dependencies {
    // Kotlin
    implementation(libs.kotlinGradlePlugin)
    // Coroutines
    implementation(libs.coroutines.coreJvm)
    implementation(libs.coroutines.core)
    // Exposed
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.core)
    // Serialization
    implementation(libs.kotlin.serialization)
    implementation(libs.kotlin.serializationJson)
    implementation(libs.kotlin.serializationKaml)
    // AstraLibs
    implementation(libs.astralibs.ktxCore)
    implementation(libs.astralibs.orm)
    // Test-Core
    testImplementation(platform(libs.junit.bom))
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // Test-libs
    testImplementation(libs.coroutines.core)
    testImplementation(libs.coroutines.coreJvm)
    testImplementation(libs.xerial.sqlite.jdbc)
    // Local
    implementation(project(":dto"))
    implementation(project(":api"))
}
