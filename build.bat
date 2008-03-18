rm -rf bin/*
rm UltiGPX.jar

javac ultigpx\UltiGPX.java -d bin -cp .;jdom.jar;AbsoluteLayout.jar;appframework-1.0.3.jar;swing-worker-1.1.jar
cp -r ultigpx\resources bin\ultigpx\resources
jar xf jdom.jar org
rem jar xf AbsoluteLayout.jar org
rem jar xf appframework-1.0.3.jar org
rem jar xf swing-worker-1.1.jar org
jar vcmf Manifest.txt UltiGPX.jar -C bin . org