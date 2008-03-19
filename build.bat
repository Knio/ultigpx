rm -rf bin
rm UltiGPX.jar
mkdir bin
javac ultigpx\UltiGPX.java -d bin -cp .;jdic.jar;jdom.jar;AbsoluteLayout.jar;appframework-1.0.3.jar;swing-worker-1.1.jar
jar cmf Manifest.txt UltiGPX.jar -C bin . org
