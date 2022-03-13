User interface locally

	http://localhost:8080/jakartaee8-migration/index.xhtml

REST-controller end points locally

	http://localhost:8080/jakartaee8-migration/api/user-controller/users

	http://localhost:8080/jakartaee8-migration/api/user-controller/user/11

Running Arquillian tests

	Install maven and add its bin folder to path
	Set up JBOSS_HOME and JAVA_HOME as environmental variables

	run the following on command line:

		mvn clean verify -Parq-wildfly-managed