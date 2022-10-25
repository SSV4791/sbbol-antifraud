pluginManagement {
    repositories {
        maven {
            url = uri("https://nexus-ci.delta.sbrf.ru/repository/public/")
            val tokenName: String by settings
            val tokenPassword: String by settings
            credentials {
                username = tokenName
                password = tokenPassword
            }
            isAllowInsecureProtocol = true
        }
    }
}
