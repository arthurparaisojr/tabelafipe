
# comotestar.md


````markdown
# 🧪 Guia de Testes (Fluxo de Integração)

Este documento descreve como validar o funcionamento do sistema **Tabela FIPE**. 

Como o projeto foca na automação de dados (Orquestrador + Worker), não há necessidade de inserção manual de dados. O teste consiste em **disparar a carga** na API-1 e **monitorar o processamento** até a persistência no banco pela API-2.

---

## 📋 Pré-requisitos

Certifique-se de que o ambiente Docker esteja rodando:

```powershell
docker-compose up --build -d
````

-----

## 🚀 Passo 1: Disparar a Carga (O Gatilho)

Este comando ordena que a **API-1** consulte a API externa da FIPE, busque as marcas disponíveis e envie mensagens para a fila RabbitMQ.

### Opção A: Via Swagger UI (Recomendado)

1.  Acesse o painel: [http://localhost:8081/q/swagger-ui/](https://www.google.com/search?q=http://localhost:8081/q/swagger-ui/)
2.  Localize o controller **`Carga Controller`**.
3.  Abra o endpoint **`POST /api/v1/carga`**.
4.  Clique em **Try it out** e depois em **Execute**.

**Resultado Esperado:**

  * **Código HTTP:** `202 Accepted`
  * **Corpo da Resposta:** Mensagem indicando que o processamento foi iniciado em background.

### Opção B: Via Terminal (cURL)

Se preferir linha de comando:

```bash
curl -X POST http://localhost:8081/api/v1/carga
```

-----

## 🔍 Passo 2: Validar o Processamento (Monitoramento)

Como o processo é assíncrono, a validação ocorre observando os componentes de infraestrutura trabalhando.

### 1\. Verifique a Fila (RabbitMQ)

Veja as mensagens fluindo entre as APIs.

  * **Acesse:** [http://localhost:15672](https://www.google.com/search?q=http://localhost:15672)
  * **Login:** `usrtf`
  * **Senha:** `pwdtf`
  * **Ação:** Vá na aba **Queues** e observe a fila `fipe-queue`. Você verá os gráficos de "Publish" (entrada) e "Consumer" (saída) se movimentando.

### 2\. Verifique os Logs do Worker (API-2)

Veja o "chão de fábrica" processando os dados.

  * **Comando:**
    ```powershell
    docker-compose logs -f api-2
    ```
  * **O que procurar:** Mensagens como:
    > *"Recebido: Marca X"*
    > *"Modelos salvos no banco..."*

### 3\. Verifique o Banco de Dados (PostgreSQL)

Confira se os dados foram persistidos corretamente.

**Via API de Consulta (Se habilitado):**

  * Acesse: [http://localhost:8081/api/v1/veiculos](https://www.google.com/search?q=http://localhost:8081/api/v1/veiculos)
  * Você deve ver um JSON com a lista de veículos.

**Via SQL (DBeaver / PgAdmin):**

  * **Host:** `localhost` : `5432`
  * **Banco:** `fipe_db`
  * **User/Pass:** `usrdb` / `pwd123`
  * **Query:**
    ```sql
    SELECT * FROM VeiculoEntity;
    ```

<!-- end list -->

````
