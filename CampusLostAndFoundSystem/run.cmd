@echo off
setlocal
cd /d "%~dp0"
if not exist "build\classes\campus\lost\and\found\system\LoginForm.class" (
  call compile.cmd
  if errorlevel 1 exit /b 1
)
java -Dfile.encoding=UTF-8 -cp "build\classes;lib/mysql-connector-java-8.0.25.jar" campus.lost.and.found.system.LoginForm
exit /b %errorlevel%
