cd bin
rmdir /s /q ch
cd ..
dir /s /B *.java > sources.txt
javac -d bin -cp bin @sources.txt
del /Q sources.txt
xcopy src\ch\idsia\benchmark\mario\engine\resources bin\ch\idsia\benchmark\mario\engine\resources /s /e /y