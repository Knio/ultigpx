rm -rf bin/*
rm UltiGPX.jar

javac ultigpx\UltiGPX.java -d bin
jar cvmf Manifest.txt UltiGPX.jar -C bin .