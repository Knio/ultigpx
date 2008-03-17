rm -rf bin/*
rm UltiGPX.jar
javac ultigpx\UltiGPX.java -d bin -cp .;jdom.jar
jar xf jdom.jar org
jar cmf Manifest.txt UltiGPX.jar -C bin . org