rm -rf bin
rm UltiGPX.jar
mkdir bin

javac ultigpx\UltiGPX.java -d bin -cp .;jdic.jar;jdom.jar; -Xlint:unchecked
jar cmf Manifest.txt UltiGPX.jar -C bin . org resources
