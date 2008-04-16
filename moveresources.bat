del /F /S /Q org
del /F /S /Q resources
del /F /S /Q jdic-0.9.4-bin-cross-platform

rm org -rf
rm resources -rf
rm jdic-0.9.4-bin-cross-platform -rf

jar xf jdom.jar org
jar xf jdic.jar org

unzip jdic-0.9.4-bin-cross-platform.zip

mkdir resources

cp jdic-0.9.4-bin-cross-platform\windows resources\windows -r
cp jdic-0.9.4-bin-cross-platform\sunos   resources\sunos -r
cp jdic-0.9.4-bin-cross-platform\linux   resources\linux -r
cp point_b.png resources
