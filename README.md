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
###Starting a small project
```java
ArdoqClient client = new ArdoqClient("app.ardoq.com", "username", "password");
Model model = client.model().getModelByName("Application Service");
Workspace testWorkspace = new Workspace("workspace-name", model.getId(), "Description");
Workspace workspace = client.workspace().createWorkspace(testWorkspace);

ComponentService componentService = client.component();
Component parent = componentService.createComponent(new Component("name", workspace.getId(), "myDescription"));
Component childA = componentService.createComponent(new Component("a", workspace.getId(), "myDescription", parent.getId()));
Component childB = componentService.createComponent(new Component("b", workspace.getId(), "myDescription", parent.getId()));

Reference ref = new Reference(workspace.getId(), childA.getId(), childB.getId(), model.getReferenceTypeByName("Synchronous"));
client.reference().createReference(ref);
...
Tag tag = client.tag().createTag(new Tag("REST", workspace.getId(), "description", components, references));
```
####Models
The model API is not stable yet, so you have to create your Model in the UI and refer to the id.
####More examples
The api is pretty straight forward. For more examples, please refer to the [tests](https://github.com/ardoq/ardoq-java-client/tree/master/src/test/java/com/ardoq/service).
