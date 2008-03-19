rm -rf bin
rm UltiGPX.jar
mkdir bin


xcopy resources           bin\guigtx\resources\ /EXCLUDE:ignore
xcopy resources\busyicons bin\guigtx\resources\busyicons\ /EXCLUDE:ignore
jar xf jdom.jar org
jar xf jdic.jar org
rem jar xf AbsoluteLayout.jar org
rem jar xf appframework-1.0.3.jar org
rem jar xf swing-worker-1.1.jar org



javac ultigpx\UltiGPX.java -d bin -cp .;jdic.jar;jdom.jar;AbsoluteLayout.jar;appframework-1.0.3.jar;swing-worker-1.1.jar
jar cmf Manifest.txt UltiGPX.jar -C bin . org
