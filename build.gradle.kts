import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.3"
	id("io.spring.dependency-management") version "1.1.3"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
	id("nu.studer.jooq") version "8.0"
	id("org.flywaydb.flyway") version "8.0.1"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-mysql")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	runtimeOnly("com.mysql:mysql-connector-j")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.flywaydb:flyway-mysql")
	jooqGenerator("com.mysql:mysql-connector-j")
	jooqGenerator("jakarta.xml.bind:jakarta.xml.bind-api:4.0.0")
	implementation("com.github.database-rider:rider-core:1.34.0")
	implementation("com.github.database-rider:rider-spring:1.34.0")
	testImplementation("com.github.database-rider:rider-junit5:1.34.0")

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

jooq {
	configurations {
	        create("main") {
	            jooqConfiguration.apply {
	                jdbc.apply {
	                    url = "jdbc:mysql://localhost:3306/library?enabledTLSProtocols=TLSv1.2"
						user = "root"
						password = "root"
					}
					generator.apply {
						name = "org.jooq.codegen.KotlinGenerator"
						database.apply {
							name = "org.jooq.meta.mysql.MySQLDatabase"
							inputSchema = "library"
							excludes = "flyway_schema_history"
						}
						generate.apply {
							isDeprecated = false
							isTables = true
						}
						target.apply {
							packageName = "com.example.ktknowledgeTodo.infra.jooq"
							directory = "$buildDir/generated/source/jooq/main"
						}
					}
				}
			}
	}
}

flyway {
	url = "jdbc:mysql://localhost:3306/library?enabledTLSProtocols=TLSv1.2"
	user = "root"
	password = "root"
}