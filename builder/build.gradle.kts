plugins {
    `java-library`
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(project(":model"))
    testImplementation("junit:junit:4.13")
    api("org.apache.commons:commons-math3:3.6.1")
    implementation("com.google.guava:guava:29.0-jre")
}
