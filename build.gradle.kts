import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	val kotlinVersion = "1.8.22"

	id("org.springframework.boot") version "3.1.2"
	id("io.spring.dependency-management") version "1.1.2"
	kotlin("jvm") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
	kotlin("plugin.jpa") version kotlinVersion

	// KAPT(Kotlin Annotation Processing Tool)를 설치합니다
	kotlin("kapt") version kotlinVersion
	// Intellij에서 사용할 파일을 생성하는 플러그인입니다
	idea
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")
	testImplementation("io.kotest:kotest-runner-junit5:5.6.2")
	testImplementation("io.kotest:kotest-assertions-core-jvm:5.6.2")
	testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")

	//h2
	implementation("com.h2database:h2")
//	implementation("org.springframework.boot:spring-boot-starter-jdbc")

	//mybatis
	implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.2")

	//jpa
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	//Querydsl
	implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
	kapt("com.querydsl:querydsl-apt:5.0.0:jpa")
	kapt("jakarta.annotation:jakarta.annotation-api")
	kapt("jakarta.persistence:jakarta.persistence-api")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

noArg {
	annotation("jakarta.persistence.Entity")
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("javax.persistence.Embeddable")
}

//QClass를 Intellij가 사용할 수 있도록 경로에 추가합니다
idea {
	module {
		val kaptMain = file("build/generated/source/kapt/main")
		sourceDirs.add(kaptMain)
		generatedSourceDirs.add(kaptMain)
	}
}