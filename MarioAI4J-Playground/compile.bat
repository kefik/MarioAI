cd bin
rmdir /s /q ch
cd ..
dir /s /B *.java > sources.txt
javac -d bin -cp bin;../MarioAI4J/bin;../MarioAI4J/lib/simple-logging-1.0.1.jar;../MarioAI4J-Tournament/bin;../MarioAI4J-Tournament/lib/*;lib/* @sources.txt
del /Q sources.txt