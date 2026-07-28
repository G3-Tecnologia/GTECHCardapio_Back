REM Limpa o JAVA_HOME apenas para esta execucao, forcando o uso do Java do PATH (Java 17)
set JAVA_HOME=
@echo off
IF EXIST .env (
    echo Carregando variaveis do .env...
    FOR /F "usebackq eol=# tokens=1,* delims==" %%A IN (".env") DO (
        set %%A=%%B
    )
)

echo Iniciando o back-end com Java 17...
call mvnw.cmd spring-boot:run
