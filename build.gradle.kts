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

val grpcVersion by extra("1.37.0")
//val grpcVersion by extra ("1.41.0") // 최신이지만 1.40.0에서 새로 생긴 annotation을 못 읽어오는 문제 있음
val grpcKotlinVersion by extra("1.1.0")
//val protobufVersion by extra("3.15.8")
val protobufVersion by extra ("3.18.1")
val springBootVersion by extra("2.5.5")
val lognetVersion by extra("4.5.7")
val platform by extra("osx-x86_64")

dependencies {
    // Grpc
    implementation("io.grpc:grpc-protobuf:$grpcVersion") // com.google.protobuf.*
//    implementation("com.google.protobuf:protobuf-java-util:$protobufVersion")
    implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion") // io.grpc.*
    implementation("com.google.protobuf:protobuf-kotlin:$protobufVersion")
//    implementation("io.grpc:grpc-netty:$grpcVersion")
    implementation("io.github.lognet:grpc-spring-boot-starter:$lognetVersion")
//    implementation("net.devh:grpc-server-spring-boot-starter:2.12.0.RELEASE")

    // Added by Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
//    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//    implementation("mysql:mysql-connector-java")
    implementation("com.h2database:h2")
    implementation("mysql:mysql-connector-java")
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
        //artifact = "com.google.protobuf:protoc:$protobufVersion"
        artifact = "com.google.protobuf:protoc:$protobufVersion:$platform"
    }
    plugins {
        // Java version의 ServiceGrpc 객체 생성
        id("grpc") {
            //artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion"
            artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion:$platform"
        }
        // Kotlin version의 ServiceGrpcKt 객체 생성
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion:jdk7@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
            it.builtins {
                id("kotlin")
            }
        }
    }
}

// Gradle 7에서 "no duplicate handling strategy has been set" 오류 해결용
project.tasks.named("processResources", Copy::class.java) {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}
