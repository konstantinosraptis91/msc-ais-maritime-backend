plugins {
    `java-library`
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    testImplementation("junit:junit:4.13")
    implementation("org.mongodb:mongo-java-driver:3.12.7")
}
