# you know you love it
FROM ubuntu:14.04

# expose HTTP, SSH, Jolokia and Hazelcast (for Cellar)
EXPOSE 8181 8101 8778 5701

ENV KARAF_VERSION 3.0.2
ENV KARAF_HOME /home/karaf/karaf
ENV DEPLOY_DIR $KARAF_HOME/deploy

ENV JOLOKIA_VERSION 1.2.3

ENV JAVA_HOME /usr/lib/jvm/jre

RUN echo "deb http://archive.ubuntu.com/ubuntu trusty main universe" > /etc/apt/sources.list && \
    apt-get update && \
    apt-get upgrade -y

# install Oracle JRE 8
RUN apt-get -y install software-properties-common && \
    add-apt-repository ppa:webupd8team/java && \
    apt-get -y update && \
    echo "oracle-java8-installer  shared/accepted-oracle-license-v1-1 boolean true" | debconf-set-selections && \
    apt-get -y install oracle-java8-installer && \
    apt-get install oracle-java8-set-default && \
    apt-get autoclean && \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/* /var/cache/oracle-jdk8-installer

# enabling sudo group
RUN echo '%sudo ALL=(ALL) NOPASSWD:ALL' >> /etc/sudoers
# enabling sudo over ssh
RUN sed -i 's/.*requiretty$/#Defaults requiretty/' /etc/sudoers

# add karaf user with sudo permissions
RUN adduser --disabled-password --gecos '' karaf
RUN adduser karaf sudo

# command line goodies
RUN echo "export JAVA_HOME=/usr/lib/jvm/jre" >> /etc/profile
RUN echo "alias ll='ls -l --color=auto'" >> /etc/profile
RUN echo "alias grep='grep --color=auto'" >> /etc/profile

# download, extract and install Karaf
WORKDIR /home/karaf
USER karaf
RUN wget http://archive.apache.org/dist/karaf/${KARAF_VERSION}/apache-karaf-${KARAF_VERSION}.tar.gz
RUN tar xzf apache-karaf-${KARAF_VERSION}.tar.gz
RUN ln -s apache-karaf-${KARAF_VERSION} karaf
RUN rm apache-karaf-${KARAF_VERSION}.tar.gz

# add roles
ADD users.properties /home/karaf/apache-karaf-${KARAF_VERSION}/etc/

# jolokia agent
RUN wget http://central.maven.org/maven2/org/jolokia/jolokia-jvm/${JOLOKIA_VERSION}/jolokia-jvm-${JOLOKIA_VERSION}-agent.jar -O ${KARAF_HOME}/jolokia-agent.jar

# clear deploy folder
RUN rm -rf ${DEPLOY_DIR}/*

ENV KARAF_OPTS -javaagent:/$KARAF_HOME/jolokia-agent.jar=host=0.0.0.0,port=8778,authMode=jaas,realm=karaf,user=admin,password=admin,agentId=$HOSTNAME
ENV PATH $PATH:$KARAF_HOME/bin