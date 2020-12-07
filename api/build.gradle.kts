plugins {
    application
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(project(":model"))
    implementation(project(":retriever"))
    testImplementation("junit:junit:4.13")
    implementation("io.javalin:javalin:3.12.0")
    implementation("org.slf4j:slf4j-simple:1.7.30")
    implementation("com.typesafe:config:1.4.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.10.3")
}

application {
    mainClass.set("kraptis91.maritime.api.Server")
}
