class_path="$HOME/GitHub/Framework/Framework/build/web/WEB-INF/classes"
librairies_path="$HOME/Documents/GitHub/Framework/Test-framework/WEB-INF/lib"
test_class_path="$HOME/Documents/GitHub/Framework/Test-framework/WEB-INF/classes"

cd $class_path
jar -cf etu2004Files.jar .
mv etu2004Files.jar $librairies_path
cd $test_class_path
ls ../lib
javac -cp "../lib/*" -d . *java
