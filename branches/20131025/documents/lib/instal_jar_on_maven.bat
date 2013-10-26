@echo off
mvn install:install-file -Dfile=bwcaptcha.jar -DgroupId=org.zkoss.zkforge -DartifactId=bwcaptcha -Dversion=1.0 -Dpackaging=jar

mvn install:install-file -Dfile=pen_API_SDK.jar -DgroupId=com.anoto -DartifactId=anoto -Dversion=1.0 -Dpackaging=jar

