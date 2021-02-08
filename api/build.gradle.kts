plugins {
    id("maritime.java-application-conventions")
}

dependencies {
    implementation(project(":model"))
    implementation(project(":retriever"))
    implementation(project(":codelists"))
    implementation("io.javalin:javalin:3.12.0")
    implementation("org.slf4j:slf4j-simple:1.7.30")
}

application {
    mainClass.set("kraptis91.maritime.api.Application")
}

tasks.register<Jar>("uberJar") {
    archiveClassifier.set("uber")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(sourceSets.main.get().output)
    manifest {
        attributes(mapOf("Main-Class" to "kraptis91.maritime.api.Application"))
    }
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}
