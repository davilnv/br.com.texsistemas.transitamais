#!/bin/bash
set -e

echo "==== Iniciando deploy ===="
cd /home/transitamais/transitamais-app

echo "==== Verificando branch ===="
git checkout main

echo "==== Atualizando repositório ===="
git pull origin main

echo "==== Último commit ===="
git log -1

echo "==== Subindo banco de dados ===="
docker-compose up -d db

echo "==== Aguardando container do banco ficar saudável ===="
until [ "$(docker inspect -f '{{.State.Health.Status}}' postgres_transitamais 2>/dev/null)" == "healthy" ]; do
  echo "$(date +'%T') - Aguardando o banco estar saudável..."
  sleep 2
done

echo "==== Banco saudável! Continuando... ===="

echo "==== Rodando build Maven ===="
mvn clean install

echo "==== Parando containers atuais ===="
docker-compose down

echo "==== Limpando imagens antigas (Docker system prune) ===="
docker system prune -f

echo "==== Subindo containers atualizados ===="
docker-compose up -d --build

echo "==== Deploy finalizado com sucesso! ===="
