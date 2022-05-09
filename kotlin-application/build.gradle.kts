plugins {
    id("java")
    kotlin("jvm")
}

group = "ru.vad1mchk"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":kotlin-core"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.20")
    implementation("net.objecthunter", "exp4j", "0.4.8")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}