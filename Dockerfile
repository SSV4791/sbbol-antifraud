FROM registry.sigma.sbrf.ru/ci00149046/ci00405008_sbbolufs/bellsoft/liberica-openjdk-alpine:15.0.1-9
COPY antifraud-application/build/libs/antifraud-application*.jar antifraud-application.jar
COPY docker/entrypoint.sh entrypoint.sh

EXPOSE 8080

ENTRYPOINT ["sh", "/entrypoint.sh"]
