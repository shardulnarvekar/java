@echo off
setlocal EnableDelayedExpansion
cd /d "%~dp0"
if not exist "build\classes" mkdir "build\classes"
if errorlevel 1 exit /b 1
>"build\sources.txt" (
  for /R "src" %%F in (*.java) do (
    set "sourcePath=%%~fF"
    set "sourcePath=!sourcePath:\=/!"
    echo "!sourcePath!"
  )
)
javac --release 17 -encoding UTF-8 -cp "lib/*" -d "build\classes" @build\sources.txt
exit /b %errorlevel%
