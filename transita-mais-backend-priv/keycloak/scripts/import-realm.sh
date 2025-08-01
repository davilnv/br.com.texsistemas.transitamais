#!/bin/bash
set -e

REALM_FILE="/opt/keycloak/data/import/transitamais-realm.json"

echo "Iniciando a importação do realm..."
/opt/keycloak/bin/kc.sh import --file="$REALM_FILE" --override=true

echo "Importação do realm concluída. Reinicie o Container para aplicar as alterações."