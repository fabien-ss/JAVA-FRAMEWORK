cd Framework/build/web/WEB-INF/classes
jar -cf etu2004Files.jar .
mv etu2004Files.jar ../../../../../Test-framework/WEB-INF/lib
cd ../../../../../Test-framework
jar -cvf etu2004Framework.war .
mv etu2004Framework.war /home/fabien/Documents/apache-tomcat-9.0.72/webapps