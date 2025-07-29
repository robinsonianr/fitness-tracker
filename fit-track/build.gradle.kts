import org.jetbrains.kotlin.gradle.tasks.KotlinCompile



plugins {
    java
    application
    id("org.springframework.boot") version "3.0.4"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.flywaydb.flyway") version "10.18.2"
    val kotlinVer = "1.8.10"
    kotlin("jvm") version kotlinVer
    kotlin("plugin.spring") version kotlinVer
    kotlin("plugin.jpa") version kotlinVer
    kotlin("plugin.allopen") version kotlinVer
}

group = "com.robinsonir"
version = "1.2.1"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

application {
    mainClass.set("com.robinsonir.fittrack.FitTrackApplication")
}

repositories {
    mavenCentral()
}


dependencies {
    // Springboot dependencies
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    // Json Web Token dependencies
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    // AWS S3 dependencies
    implementation(platform("software.amazon.awssdk:bom:2.20.56"))
    implementation("software.amazon.awssdk:s3")
    implementation("software.amazon.awssdk:sso")
    implementation("software.amazon.awssdk:ssooidc")
    // Other Dependencies
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
    implementation("org.apache.commons:commons-lang3:3.13.0")
    implementation("org.projectlombok:lombok:1.18.30")
    implementation("org.mapstruct:mapstruct:1.6.3")
    implementation("org.hibernate.orm:hibernate-envers:6.1.7.Final")
    // Annotation Processors
    annotationProcessor("org.projectlombok:lombok:1.18.30") // Add Lombok annotation processor dependency
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
    // Database/Migration postgresql
    runtimeOnly("org.postgresql:postgresql")

    // Tests
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-inline:5.2.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}


tasks.withType<Test> {
    useJUnitPlatform()
    systemProperty("org.mockito.mock-inline.mock-maker", "true")
}
