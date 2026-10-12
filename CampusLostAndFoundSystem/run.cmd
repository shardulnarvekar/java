@echo off
setlocal
cd /d "%~dp0"
java -Dfile.encoding=UTF-8 -cp "build\classes;src;lib/*" com.campus.lostandfound.UI.LoginPage
exit /b %errorlevel%
