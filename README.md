# cdc-with-pact
Code for "Consumer Driven Contracts with Pact" talk

## How to spin up Pact Broker
```
docker-machine start default
eval $(docker-machine env default)
docker run -it --link pactbroker-db:postgres --rm postgres sh -c 'exec psql -h "$POSTGRES_PORT_5432_TCP_ADDR" -p "$POSTGRES_PORT_5432_TCP_PORT" -U admin'
docker run --name pactbroker --link pactbroker-db:postgres -e PACT_BROKER_DATABASE_USERNAME=pactbrokeruser -e PACT_BROKER_DATABASE_PASSWORD=TheUserPassword -e PACT_BROKER_DATABASE_HOST=postgres -e PACT_BROKER_DATABASE_NAME=pactbroker -d  -p 9292:9292 pactfoundation/pact-broker
```
