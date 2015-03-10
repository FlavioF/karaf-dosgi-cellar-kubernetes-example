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
    apt-get -y install git golang runit

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

# Karaf Cellar Kubernetes configuration in $KARAF/etc
ADD org.apache.karaf.cellar.kubernetes.cfg /karaf/etc/

# Build logstash-forwarder
RUN cd / && \
  git clone git://github.com/elasticsearch/logstash-forwarder.git source && \
  cd source && \
  go build && \
  cd / && \
  mkdir /logstash-forwarder && \
  mv source/source /logstash-forwarder/logstash-forwarder && \
  rm -rf source && \
  apt-get -y remove git golang && \
  apt-get -y autoremove && \
  apt-get autoclean

# Add configuration files
ADD logstash-forwarder.conf /logstash-forwarder/logstash-forwarder.conf
ADD certs /logstash-forwarder/certs

# put app features.xml to $KARAF/deploy
ADD target/classes/features.xml /karaf/deploy/

# Add runnable scripts
#ADD run_logstash_forwarder.sh /etc/service/logstash-forwarder/run
#RUN chmod u+x /etc/service/logstash-forwarder/run
ADD run_karaf.sh /etc/service/karaf/run
RUN chmod u+x /etc/service/karaf/run

CMD ["/usr/bin/runsvdir", "-P", "/etc/service"]
