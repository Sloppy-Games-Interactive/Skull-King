#FROM sbtscala/scala-sbt:eclipse-temurin-jammy-22_36_1.10.0_3.4.2
FROM hseeberger/scala-sbt:17.0.2_1.6.2_3.1.1
WORKDIR /skullking
ADD . /skullking
RUN apt update && apt install -y libxrender1 libxtst6 libxi6 unzip
#Download and extract JavaFX SDK for Linux
#RUN wget https://download2.gluonhq.com/openjfx/17.0.11/openjfx-17.0.11_osx-aarch64_bin-sdk.zip -O javafx-sdk.zip && \
#    unzip javafx-sdk.zip && \
#    rm javafx-sdk.zip
#Set PATH_TO_FX environment variable
#ENV PATH_TO_FX=/skullking/javafx-sdk-17.0.1/lib
CMD sbt run
