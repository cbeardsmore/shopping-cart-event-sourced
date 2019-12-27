FROM postgres:11.1-alpine
COPY ./app/src/main/resources/db/* /docker-entrypoint-initdb.d/
RUN cd /docker-entrypoint-initdb.d/ && ls
