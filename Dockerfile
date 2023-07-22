FROM openjdk:11

ADD target/social-*.jar /app/social.jar
CMD java -jar /app/social.jar

EXPOSE 80
