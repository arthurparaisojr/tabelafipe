# 🚗 Sistema de Integração Tabela FIPE (Microservices)

Este projeto implementa uma solução distribuída para consultar, processar e armazenar dados da Tabela FIPE. Utiliza arquitetura de microsserviços com **Java 21**, **Quarkus**, **RabbitMQ** e **PostgreSQL**, totalmente orquestrado via **Docker**.

---

## 🏗️ Arquitetura e Componentes

O sistema é composto por 4 containers que conversam entre si:

1.  **API-1 (Orquestrador & Gateway)**
    * **Função:** É a porta de entrada. Recebe requisições REST do usuário.
    * **Responsabilidade:** Busca a lista de *Marcas* na FIPE e envia para a fila RabbitMQ. Expõe dados para consulta.
    * **Porta:** `8081`

2.  **API-2 (Worker & Processador)**
    * **Função:** "Chão de fábrica". Processamento assíncrono.
    * **Responsabilidade:** Consome mensagens da fila, busca os *Modelos* detalhados na FIPE e salva no Banco de Dados.
    * **Porta:** `8082`

3.  **RabbitMQ (Mensageria)**
    * **Função:** Garante a comunicação desacoplada entre API-1 e API-2.
    * **Portas:** `5672` (AMQP) e `15672` (Painel Web).

4.  **PostgreSQL (Banco de Dados)**
    * **Função:** Persistência dos veículos processados.
    * **Porta:** `5432`

---

## 🔐 Credenciais de Acesso

Utilize estas credenciais configuradas no `docker-compose.yaml` para acessar os serviços:

| Serviço | Tipo de Acesso | Usuário | Senha | URL / Host |
| :--- | :--- | :--- | :--- | :--- |
| **PostgreSQL** | Banco de Dados | `usrdb` | `pwd123` | `localhost:5432` |
| **RabbitMQ** | Painel Web | `usrtf` | `pwdtf` | [http://localhost:15672](http://localhost:15672) |
| **API-1** | Swagger UI | - | - | [http://localhost:8081/q/swagger-ui/](http://localhost:8081/q/swagger-ui/) |

> **Nota:** Se o Swagger der erro 404, verifique se colocou a barra `/` no final da URL.

---

## 🚀 Como Executar o Projeto (Passo a Passo)

Como o projeto usa Java, precisamos gerar os executáveis (`.jar`) antes de criar os containers Docker.

### 1. Compilar as Aplicações
Abra seu terminal na raiz do projeto e execute:

```powershell
# 1. Compilar API-1
cd api-1
mvn clean package -DskipTests

# 2. Compilar API-2
cd ..\api-2
mvn clean package -DskipTests

# 3. Voltar para a raiz
cd ..


```docker

# 1. Derruba os containers e APAGA os dados do banco (-v)
docker-compose down -v

# 2. Sobe tudo de novo (vai recriar o banco e rodar o script novo)
docker-compose up --build -d


---


# 🧪 Como Testar

Para instruções detalhadas de como validar o fluxo de integração, disparar a carga e monitorar o RabbitMQ, consulte o nosso guia de testes:

👉 **[📄 Leia o Guia de Testes Completo](documents/ComoTestar.md)**