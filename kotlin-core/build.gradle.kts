plugins {
    id("java")
    kotlin("jvm")
}

group = "ru.vad1mchk"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.objecthunter", "exp4j", "0.4.8")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}