Karaf DOSGi Cellar Example
============================
This is a simple example of Karaf Cellar DOSGi Application.

Note: This only works with the version of Karaf Cellar available [here](https://github.com/albertocsm/karaf-cellar) since the original version is still with blocking issues to test it successfully (look the the open PRs [here](https://github.com/apache/karaf-cellar) for more information).

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

## Build Docker Containers
## Build Your Application Containers
Note: You can use an available image

* Move to docker/docker-karaf/
* Build Docker Image
```
    docker build --rm -t=flaviof/karaf .
```
* Push Image to Docker Hub
```
    docker push flaviof/karaf1
```
### Build Karaf Cellar Image
This image adds cellar and kubernetes related configurations to Karaf (eg. Hazelcast.xml)

* Move to docker/docker-karaf-cellar/
* Build Docker Image
```
    docker build --rm -t=flaviof/karaf-cellar .
```
* Push Image to Docker Hub
```
    docker push flaviof/karaf-cellar
```

### Build App 1 (Service) Container
This image adds a feature containing Gretter Service and Gretter API bundles.

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