pluginManagement {
    repositories {
        maven {
            val publicRepositoryUrl: String by settings
            url = uri(publicRepositoryUrl)
            isAllowInsecureProtocol = true
        }

        maven {
            url = uri("https://nexus.sigma.sbrf.ru/nexus/service/local/repositories/thirdparty/content/")
            isAllowInsecureProtocol = true
        }
    }
}

rootProject.name = "antifraud"

include(":antifraud-api")
include(":antifraud-service")
include(":antifraud-application")
include(":antifraud-rpc-api")
include(":docs")
