FROM sbtscala/scala-sbt:eclipse-temurin-jammy-22_36_1.10.0_3.4.2

RUN apt update && \
    apt install -y \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libgl1-mesa-glx \
    libgtk-3-0 \
    libgl1-mesa-dri \
    libgl1-mesa-dev \
    libcanberra-gtk-module \
    libcanberra-gtk3-module \
    default-jdk

RUN apt update && \
    apt install -y \
    xvfb \
    x11vnc

# Set environment variables for X11 forwarding
ENV DISPLAY=host.docker.internal:0
ENV LIBGL_ALWAYS_INDIRECT=true

# Create a directory for X11 UNIX socket
RUN mkdir -p /tmp/.X11-unix && chmod 1777 /tmp/.X11-unix

WORKDIR /skullking
ADD . /skullking

RUN sbt assembly

CMD ["java", "-jar", "target/scala-3.4.0/SkullKing.jar"]
