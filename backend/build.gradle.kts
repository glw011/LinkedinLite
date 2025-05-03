plugins {
    java
    war
}

repositories {
    mavenCentral()
}

dependencies {
    // Servlet API
    compileOnly("javax.servlet:javax.servlet-api:4.0.1")

    // Gson for JSON
    implementation("com.google.code.gson:gson:2.10.1")
    // SLF4J API
    implementation("org.slf4j:slf4j-api:1.7.36")

    // Logback implementation (MAYBE?)
    implementation("ch.qos.logback:logback-classic:1.2.12")

    // mysql dependency
    implementation("com.mysql:mysql-connector-j:9.2.0")
}


tasks.named<War>("war") {
    archiveFileName.set("ROOT.war") // So Docker can use this name
}
