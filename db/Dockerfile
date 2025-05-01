FROM mysql:latest

ENV MYSQL_DATABASE=raon

COPY init.sql /docker-entrypoint-initdb.d
COPY location.csv /var/lib/mysql-files
