@echo off
echo Current directory: %cd%

set SUITE_PATH=src\test\resources
set RESULTS_PATH=.\allure-results

:: Clear old Allure results
if exist %RESULTS_PATH%\* (
    echo Deleting old Allure results...
    del /Q /F %RESULTS_PATH%\*
)

:: Run happy-path-suite.xml using Maven with surefire.suiteXmlFiles
call mvn clean test -Dsurefire.suiteXmlFiles=.\src\test\resources\post_suite.xml

:: Serve the Allure report from the allure-results directory
call npx allure-commandline serve %RESULTS_PATH% --single-file