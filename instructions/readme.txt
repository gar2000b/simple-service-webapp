Make sure you execute the following so that eclipse sets up the configurations for the WTP (Web Tools Project).
This then allows you to drag the web app project / servlet based into a server container set up in eclipse:

mvn eclipse:eclipse -Dwtpversion=2.0

Note: when you add the Tomcat Server instance, double click on it to bring up the config screen and choose:
Use Tomcat Installation