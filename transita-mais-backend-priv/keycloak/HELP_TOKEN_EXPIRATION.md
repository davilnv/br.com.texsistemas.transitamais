# Realiza Login
/opt/keycloak/bin/kcadm.sh config credentials --server http://localhost:8080 --realm master --user admin --password admin

# Lista os clientes
/opt/keycloak/bin/kcadm.sh get clients -r transitamais | grep -B 1 '"clientId" : "usuario-service"'

# Atualizar client
/opt/keycloak/bin/kcadm.sh update clients/[cliente-id-interno] -r transitamais -s 'attributes."access.token.lifespan"=1800' -s 'attributes."refresh.token.lifespan"=2592000'
