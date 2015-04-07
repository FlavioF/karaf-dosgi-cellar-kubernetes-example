karaf-cellar-example
============================
This is a simple example of Karaf Cellar DOSGi Application.

# Pre-requisites

* JDK 8
* Maven 3.1.0 or newer
* Docker 1.5

## Build Greeter Example
* Move to dosgi-greeter
* Install with manven
```
    mvn clean install
```

## Build Your Application Containers
### Build App 1 (Service) Container

* Move to docker/dosgi-containers/app1/
* Install with manven
```
    mvn clean install
```
* Start
```
    bash debug.sh app1 client
```
### Build App 2 (Client) Container
* Move to docker/dosgi-containers/app2/
* Install with manven
```
    mvn clean install
```
* Start
```
    bash debug.sh app2 client
```

# Testing DOSGi with Karaf Cellar
* SSH into node-01 of the Kubernetes Cluster
* Enter in a docker container running App2
```
   docker ps
   docker exec -it <containerId> bash
```
* In App2 Karaf's client run
```
admin@root()> dosgi-greeter:greet Hello 1
    Hello.Hello from node app1-jmxbo:5701 count 0.
```
