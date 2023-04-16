plugins {
    java
    `java-library`
    id("org.jetbrains.kotlin.jvm")
    id("com.github.johnrengelman.shadow")
}
tasks.shadowJar {

    isReproducibleFileOrder = true
    mergeServiceFiles()
    relocate("org.bstats", libs.versions.group.get())
    dependsOn(configurations)
    archiveClassifier.set(null as String?)
    from(sourceSets.main.get().output)
    from(project.configurations.runtimeClasspath)
    minimize {
        exclude(dependency("org.jetbrains.exposed:exposed-jdbc:${libs.versions.exposed.get()}"))
        exclude(dependency("org.jetbrains.exposed:exposed-dao:${libs.versions.exposed.get()}"))
    }
    archiveBaseName.set(libs.versions.name.get())
    destinationDirectory.set(File(libs.versions.destinationDirectoryPath.get()))
}
