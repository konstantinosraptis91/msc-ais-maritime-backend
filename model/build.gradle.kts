plugins {
    `java-library`
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    testImplementation("junit:junit:4.13")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("org.hibernate.validator:hibernate-validator:6.1.6.Final")
    implementation("org.glassfish:javax.el:3.0.0")
}
