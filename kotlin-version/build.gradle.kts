plugins {
    kotlin("jvm") version "1.9.23"
    application
}

group = "ai.saai"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    // LangChain4j — core + OpenAI integration
    implementation("dev.langchain4j:langchain4j:0.29.1")
    implementation("dev.langchain4j:langchain4j-open-ai:0.29.1")

    // Ktor server — for API gateway / health endpoints
    val ktorVersion = "2.3.9"
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")

    // Kotlin coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // Kotlin serialisation
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    // Koin — dependency injection
    implementation("io.insert-koin:koin-core:3.5.3")
    implementation("io.insert-koin:koin-ktor:3.5.3")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.4.14")

    // Testing
    testImplementation(kotlin("test"))
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("io.insert-koin:koin-test:3.5.3")
}

application {
    mainClass.set("ai.saai.MainKt")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
