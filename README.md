ardoq-java-client
=================

Small Java wrapper for the [Ardoq](http://ardoq.com) REST-api.

###Install
Add the ardoq maven repository to your pom.xml
```xml
    <repositories>
        <repository>
            <id>ardoq.com</id>
            <url>http://maven.ardoq.com/snapshot/</url>
        </repository>
    </repositories>
```

Add `ardoq-java-client` to your dependencies

```xml
    <dependencies>
        ...
        <dependency>
            <groupId>com.ardoq.api</groupId>
            <artifactId>client</artifactId>
            <version>1.0</version>
        </dependency>
        ...
    </dependencies>
```
###Usage
```java
ArdoqClient client = new ArdoqClient("hostname", "username", "password");
```
The client will operate on the default organization (Personal). To change this
```java
ArdoqClient client = new ArdoqClient("hostname", "username", "password").setOrganization("my-organization");
```
####Model
The model API is not stable yet, so you have to create your Model in the UI and refer to the id.
####Workspace
#####Creating a workspace
```java
ArdoqClient client = new ArdoqClient("hostname", "username", "password");
client.workspace().createWorkspace(new Workspace("workspace-name", "<model-id>", "Description"))
```
####More examples
The api is pretty straight forward. For more examples, please refer to the [tests](https://github.com/ardoq/ardoq-java-client/tree/master/src/test/java/com/ardoq/service).
