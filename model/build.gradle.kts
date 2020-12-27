plugins {
    id("maritime.java-library-conventions")
}

dependencies {
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("org.hibernate.validator:hibernate-validator:6.1.6.Final")
    implementation("org.glassfish:javax.el:3.0.0")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.12.0")
}
