

allprojects {
    group = "com.gxf"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
        maven {
            name = "confluent"
            url = uri("https://packages.confluent.io/maven/")
        }
    }
}
