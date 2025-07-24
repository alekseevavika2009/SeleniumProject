plugins {
    id 'java'
}

group 'org.example'
version '1.0-SNAPSHOT'

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.seleniumhq.selenium:selenium-java:4.15.0'

    testImplementation 'org.testng:testng:7.6.1'
    testImplementation 'org.seleniumhq.selenium:selenium-java:4.15.0'
}

test{
    useTestNG()
}