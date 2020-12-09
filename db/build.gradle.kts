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
    implementation("org.mongodb:mongo-java-driver:3.12.7")
}
