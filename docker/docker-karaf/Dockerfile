FROM dockerfile/java:oracle-java8

# expose HTTP, Jolokia and Hazelcast (for Cellar)
EXPOSE 8181 8778 5701

ENV KARAF_VERSION 3.0.3
ENV KARAF_HOME /karaf
ENV DEPLOY_DIR $KARAF_HOME/deploy

ENV JOLOKIA_VERSION 1.2.3

ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

# Install Git, Go and Runit
RUN echo "deb http://archive.ubuntu.com/ubuntu trusty main universe" > /etc/apt/sources.list && \
    apt-get update && \
    apt-get upgrade -y && \
    apt-get -y install git golang runit && \
    apt-get autoclean && apt-get clean && apt-get autoremove
   

# download, extract and install Karaf
WORKDIR /tmp
RUN wget http://archive.apache.org/dist/karaf/${KARAF_VERSION}/apache-karaf-${KARAF_VERSION}.tar.gz && \
    tar xzf apache-karaf-${KARAF_VERSION}.tar.gz && \
    mv apache-karaf-${KARAF_VERSION} ${KARAF_HOME} && \
    rm -rf /tmp/apache-karaf-${KARAF_VERSION}.tar.gz && \
    rm -rf ${KARAF_HOME}/deploy/*

# add roles
ADD users.properties /karaf/etc/

# Download jolokia agent
RUN wget http://central.maven.org/maven2/org/jolokia/jolokia-jvm/${JOLOKIA_VERSION}/jolokia-jvm-${JOLOKIA_VERSION}-agent.jar -O ${KARAF_HOME}/jolokia-agent.jar

ENV KARAF_OPTS -javaagent:/$KARAF_HOME/jolokia-agent.jar=host=0.0.0.0,port=8778,authMode=jaas,realm=karaf,user=admin,password=admin,agentId=$HOSTNAME
ENV PATH $PATH:$KARAF_HOME/bin

# Add runnable scripts
ADD run_karaf.sh /etc/service/karaf/run
RUN chmod u+x /etc/service/karaf/run

# To run in debug mode
#RUN echo "log4j.rootLogger=TRACE, out, osgi:*" >> /karaf/etc/org.ops4j.pax.logging.cfg

CMD ["/usr/bin/runsvdir", "-P", "/etc/service"]