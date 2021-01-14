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
