FROM sbtscala/scala-sbt:eclipse-temurin-jammy-22_36_1.10.0_3.4.2
WORKDIR /skullking
ADD . /skullking
RUN apt update && \
    apt install -y \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libgl1-mesa-glx libgtk-3-0 openjfx libgl1-mesa-dri libgl1-mesa-dev libcanberra-gtk-module libcanberra-gtk3-module default-jdk xvfb
ENV DISPLAY=:99
CMD Xvfb :99 -screen 0 1024x768x24 > /dev/null 2>&1 & \
    sbt -Djava.awt.headless=false -Dawt.useSystemAAFontSettings=lcd -Dsun.java2d.xrender=true