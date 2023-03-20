pluginManagement {
    // Define convention plugins
    includeBuild("build-logic")
}

rootProject.name = "gxf-service-template"

include("application")
include("components:avro")
include("components:kafka")
include("components:mqtt")
