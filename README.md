#  Openclassrooms JAVA Path
## Project 6: create a collaborative web application for rock-climbers

### **Presentation**

This application is the 6th project of the [Openclassrooms JAVA learning path](https://openclassrooms.com/en/paths/88-developpeur-dapplication-java).
It has been concieved as a collaborative plateform where rock-climbers can share information such as spots and guidebooks. 

### **Features**

**All users can:**
* research of climbing spots based on differents criteria such as location, rating of the routes, equipement of the route (bolted or not)
* research of climbing guidebooks that describe some spots based on the location
* possibility to navigate from guidebook to matching spots and from spot to matching guidebooks

**Registered members can:**
* comment the spots
* add some content (new spots and new guidebooks)

**Administration** under the logging-email "superadmin@admin.fr" it is possible to:
* delete comments
* update or delete routes and guidebook


### **Configuration**
* This application is a **Maven** project, developped in Java. The bytecode is compatible with **Java 8** and more. 
* Server used in developpement is Tomcat 9.0.13
* The database used is PostgreSQL 9.6. 
* The JNBI ressource required to connect to the database is set in the file "context.xml" (in Webapp> META-INF ) and is also part of the WAR. 
