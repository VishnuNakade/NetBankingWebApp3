# Use official Tomcat image
FROM tomcat:9.0

# Set working directory inside the container
WORKDIR /usr/local/tomcat/webapps/

# Copy the WAR file from the build directory to Tomcat webapps
COPY build/NetBankingWebApp3.war ./NetBankingWebApp3.war

# Expose Tomcat’s default port
EXPOSE 8080

# Start Tomcat server
CMD ["catalina.sh", "run"]
