plugins {
    id("java")
}

group = "org.nbu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.hibernate.orm:hibernate-core:7.1.0.Final")
    implementation("com.mysql:mysql-connector-j:9.4.0")
    implementation("org.apache.logging.log4j:log4j-core:2.17.1")
    // Jakarta Validation
    implementation("org.hibernate.validator:hibernate-validator:9.1.0.Final")
    implementation("org.hibernate.validator:hibernate-validator-annotation-processor:9.1.0.Final")
    implementation("org.glassfish.expressly:expressly:6.0.0")
    // Annotation processor for metamodel
    annotationProcessor("org.hibernate.orm:hibernate-processor:7.1.0.Final")
    
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.11.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // H2 in-memory database for tests
    testImplementation("com.h2database:h2:2.2.224")
}

tasks.test {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
