import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
// 이걸 import하지 않으면 protobuf.protoc를 이용할 수 없음
import com.google.protobuf.gradle.*

plugins {
    id("org.springframework.boot") version "2.5.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.spring") version "1.5.31"
    id("com.google.protobuf") version "0.8.17"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

ext["grpcVersion"] = "1.37.0"
//ext["grpcVersion"] = "1.41.0" // 최신이지만 1.40.0에서 새로 생긴 annotation을 못 읽어오는 문제 있음
ext["grpcKotlinVersion"] = "1.1.0"
ext["protobufVersion"] = "3.15.8"
//ext["protobufVersion"] = "3.18.1" // 역시 최신이지만 사용하지 않음

dependencies {
    // Grpc
    implementation("io.grpc:grpc-protobuf:${rootProject.ext["grpcVersion"]}") // com.google.protobuf.*
//    implementation("com.google.protobuf:protobuf-java-util:${rootProject.ext["protobufVersion"]}")
    implementation("io.grpc:grpc-kotlin-stub:${rootProject.ext["grpcKotlinVersion"]}") // io.grpc.*
    runtimeOnly("io.grpc:grpc-netty:${rootProject.ext["grpcVersion"]}")

    // Added by Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

sourceSets {
    main {
        // presentation에서 문제없이 import하기 위해서 미리 source directories에 포함시킴
        java {
            srcDirs("build/generated/source/proto/main/grpc")
            srcDirs("build/generated/source/proto/main/grpckt")
            srcDirs("build/generated/source/proto/main/java")
            proto.srcDirs("src/main/proto")
        }
    }
}

protobuf {
    protoc {
        //artifact = "com.google.protobuf:protoc:${rootProject.ext["protobufVersion"]}"
        artifact = "com.google.protobuf:protoc:${rootProject.ext["protobufVersion"]}:osx-x86_64"
    }
    plugins {
        // Java version의 ServiceGrpc 객체 생성
        id("grpc") {
            //artifact = "io.grpc:protoc-gen-grpc-java:${rootProject.ext["grpcVersion"]}"
            artifact = "io.grpc:protoc-gen-grpc-java:${rootProject.ext["grpcVersion"]}:osx-x86_64"
        }
        // Kotlin version의 ServiceGrpcKt 객체 생성
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:${rootProject.ext["grpcKotlinVersion"]}:jdk7@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}

// Gradle 7에서 "no duplicate handling strategy has been set" 오류 해결용
project.tasks.named("processResources", Copy::class.java) {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}
