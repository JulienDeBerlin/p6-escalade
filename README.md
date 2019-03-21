#  Openclassrooms JAVA Path
## Project 6: create a collaborative web application for rock-climbers

### **Presentation**

This application is the 6th project of the [Openclassrooms JAVA learning path](https://openclassrooms.com/en/paths/88-developpeur-dapplication-java).
It has been concieved as a collaborative plateform where rock-climbers can share information such as spots and guidebooks. 

### **Features**

**All users can:**
* research climbing spots based on differents criteria such as location, rating of the routes, equipement of the route (bolted or not)
* research guidebooks that describe some spots based on the location
* possibility to navigate from guidebook to matching spots and from spot to matching guidebooks

**Registered members can:**
* comment the spots
* add some content (new spots and new guidebooks)
* offer their private guidebooks for loan to the members' community. 
* send a request to other members to borrow a specific guidebook

**Admin** 

Under the logging-email "superadmin@admin.fr" it is possible to:
* delete comments
* update or delete routes and guidebook


### **Configuration**
* This application is a **Maven** project, developped in Java. The bytecode is compatible with **Java 8** and more. 
* Server used in developpement is **Tomcat 9.0.13**
* The database used is **PostgreSQL 9.6.** 


### **Install the app on your PC**

1/ clone this repository on your PC

2/ create a database on PostgreSQL and use the dump of the demo-database available in this repo to import all the data in your database

3/ build the project with Maven (mvn package). A war file (p6.war) should be created in the target folder of the webapp module

4/ if Tomcat is not installed on your PC yet, install it. 

5/ deploy the war file on tomcat (copy and paste or use the tomcat manager)

6/ in order to allow the app to connect to your database, you need to declare a datasource named "jdbc/P6_DB" in tomcat. The datasource should be declared in the context.xml file of tomcat. For instance: 
```xml
<Resource name="jdbc/P6_DB" auth="Container" type="javax.sql.DataSource" driverClassName="org.postgresql.Driver" url="jdbc:postgresql://127.0.0.1:5433/yourDatabase" username="username" password="password" maxTotal="20" maxIdle="10" maxWaitMillis="-1"/>
```
For more information on this step have a look on the [Tomcat documentation](https://tomcat.apache.org/tomcat-9.0-doc/jndi-resources-howto.html#context.xml_configuration). 

7/ start Tomcat and go to http\://yourServerAdress/p6, for instance http\://localhost:8080/p6/ 

8/ the demo-database include a few demo-members whose credentials you can use to test the app. 
* For instance, you can log as a member with these credentials: email = caroline@hotmail.com, password = gygy654!
* to test the admin features, log as superadmin: email = superadmin@admin.fr, password = superadmin

9/ Enjoy!
