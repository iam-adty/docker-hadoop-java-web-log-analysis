FROM apache/hadoop:3

RUN sudo yum -y update

RUN sudo yum -y install java-1.8.0-openjdk-devel.x86_64

# USER root