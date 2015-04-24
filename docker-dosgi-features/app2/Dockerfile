FROM flaviof/karaf

# Add hazelcast configuration to be used by cellar
ADD config/hazelcast.xml $KARAF_HOME/etc/
# Disable auto replication of everything except config
ADD config/org.apache.karaf.cellar.groups.cfg $KARAF_HOME/etc/


# put app features.xml to $KARAF/deploy
ADD target/classes/features.xml /karaf/deploy/
