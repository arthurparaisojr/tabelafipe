# Sistema Integrado Tabela FIPE (Microservices)

Este projeto implementa um sistema distribuído para consulta e processamento da Tabela FIPE, utilizando arquitetura de microsserviços, mensageria assíncrona e bancos de dados relacionais, tudo containerizado com Docker.

## 📋 Estrutura do Projeto

O sistema é composto por 4 containers principais:

1.  **api-1**: Responsável pela carga inicial (busca marcas na FIPE) e exposição dos dados via REST.
2.  **api-2**: Worker que consome mensagens da fila, busca detalhes (modelos) na FIPE e salva no banco.
3.  **rabbitmq**: Broker de mensageria para comunicação assíncrona entre API-1 e API-2.
4.  **postgres-db**: Banco de dados relacional para persistência dos veículos.

## 🔐 Credenciais e Acessos

Conforme solicitado, abaixo estão os usuários e senhas configurados no ambiente Docker.

| Serviço | Tipo | Usuário | Senha | Porta Externa |
| :--- | :--- | :--- | :--- | :--- |
| **PostgreSQL** | Banco de Dados | `usrdb` | `pwd123` | `5432` |
| **RabbitMQ** | Fila / Admin UI | `usrtf` | `pwdtf` | `5672` (App) / `15672` (Web) |
| **API-1** | Swagger / REST | - | - | `8081` |
| **API-2** | Swagger / REST | - | - | `8082` |

> **Nota:** As APIs Java conectam-se automaticamente usando estas credenciais através das variáveis de ambiente definidas no `docker-compose.yaml`.

## 🚀 Como gerar e rodar o YAML

O arquivo `docker-compose.yaml` é a "receita" que diz ao Docker como criar tudo. Você não precisa compilar este arquivo, apenas criá-lo.

### Passo 1: Criar o arquivo
Crie um arquivo chamado `docker-compose.yaml` na raiz do projeto e cole o conteúdo fornecido na documentação do projeto (seção anterior).

### Passo 2: Gerar os executáveis Java
Antes de subir os containers, você precisa gerar os arquivos `.jar` das aplicações Quarkus. Na raiz de cada pasta (`api-1` e `api-2`), execute:

```bash
# Na pasta api-1/
./mvnw package -DskipTests

# Na pasta api-2/
./mvnw package -DskipTests