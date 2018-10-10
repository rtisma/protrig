FROM maven:3.5.4-jdk-8

ARG branch=master

ENV APP_NAME protrig
ENV DOWNLOAD_URL https://github.com/rtisma/protrig/archive/${branch}.tar.gz
ENV APP_HOME /${APP_NAME}
ENV TARBALL /tmp/${APP_NAME}.tar.gz
RUN curl -fsSL -o ${TARBALL} ${DOWNLOAD_URL} \
        && tar zxvf ${TARBALL} -C /tmp \
        && mv /tmp/${APP_NAME}-${branch} ${APP_HOME} \
        && cd ${APP_HOME} \
        && mvn clean package -DskipTests \
        && cp target/*-exec.jar /app.jar \
        && rm -rf ~/.m2 target

COPY ./entrypoint.sh /entrypoint.sh
RUN chmod 755 /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]
CMD []

