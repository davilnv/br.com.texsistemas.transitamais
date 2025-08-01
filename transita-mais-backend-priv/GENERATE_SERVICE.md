# Exemplo de geração de um microserviço com Spring Boot

Gerar o projeto no Spring Initializr

````bash
curl https://start.spring.io/starter.zip \
  -d dependencies=web,data-jpa,postgresql,flyway,security,lombok,oauth2-resource-server \
  -d language=java \
  -d type=maven-project \
  -d bootVersion=3.4.4 \
  -d javaVersion=17 \
  -d name=ponto-service \
  -d groupId=br.com.texsistemas.transitamais \
  -d artifactId=ponto-service \
  -d packageName=br.com.texsistemas.transitamais.pontoservice \
  -d description="Microserviço de registro de ponto para o sistema Transita+" \
  -o ponto-service.zip
````
