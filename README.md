karaf-cellar-example
============================
This is a simple example of Karaf Cellar DOSGi Application.

# Pre-requisites

* JDK 8
* Maven 3.1.0 or newer
* Docker 1.5

## Build Greeter Example
* Move to ./dosgi-greeter
* Install with maven
```
    mvn clean install
```

## Build Your Application Containers
### Build App 1 (Service) Container

* Move to ./docker/dosgi-containers/app1/
* Install with maven
```
    mvn clean install
```
* Start
```
    ./debug.sh app1 client
```
### Build App 2 (Client) Container
* Move to ./docker/dosgi-containers/app2/
* Install with maven
```
    mvn clean install
```
* Start
```
    ./debug.sh app2 client
```

# Testing DOSGi with Karaf Cellar
* In App2 Karaf Client run:
```
admin@root()> dosgi-greeter:greet Hello 1
```
* You should get a response similar to:
```
Hello.Hello from node 675d6b2875da:5701 count 0.
```