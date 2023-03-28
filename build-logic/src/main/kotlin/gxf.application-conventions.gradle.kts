plugins {
    id("gxf.spring-conventions")
    id("org.springframework.boot")
}



springBoot {
    buildInfo()
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootBuildImage>("bootBuildImage") {
    imageName.set("ghcr.io/osgp/${rootProject.name}")
    if (project.hasProperty("publishImage")) {
        publish.set(true)
        docker {
            publishRegistry {
                username.set(System.getenv("GITHUB_ACTOR"))
                password.set(System.getenv("GITHUB_TOKEN"))
            }
        }
    }
}
