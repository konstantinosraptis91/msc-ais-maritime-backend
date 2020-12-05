plugins {
    `java-library`
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    testImplementation("junit:junit:4.13")
    implementation("io.javalin:javalin:3.12.0")
}
