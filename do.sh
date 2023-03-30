cd Source
javac -cp lib/* -d . *java
jar -cf files.jar .
cd ../Test-framework
cp ../Source/files.jar  ./WEB-INF/lib/

jar -cf Framework9.war .
cp Framework9.war /home/fabien/Documents/apache-tomcat-9.0.72/webapps/
