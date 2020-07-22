FROM adoptopenjdk/openjdk12:x86_64-ubuntu-jre-12.0.2_10
LABEL maintainer="jllado@gmail.com"
RUN apt update
RUN apt install -y xfonts-75dpi wget wkhtmltopdf
RUN wget https://github.com/wkhtmltopdf/packaging/releases/download/0.12.6-1/wkhtmltox_0.12.6-1.bionic_amd64.deb
RUN dpkg -i wkhtmltox_0.12.6-1.bionic_amd64.deb
EXPOSE 8080
ADD build/libs/vibotthumbnail-0.0.1-SNAPSHOT.jar vibotthumbnail.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/vibotthumbnail.jar"]
