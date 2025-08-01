#!/bin/bash

set -e

# Default para DEV
ROOT_URL="http://localhost:8081"

# Verifica se a flag -prod foi passada
if [[ "$1" == "-prod" ]]; then
  ROOT_URL="http://usuario-service:8081"
fi

# Carrega as variáveis do .env
if [ -f ../.env ]; then
  export $(grep -v '^#' ../.env | xargs)
else
  echo ".env não encontrado!"
  exit 1
fi

# Exporta a variável para o envsubst
export ROOT_URL

# Gera o JSON final com o rootUrl correto
envsubst < ../keycloak/template/transitamais-realm.template.json > ../keycloak/realms/transitamais-realm.json

echo "Arquivo keycloak/realms/transitamais-realm.json gerado com sucesso!"