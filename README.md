# EdutechApp – Plataforma Educacional com Monitoramento e Deploy na Nuvem

**EdutechApp** é uma aplicação monolítica construída com [JHipster](https://www.jhipster.tech/) e baseada no modelo JDL, com backend em Spring Boot e frontend em React. Esta aplicação é preparada para ambientes de produção com **deploy automatizado via Azure DevOps**, **hospedagem no Azure App Service** e **banco de dados MySQL flexível**. O sistema conta com **monitoramento via Azure Monitor/Application Insights** e **observabilidade local com Prometheus + Grafana via Docker Compose**.

---

## 🚀 Tecnologias Utilizadas

- Java 17 + Spring Boot (monolito)
- ReactJS + Bootstrap (JHipster frontend)
- MySQL (Azure Flexible Server)
- Azure App Service
- Azure DevOps (CI/CD)
- Prometheus & Grafana (monitoramento)
- Azure Monitor / Application Insights

---

## 📦 Estrutura do Projeto

- `edutech.jdl`: modelo de dados e entidades da aplicação.
- `azure-pipelines.yml`: pipeline de CI/CD configurada para o Azure DevOps.
- `docker-compose.yml`: sobe os serviços Prometheus e Grafana localmente para monitoramento da aplicação na Azure.
- `src/`: código fonte Java + React.
- `application.yml`: contém a configuração da aplicação, incluindo `/management/prometheus`.

---

## 🛠️ Configuração e Execução Local

### Pré-requisitos

- JDK 17
- Node.js + Yarn
- MySQL ou Docker
- Docker + Docker Compose
- JHipster CLI (`npm install -g generator-jhipster`)

### Passo a Passo

```bash
# 1. Clone o projeto
git clone https://github.com/gilbriatore/edutech.git
cd edutech

# 2. Gere o projeto com base no JDL (caso necessário)
jhipster import-jdl edutech.jdl

# 3. Rode o banco local (opcional)
# configure sua URL de conexão em application-dev.yml
# ou use um banco MySQL local rodando em 3306

# 4. Rode a aplicação
./mvnw

# 5. Acesse em http://localhost:8080
```

---

## 📊 Monitoramento Local com Prometheus + Grafana

```bash
# 1. Suba os serviços de monitoramento
docker-compose up -d

# 2. Acesse Prometheus em:
http://localhost:9090

# 3. Acesse Grafana em:
http://localhost:3000
# Usuário: admin | Senha: admin (padrão)

# 4. Configure o dashboard no Grafana
- Data source: Prometheus (http://prometheus:9090)
- Métrica exemplo: rate(http_server_requests_seconds_count[1m])
```

> A aplicação hospedada no Azure está expondo as métricas em `/management/prometheus`, já sendo consumidas pelo Prometheus local.

---

## ☁️ Deploy na Azure Cloud via Azure DevOps

### 1. Criação dos Recursos no Azure

- **Azure App Service** (Linux, Java 17, porta 8080)
- **MySQL Flexible Server** (configure usuário, senha e IP de acesso)
- **Application Insights** (para métricas e logs)
- **Crie um Azure Container Registry (ACR)** se for usar containers (opcional)

### 2. Conectando Azure DevOps à Azure

- Acesse Azure DevOps > Project Settings > Service connections
- Crie uma nova **Azure Resource Manager** connection (Service Principal + Subscription)

### 3. Publicando o Pipeline

- No seu repositório no GitHub: https://github.com/gilbriatore/edutech
- Vá ao Azure DevOps > Pipelines > New Pipeline
- Escolha **GitHub** como repositório
- Escolha a opção **YAML file**
- Caminho: `azure-pipelines.yml`
- Salve e execute

A pipeline irá:

- Fazer o build do projeto com Maven
- Rodar testes
- Publicar artefato
- Fazer o deploy no Azure App Service

---

## 📈 Azure Monitor & Application Insights

- Integrado no `application.yml` com a variável:
  ```yaml
  azure.application-insights.instrumentation-key: YOUR-INSTRUMENTATION-KEY
  ```
- Métricas como requisições, exceções, tempo de resposta e logs são capturados automaticamente.

---

## 📎 Exemplo de URL de monitoramento

- Azure App Service: `https://edutech-<id>.azurewebsites.net`
- Endpoint Prometheus exposto: `https://edutech-<id>.azurewebsites.net/management/prometheus`
- Prometheus local: `http://localhost:9090`
- Grafana local: `http://localhost:3000`

---

## 📄 Licença

Projeto com fins educacionais e acadêmicos. Sinta-se livre para usar e adaptar.

---

## ✍️ Autor

Gil Briatore – [linkedin.com/in/gilbriatore](https://www.linkedin.com/in/gilbriatore/)
