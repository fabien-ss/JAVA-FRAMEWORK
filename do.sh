chemin_webapps="/home/fabien/Documents/apache-tomcat-9.0.72/webapps"
package_name="objet"
webxml_path="Test-framework/WEB-INF/web.xml"

echo "Création du répertoire temporaire"
mkdir temp
cd temp
mkdir WEB-INF
cd WEB-INF
mkdir classes
cd classes 
mkdir $package_name
cd ..
mkdir lib
cd ../..    

echo "Copie des fichiers jsp"
cp Test-framework/*.jsp temp/
echo "Copie des fichiers de configuraiton"
cp Test-framework/WEB-INF/web.xml temp/WEB-INF/
echo "Copie de la librairie"
cp Test-framework/WEB-INF/lib/etu2004Files.jar temp/WEB-INF/lib/
echo "Copie des classes"

cp Test-framework/WEB-INF/classes/$package_name/*.class temp/WEB-INF/classes/$package_name/

cd temp
jar -cvf etu2004Framework.war .
echo "Suppression du répertoire temporaire"
mv etu2004Framework.war $chemin_webapps
cd ..
rm -r temp