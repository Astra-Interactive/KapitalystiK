import ru.astrainteractive.gradleplugin.setupSpigotProcessor
import ru.astrainteractive.gradleplugin.setupSpigotShadow

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    // Kotlin
    implementation(libs.bundles.kotlin)
    // AstraLibs
    implementation(libs.minecraft.astralibs.ktxcore)
    implementation(libs.minecraft.astralibs.orm)
    implementation(libs.minecraft.astralibs.di)
    implementation(libs.minecraft.astralibs.spigot.gui)
    implementation(libs.minecraft.astralibs.spigot.core)
    // Exposed
    implementation(libs.bundles.exposed)
    // Spigot dependencies
    compileOnly(libs.minecraft.paper.api)
    implementation(libs.minecraft.bstats)
    compileOnly(libs.minecraft.vaultapi)
    // Test
    testImplementation(libs.bundles.testing.kotlin)
    testImplementation(libs.tests.kotlin.test)
    // Local
    implementation(projects.modules.features.root)
    implementation(projects.modules.services.dto)
    implementation(projects.modules.services.api)
}

setupSpigotShadow(
    destination = File("D:\\Minecraft Servers\\Servers\\esmp-configuration\\smp\\plugins")
) {
    relocators = emptyList()
    minimize {
        exclude(dependency("org.jetbrains.exposed:exposed-jdbc:${libs.versions.exposed.get()}"))
        exclude(dependency("org.jetbrains.exposed:exposed-dao:${libs.versions.exposed.get()}"))
    }
}
setupSpigotProcessor()
