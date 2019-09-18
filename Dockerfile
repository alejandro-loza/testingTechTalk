FROM fabric8/java-jboss-openjdk8-jdk:1.3.1
LABEL maintainer="developers@kubofinanciero.com"
ENV TZ=America/Mexico_City
ENV JAVA_APP_JAR testingTechTalk-0.0.1-SNAPSHOT.jar
ENV AB_OFF true
ENV JAEGER_SERVICE_NAME=testingTechTalk\
  JAEGER_ENDPOINT=http://jaeger-collector.istio-system.svc:14268/api/traces\
  JAEGER_PROPAGATION=b3\
  JAEGER_SAMPLER_TYPE=const\
  JAEGER_SAMPLER_PARAM=1
ADD build/libs/testingTechTalk-0.0.1-SNAPSHOT.jar /deployments/
EXPOSE 8080
