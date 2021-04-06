cd ..
mvn clean package -Dmaven.test.skip=true -PlocalDeployLiquibase
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -cp "$(printf ./antifraud-model-jpa/target/standalone/*jar)" -Dloader.path=./antifraud-model-jpa/target org.springframework.boot.loader.PropertiesLauncher --spring.profiles.active=local-h2-profile --dataspace-core.model.packagesToScan=ru.sberbank.pprb.sbbol.antifraud
