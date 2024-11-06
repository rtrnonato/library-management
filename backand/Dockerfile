# 1. Use uma imagem oficial do Java como base
FROM openjdk:17-jdk-slim

# 2. Define o diretório de trabalho dentro do container
WORKDIR /app

# 3. Copia o JAR gerado para o container
COPY target/*.jar app.jar

# 4. Expõe a porta em que a aplicação Spring Boot vai rodar (padrão é 8080)
EXPOSE 8080

# 5. Define o comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
