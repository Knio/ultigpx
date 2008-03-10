rm -rf bin/*
rm UltiGPX.jar
javac ultigpx\UltiGPX.java -d bin -cp .;jdom.jar
jar vxf jdom.jar org
jar cvmf Manifest.txt UltiGPX.jar -C bin . org