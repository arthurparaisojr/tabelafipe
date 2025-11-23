# API-1: FIPE Orchestrator & Query Service

Este microsserviço é responsável pela **carga inicial** dos dados, orquestração via mensageria e exposição dos dados
para consulta via REST.

## 🚀 Tecnologias

* **Java 21**
* **Quarkus** (Resteasy Reactive, SmallRye Messaging, Panache)
* **RabbitMQ** (Producer)
* **PostgreSQL** (Read-Only operations)

## ⚙️ Pré-requisitos

* JDK 21 instalado
* Docker e Docker Compose rodando (para as dependências de infra)

## 🛠️ Como rodar localmente (Dev Mode)

Para desenvolver sem precisar reconstruir o container Docker a toda hora:

1. Certifique-se de que o RabbitMQ e o Postgres estão rodando pelo Docker na raiz:
   ```bash
   docker-compose up postgres-db rabbitmq -d
   ```
2. Execute o modo dev do Quarkus:
   ```bash
   ./mvnw quarkus:dev
   ```
   *A aplicação subirá na porta 8081 (conforme configurado no properties).*

## 📦 Como compilar para Docker

Antes de subir o `docker-compose` da raiz, gere o pacote `.jar`:

```bash
./mvnw package -DskipTests