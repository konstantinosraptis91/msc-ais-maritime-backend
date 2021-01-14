plugins {
    id("maritime.java-library-conventions")
}

dependencies {
    implementation(project(":model"))
    implementation(project(":parser"))
    implementation(project(":codelists"))
    implementation("org.mongodb:mongo-java-driver:3.12.7")
}
