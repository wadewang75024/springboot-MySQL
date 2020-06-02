1. Here is in eclipse Program arguments for run it:

C:\DEV\springboot-MySQL\springboot-MySQL\config\application.properties

2. This application is enhanced on 6/1/2020 to be able to work with
both Hibernate 5 and spring JPA. Here is how to run it.

	- To run with Hibernate, 
		uncomment the following line in application.properties
			spring.profiles.active=hibernate
		and comment out the following line in application.properties
			spring.profiles.active=jpa
	- To run with JPA
		uncomment the following line in application.properties
			spring.profiles.active=jpa
		and comment out the following line in application.properties
			spring.profiles.active=hibernate
	
	This profile switch will impact some of beans in Configer.java
	and MainApp will pick BusinessApplication accordingly.