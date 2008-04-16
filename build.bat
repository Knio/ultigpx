rm -rf bin
rm UltiGPX.jar
mkdir bin

javac ultigpx\UltiGPX.java -d bin -cp .;jdic.jar;jdom.jar;datetimepicker2.7.3.jar; -Xlint:unchecked -Xlint:deprecation
jar cmf Manifest.txt UltiGPX.jar -C bin . org resources
