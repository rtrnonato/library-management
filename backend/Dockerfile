# Usar uma imagem base com Maven e JDK
FROM maven:3.9.5-eclipse-temurin-17 AS builder

# Define o diretório de trabalho
WORKDIR /app

# Copiar arquivos do projeto para o container
COPY . .

# Construir o JAR
RUN mvn clean package -DskipTests

# Usar uma imagem mais leve para rodar o JAR
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Copiar o JAR do build anterior
COPY --from=builder /app/target/library_management-0.0.1-SNAPSHOT.jar app.jar

# Configurar o comando de inicialização
CMD ["java", "-jar", "app.jar"]

# Expor a porta utilizada pelo Spring Boot
EXPOSE 8080
