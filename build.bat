rm -rf bin/*
rm UltiGPX.jar
javac ultigpx\UltiGPX.java -d bin -cp .;jdom.jar;AbsoluteLayout.jar;appframework-1.0.3.jar;swing-worker-1.1.jar
jar xf jdom.jar org
jar xf AbsoluteLayout.jar org
jar xf appframework-1.0.3.jar org
jar xf swing-worker-1.1.jar org
jar cmf Manifest.txt UltiGPX.jar -C bin . org