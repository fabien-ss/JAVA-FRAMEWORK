cd Framework/build/web/WEB-INF/classes
jar -cf files.jar  .
mv files.jar  ../../../../../Test-framework/WEB-INF/lib/
cd ../../../../../Test-framework
jar -cf Framework3.war  .
mv Framework3.war /home/fabien/Documents/apache-tomcat-9.0.72/webapps/
