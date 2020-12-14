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
    // guava
    implementation("com.google.guava:guava:29.0-jre")
    // annotations
    implementation("org.jetbrains:annotations:19.0.0")
    implementation("jakarta.validation:jakarta.validation-api:3.0.0")
    // config
    implementation("com.typesafe:config:1.4.1")
    // testing
    testImplementation("junit:junit:4.13")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.7.0")
}

tasks.test {
    useJUnitPlatform()
}


