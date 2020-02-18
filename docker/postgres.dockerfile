FROM postgres:11.1-alpine
COPY ./dataaccess/src/main/resources/db/* /docker-entrypoint-initdb.d/
COPY ./rmp/src/main/resources/db/* /docker-entrypoint-initdb.d/
RUN cd /docker-entrypoint-initdb.d/ && ls
