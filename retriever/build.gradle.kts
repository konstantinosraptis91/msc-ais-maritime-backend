plugins {
    id("maritime.java-library-conventions")
}

dependencies {
    implementation(project(":model"))
    implementation(project(":db"))
    implementation(project(":parser"))
    implementation(project(":codelists"))
    implementation("jakarta.validation:jakarta.validation-api:3.0.0")
}
