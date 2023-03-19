import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    id("org.springframework.boot") version "3.0.4"
    id("io.spring.dependency-management") version "1.1.0"
    id("com.palantir.docker") version "0.34.0"
    kotlin("jvm") version "1.8.10"
    kotlin("plugin.spring") version "1.8.10"
    kotlin("plugin.jpa") version "1.8.10"
    kotlin("plugin.allopen") version "1.8.10"
}

group = "com.robinsonir"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}



docker {
    val bootJar = tasks.getByName<BootJar>("bootJar")
    dependsOn(bootJar)
    name = "${project.name}:${project.version}"
    files(bootJar.archiveFile)
    tag("DockerHub", "robinsonir/${project.name}:${version}")
}


tasks {
    val dockerRun by creating {
        dependsOn(dockerPrepare)
        description = "Runs the Docker container"
        group = "docker"
        doLast {
            exec {
                executable = "docker"
                args = listOf("run", "-p", "8080:8080", "--name", "${project.name}_${version}", "--rm", "${project.name}:${version}")
            }
        }
    }

    val dockerStop by creating {
        description = "Stops the Docker container"
        group = "docker"
        doLast {
            exec {
                executable = "docker"
                args = listOf("stop", "${project.name}_${version}")
            }
        }
    }
}
tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}


tasks.withType<Test> {
    useJUnitPlatform()
}
