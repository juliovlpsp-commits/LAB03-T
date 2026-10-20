if (-not (Test-Path junit.jar)) {
    Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.0/junit-platform-console-standalone-1.10.0.jar" -OutFile "junit.jar" -UseBasicParsing
}
New-Item -ItemType Directory -Force -Path bin | Out-Null
javac -encoding UTF-8 -d bin src\filmnow\*.java
if ($LASTEXITCODE -ne 0) { exit 1 }
javac -encoding UTF-8 -cp "junit.jar;bin" -d bin test\filmnow\*.java
if ($LASTEXITCODE -ne 0) { exit 1 }
java -Dfile.encoding=UTF-8 -jar junit.jar --class-path bin --scan-class-path
