FROM registry.sigma.sbrf.ru/ci00149046/ci00405008_sbbolufs/bellsoft/liberica-openjdk-rhel7.7-205:15.0.2-10
COPY antifraud-application/build/libs/antifraud-application*.jar antifraud-application.jar
COPY docker/entrypoint.sh entrypoint.sh

EXPOSE 8080

ENTRYPOINT ["sh", "/entrypoint.sh"]
