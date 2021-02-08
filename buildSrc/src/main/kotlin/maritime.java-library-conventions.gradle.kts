plugins {
    id("maritime.java-common-conventions")
    `java-library` 
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "msc.ais.maritime"
            artifactId = project.name
            version = "1.0.0"
            from(components["java"])
        }
    }
}

dependencies {

}
