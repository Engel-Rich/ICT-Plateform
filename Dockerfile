# Étape 1 : Utiliser une image Java comme base
FROM openjdk:17-jdk-slim

# Étape 2 : Ajouter un argument pour la version du JAR (facultatif)
ARG JAR_FILE=target/itc-backend.jar

# Étape 3 : Copier le fichier JAR dans l'image Docker
COPY target/*.jar app.jar

# Étape 4 : Exposer le port sur lequel ton application s'exécute (par défaut 8080 pour Spring Boot)
EXPOSE 8080

# Étape 5 : Lancer l'application
ENTRYPOINT ["java", "-jar", "/app.jar"]
