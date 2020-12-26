plugins {
    id("maritime.java-library-conventions")
}

dependencies {
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("org.hibernate.validator:hibernate-validator:6.1.6.Final")
    // implementation("com.opencsv:opencsv:5.3")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.0")
    implementation("com.fasterxml.jackson.core:jackson-core:2.12.0")
}
