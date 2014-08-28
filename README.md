ardoq-java-client
=================

Small Java wrapper for the [Ardoq](http://ardoq.com) REST-api.

###Install
Add the ardoq maven repository to your pom.xml
```xml
    <repositories>
        <repository>
            <id>ardoq.com</id>
            <url>http://maven.ardoq.com/release/</url>
        </repository>
    </repositories>
```

Add `ardoq-java-client` to your dependencies.

```xml
    <dependencies>
        ...
        <dependency>
            <groupId>com.ardoq.api</groupId>
            <artifactId>client</artifactId>
            <version>0.8.4</version>
        </dependency>
        ...
    </dependencies>
```

**NOTE** This repository is not browsable so your IDE won't pick it up automatically. Use the mvn cli to download it `mvn install`

###Usage
```java
//Basic auth
ArdoqClient client = new ArdoqClient("hostname", "username", "password");
//Token
ArdoqClient client = new ArdoqClient("hostname", "token");
```
The client will operate on the default organization (Personal). To change this
```java
ArdoqClient client = new ArdoqClient("hostname", "username", "password").setOrganization("my-organization");
```
###Starting a small project
```java
ArdoqClient client = new ArdoqClient(host, ardoqUsername, ardoqPassword);
Model model = client.model().getModelByName("Application service");
Workspace workspace = client.workspace().createWorkspace(new Workspace("demo-workspace", model.getId(), "Description"));
ComponentService componentService = client.component();

Component webshop = componentService.createComponent(new Component("Webshop", workspace.getId(), "Webshop description"));
Component webShopCreateOrder = componentService.createComponent(new Component("createOrder", workspace.getId(), "Order from cart", webshop.getId()));

Component erp = componentService.createComponent(new Component("ERP", workspace.getId(), ""));
Component erpCreateOrder = componentService.createComponent(new Component("createOrder", workspace.getId(), "", erp.getId()));
//Create a Synchronous integration between the Webshop:createOrder and ERP:createOrder services
Reference createOrderRef = new Reference(workspace.getId(), "Order from cart", webShopCreateOrder.getId(), erpCreateOrder.getId(), model.getReferenceTypeByName("Synchronous"));
createOrderRef.setReturnValue("Created order");
Reference reference = client.reference().createReference(createOrderRef);

List<String> componentIds = Arrays.asList(webShopCreateOrder.getId(), erpCreateOrder.getId());
List<String> referenceIds = Arrays.asList(reference.getId());
client.tag().createTag(new Tag("Customer", workspace.getId(), "", componentIds, referenceIds));
```

Running this simple example let's Ardoq visualize the components and their relationships.

![Components](https://s3-eu-west-1.amazonaws.com/ardoq-resources/public/comps.png)  
######*Component landscape*
![Sequence diagram](https://s3-eu-west-1.amazonaws.com/ardoq-resources/public/sequence_diagram.png)
######*Sequence diagram*
![Relationships](https://s3-eu-west-1.amazonaws.com/ardoq-resources/public/rels.png)
######*Relationship diagram*
####Models
The model API is not stable yet, so you have to create your Model in the UI and refer to the id.
####More examples
The api is pretty straight forward. For more examples, please refer to the [tests](https://github.com/ardoq/ardoq-java-client/tree/master/src/test/java/com/ardoq/service).

###License

Copyright © 2014 Ardoq AS

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
