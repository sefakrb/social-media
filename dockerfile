FROM openjdk:17
ADD target/socialmedia-0.0.1-SNAPSHOT.jar social-media-app.jar
ENTRYPOINT [ "java", "-jar", "social-media-app.jar" ]