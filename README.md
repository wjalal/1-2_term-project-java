# 1-2_term-project-java

This is a simple football player/club management system made with JavaFX. The repository consists of a Maven project. To initialise and run the project, download the source code, install [Apache Maven](https://maven.apache.org/) and run:

``` mvn clean install ```

``` mvn clean javafx:run ```


There are two executable classes: ```edu.buet.Main``` and ```edu.buet.server.Main``` that need to run separately as the client and server respectively. To built an executable standalone JAR of the client, run:

 ``` mvn package ```


 To build an executable standalone JAR of the server, navigate to ```pom.xml``` and change ```<mainClass>edu.buet.Main</mainClass>``` to ```<mainClass>edu.buet.server.Main</mainClass>``` and run:

 ``` mvn package ```


 The JARs are produced by default in the ```target``` folder of the Maven project with the name of the project. To preserve the client JAR, you will have to rename it first and then build the server JAR. The ```server.jar``` must be placed in the same directory as the ```data``` folder (by default the project directory).

Pre-compiled JARs are included in the repository. 

The JAR files can be run with:

```java -jar server.jar```

```java -jar client.jar```

To quit the server safely writing all changes to disk, type the command ```exit``` into the terminal it's running on.