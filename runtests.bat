@echo off
if not exist junit.jar (
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.0/junit-platform-console-standalone-1.10.0.jar' -OutFile 'junit.jar' -UseBasicParsing"
)
mkdir bin 2>nul
javac -encoding UTF-8 -d bin src\filmnow\*.java
if errorlevel 1 exit /b 1
javac -encoding UTF-8 -cp "junit.jar;bin" -d bin test\filmnow\*.java
if errorlevel 1 exit /b 1
java -Dfile.encoding=UTF-8 -jar junit.jar --class-path bin --scan-class-path
