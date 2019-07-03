# cdc-with-pact
Code for "Consumer Driven Contracts with Pact" talk

## How to spin up Pact Broker
Taken from: https://github.com/DiUS/pact_broker-docker/blob/master/POSTGRESQL.md
___
Start the docker machine:
```
docker-machine start default
eval $(docker-machine env default)
```
___
Create the Postgres container:
```bash
docker run --name pactbroker-db -e POSTGRES_PASSWORD=ThePostgresPassword -e POSTGRES_USER=admin -e PGDATA=/var/lib/postgresql/data/pgdata -v /var/lib/postgresql/data:/var/lib/postgresql/data -d postgres
```
Or start it:
```bash
docker start pactbroker-db
```
___
Access Postgres CLI:
```bash
docker run -it --link pactbroker-db:postgres --rm postgres sh -c 'exec psql -h "$POSTGRES_PORT_5432_TCP_ADDR" -p "$POSTGRES_PORT_5432_TCP_PORT" -U admin'
```
Create the DB user:
```sql
CREATE USER pactbrokeruser WITH PASSWORD 'TheUserPassword';
CREATE DATABASE pactbroker WITH OWNER pactbrokeruser;
GRANT ALL PRIVILEGES ON DATABASE pactbroker TO pactbrokeruser;
```
___
Create the Pact Broker container:
```bash
docker run --name pactbroker --link pactbroker-db:postgres -e PACT_BROKER_DATABASE_USERNAME=pactbrokeruser -e PACT_BROKER_DATABASE_PASSWORD=TheUserPassword -e PACT_BROKER_DATABASE_HOST=postgres -e PACT_BROKER_DATABASE_NAME=pactbroker -d  -p 9292:9292 pactfoundation/pact-broker
```
Or start it:
```
docker start pactbroker
```

## Contracts

### frontend - flights

#### When available:
Request: **GET /flights/{departure}/{arrival}/?date={date}**

Response: **200 status with body:**
```json
{
  "flights": [
    {
      "id": "47e2f0e6-a848-48c8-b1ab-e3dd80a80829",
      "departure": {
        "time": "2019-06-16T18:00:00.000Z",
        "airport": "MXP"
      },
      "arrival": {
        "time": "2019-06-16T20:00:00.000Z",
        "airport": "STN"
      },
      "price": {
        "amount": 50,
        "currency": "EUR"
      }
    }
  ]
}
```

#### When not available:
Request: **GET /flights/{departure}/{arrival}/?date={date}**

Response: **404 with empty body**
