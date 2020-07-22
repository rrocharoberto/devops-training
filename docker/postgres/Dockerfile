FROM postgres:12.3-alpine

LABEL author="Roberto"
LABEL email="rrocha.roberto@gmail.com"
LABEL data="20/07/2020"

ENV POSTGRES_USER=aluno
ENV POSTGRES_PASSWORD=aluno
ENV POSTGRES_DB=aluno

#Transfer the database script file to imagem
COPY ./script.sql /docker-entrypoint-initdb.d/script.sql

EXPOSE 5432

#execute postgres inside the container
CMD ["postgres"]

