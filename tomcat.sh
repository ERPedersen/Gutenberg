#!/bin/bash

# Create a Tomcat group
sudo groupadd tomcat

# Create a new Tomcat user and make a member of of group
sudo useradd -s /bin/false -g tomcat -d /opt/tomcat tomcat

# Use the wget command to download the Tomcat source:
wget http://apache.mirrors.ionfish.org/tomcat/tomcat-8/v8.5.5/bin/apache-tomcat-8.5.5.tar.gz

# Extract the Tomcat source to your home directory
sudo tar -xzvf apache-tomcat-8.5.5.tar.gz

# Move the extracted content, apache-tomcat-8.5.5, to /opt
sudo mv apache-tomcat-8.5.5 /opt/tomcat

# Next you will need to give proper permission to the tomcat user to access to the Tomcat installation
sudo chgrp -R tomcat /opt/tomcat
sudo chown -R tomcat /opt/tomcat
sudo chmod -R 755 /opt/tomcat

# Create the systemd service file, tomcat.service, inside the /etc/systemd/system/ directory

echo "
[Unit]
Description=Apache Tomcat Web Server
After=network.target

[Service]
Type=forking

Environment=JAVA_HOME=/usr/lib/jvm/java-8-oracle/jre
Environment=CATALINA_PID=/opt/tomcat/temp/tomcat.pid
Environment=CATALINA_HOME=/opt/tomcat
Environment=CATALINA_BASE=/opt/tomcat
Environment='CATALINA_OPTS=-Xms512M -Xmx1024M -server -XX:+UseParallelGC'
Environment='JAVA_OPTS=-Djava.awt.headless=true -Djava.security.egd=file:/dev/./urandom'

ExecStart=/opt/tomcat/bin/startup.sh
ExecStop=/opt/tomcat/bin/shutdown.sh

User=tomcat
Group=tomcat
UMask=0007
RestartSec=15
Restart=always

[Install]
WantedBy=multi-user.target
" > /etc/systemd/system/tomcat.service

# Reload the systemd daemon with the following command
sudo systemctl daemon-reload

