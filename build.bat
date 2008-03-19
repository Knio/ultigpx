rm -rf bin
rm UltiGPX.jar
mkdir bin

rem xcopy resources           bin\guigtx\resources\ /EXCLUDE:ignore
rem xcopy resources\busyicons bin\guigtx\resources\busyicons\ /EXCLUDE:ignore

rem jar xf jdom.jar org
rem jar xf jdic.jar org

rem jar xf AbsoluteLayout.jar org
rem jar xf appframework-1.0.3.jar org
rem jar xf swing-worker-1.1.jar org

javac ultigpx\UltiGPX.java -d bin -cp .;jdic.jar;jdom.jar;AbsoluteLayout.jar;appframework-1.0.3.jar;swing-worker-1.1.jar
jar cmf Manifest.txt UltiGPX.jar -C bin . org
