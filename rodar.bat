@echo off
REM Limpa o JAVA_HOME apenas para esta execucao, forcando o uso do Java do PATH (Java 17)
set JAVA_HOME=
echo Iniciando o back-end com Java 17...
call mvnw.cmd spring-boot:run
