cd Source && javac -cp lib/* -d . *java
cp -r etu2004 classes
cp -r utils classes
cd classes && jar -cf files.jar .
cd ../../Test-framework
cp ../Source/classes/files.jar  ./WEB-INF/lib/
jar -cvf monappli.war .
mv monappli.war ../
cd .. && mv monappli.war /home/fabien/Documents/apache-tomcat-9.0.72/webapps/
