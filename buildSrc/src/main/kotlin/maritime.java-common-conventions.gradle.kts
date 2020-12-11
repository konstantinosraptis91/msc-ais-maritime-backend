plugins {
    java
    `maven-publish`
}

group = "kraptis91.maritime"
version = "1.0"

allprojects {

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
            }
        }
    }

}

repositories {
    // mavenLocal()
    mavenCentral()
    jcenter()
}

dependencies {
    // testing
    testImplementation("junit:junit:4.13")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.7.0")
}

tasks.test {
    useJUnitPlatform()
}


