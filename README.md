# Football Club Management System (JavaFX) 
This is a simple football player/club management system made with JavaFX. 

![Preview of Football Club Management System](https://raw.githubusercontent.com/WJGalib/1-2_term-project-java/main/preview.png)

## Running the App

Pre-compiled JARs ([Download](https://github.com/WJGalib/1-2_term-project-java/releases/download/1.0.0/1-2_term-project-java_football-manager_1905084.zip)) are included in the repository in [realease 1.0.0](https://github.com/WJGalib/1-2_term-project-java/releases/tag/1.0.0).
The JAR files can be run with:

```java -jar server.jar```

```java -jar client.jar```

To quit the server safely writing all changes to disk, type the command ```exit``` into the terminal it's running on.

## Building from source

The repository consists of a Maven project. To initialise and run the project, download the source code, install [Apache Maven](https://maven.apache.org/), navigate to project folder and run:

``` mvn clean install ```

``` mvn clean javafx:run ```


There are two executable classes: ```edu.buet.Main``` and ```edu.buet.server.Main``` that need to run separately as the client and server respectively. To built an executable standalone JAR of the client, run:

 ``` mvn package ```


To build an executable standalone JAR of the server, navigate to ```pom.xml``` and change ```<mainClass>edu.buet.Main</mainClass>``` to ```<mainClass>edu.buet.server.Main</mainClass>``` and run:

 ``` mvn package ```


The JARs are produced by default in the ```target``` folder of the Maven project with the name of the project. To preserve the client JAR, you will have to rename it first and then build the server JAR. The ```server.jar``` must be placed in the same directory as the ```data``` folder (by default the project directory).


To actually run over a network, the server IP (static) address (for client) and port (optionally) need to be specified through command-line arguments. Details of their usage can be found by runnning: 

``` java -jar server.jar --help ```

``` java -jar client.jar --help ```