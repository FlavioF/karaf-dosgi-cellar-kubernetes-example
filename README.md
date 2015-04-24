Karaf Cellar Kubernetes Example
============================
This is a simple example of Karaf Cellar DOSGi Application with Kubernetes Cluster in a Dev Environment.

If you are looking for an DOSGi example not using Kubernetes change to this [branch](https://github.com/FlavioF/karaf-cellar-kubernetes-example/tree/karaf_cellar_example)

Note: This only works with the version of Karaf Cellar available [here](https://github.com/albertocsm/karaf-cellar) since the original version is still with blocking issues to test it successfully (look the the open PRs [here](https://github.com/apache/karaf-cellar) for more information).

# Pre-requisites

* JDK 8
* Maven 3.1.0 or newer
* Docker 1.5

## Start Kubernetes Cluster
If you're looking for details on how kubernetes cluster works take a look at [Kubenertes Cluster](https://github.com/pires/kubernetes-vagrant-coreos-cluster)

* Clone the Kubernetes Cluster
```
git clone https://github.com/pires/kubernetes-vagrant-coreos-cluster.git
```
* Add sync folder configuration to synced_folders.yaml 
```
 - name: default
   source: ~/.m2/repository
   destination: /home/core/.m2/repository
   nfs: true
   mount_options: 'nolock,vers=3,udp,noatime'
   disabled: false
```
* Follow the instructions described in [Kubenertes Cluster](https://github.com/pires/kubernetes-vagrant-coreos-cluster)

# Build

## Clone
```
git clone https://github.com/FlavioF/karaf-cellar-kubernetes-example.git
cd karaf-cellar-kubernetes-example
```

## Build Docker Images
### Build App 1 (Service) Image
This image adds a feature containing Gretter Service and Gretter API bundles.

* Move to docker-dosgi-features/app1/
* Compile it with maven
```
    mvn clean install
```
* Build Docker Image
```
    docker build --rm -t=flaviof/app1 .
```
* Push Image to Docker Hub
```
    docker push flaviof/app1
```

### Build App 2 (Client) Image
This image adds a feature containing Gretter Client and Gretter API bundles.

* Move to docker-dosgi-features/app2/
* Compile it with maven
```
    mvn clean install
```
* Build Docker Image
```
    docker build --rm -t=flaviof/app2 .
```
* Push Image to Docker Hub
```
    docker push flaviof/app2
```

## Build Greeter Example
* Move to dosgi-greeter
* Install with manven
```
    mvn clean install
```

# Run Apps in Kubernetes Cluster
* Move to dosgi-containers
* Running App 1
```
    kubectl create -f app1/app1-controller.yaml 
```
* Running App 2
```
    kubectl create -f app2/app2-controller.yaml 
```

# Testing DOSGi with Karaf Cellar in Kubernetes
* SSH into node-01 of the Kubernetes Cluster
* Enter in a docker container running App2
```
   docker ps
   docker exec -it <containerId> bash
```
* Run Karaf Client
```
   /karaf/bin/client

           __ __                  ____      
       / //_/____ __________ _/ __/      
      / ,<  / __ `/ ___/ __ `/ /_        
     / /| |/ /_/ / /  / /_/ / __/        
    /_/ |_|\__,_/_/   \__,_/_/         

  Apache Karaf (3.0.3)

Hit '<tab>' for a list of available commands
and '[cmd] --help' for help on a specific command.
Hit 'system:shutdown' to shutdown Karaf.
Hit '<ctrl-d>' or type 'logout' to disconnect shell from current session.

admin@root()> 
```

* Testing DOSGi with Karaf Cellar
```
admin@root()> dosgi-greeter:greet Hello 2
    Hello.Hello from node app1-jmxbo:5701 count 0.
    Hello.Hello from node app1-jmxbo:5701 count 1.
```
