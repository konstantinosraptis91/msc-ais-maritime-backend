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
    implementation("jakarta.validation:jakarta.validation-api:3.0.0")
}
