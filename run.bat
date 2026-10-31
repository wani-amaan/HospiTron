@echo off
setlocal

title HospiTron Hospital System

echo ==========================
echo     HOSPITRON SYSTEM
echo     CLEAN RUN LAUNCHER
echo ==========================

cd /d "%~dp0"

if /I "%1"=="clean" (
    echo.
    echo [1/3] Cleaning data and build files...
    if exist data rmdir /s /q data
    if exist out rmdir /s /q out
    echo ✔ Data & build cleared
) else (
    echo.
    echo [1/3] Verifying directories...
)

if not exist data mkdir data
if not exist out mkdir out

echo ✔ Directories ready

echo.
echo [2/3] Compiling project (Java 27)...

javac --release 27 -d out ^
src\main\Main.java ^
src\ui\*.java ^
src\controller\*.java ^
src\service\*.java ^
src\model\*.java ^
src\data\*.java ^
src\algorithm\*.java ^
src\util\*.java ^
src\security\*.java

IF %ERRORLEVEL% NEQ 0 (
    echo.
    echo ❌ COMPILATION FAILED
    echo Check errors above
    pause
    exit /b
)

echo ✔ Compilation successful

echo.
echo [4/4] Running HospiTron...

java -cp "out" main.Main

echo.
echo ==========================
echo   APPLICATION CLOSED
echo ==========================

pause
endlocal