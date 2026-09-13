# FinAssist AI

### Enterprise Financial Intelligence & AI Risk Assistant

FinAssist AI is a **Spring Boot microservices-based financial intelligence platform** that combines **Generative AI, RAG, tool calling, event-driven architecture, vector search, and enterprise backend technologies** to assist with financial risk analysis and document intelligence.

The platform allows users to interact with financial information using natural language while combining:

* **LLM-powered conversational AI**
* **Retrieval-Augmented Generation (RAG)**
* **PGVector semantic search**
* **AI tool/function calling**
* **AI-assisted financial risk analysis**
* **Kafka event-driven communication**
* **Redis caching**
* **PostgreSQL persistence**
* **Spring Security/JWT**
* **Docker containerization**
* **Ollama local LLM inference**

The project is designed to demonstrate how a modern **Java Backend + Spring Boot + Microservices + AI** system can be structured for enterprise financial use cases.

---

# 1. Project Overview

Traditional financial applications generally expose information through fixed APIs and dashboards.

FinAssist AI introduces an AI-powered interaction layer on top of the backend services.

Instead of manually checking multiple systems, a user can ask questions such as:

> "Analyze customer 1 and explain why their risk level is high."

The AI Assistant can:

1. Retrieve the customer's profile.
2. Retrieve the customer's transactions.
3. Retrieve the customer's existing risk assessment.
4. Search relevant financial/risk policies from the vector database.
5. Provide the collected context to the LLM.
6. Generate a structured risk analysis.
7. Publish the AI analysis event through Kafka.
8. Allow the Audit Service to consume and record the AI-generated analysis.

This demonstrates an enterprise-style architecture where **AI works together with traditional backend services rather than replacing them**.

---

# 2. Key Features

## AI Features

* LLM-powered financial assistant
* Prompt templates
* Local LLM inference using Ollama
* Embedding generation
* Vector similarity search
* Retrieval-Augmented Generation (RAG)
* AI tool/function calling
* Structured LLM output
* AI-assisted risk analysis
* Policy-aware financial analysis
* AI audit event generation

## Backend Features

* Spring Boot microservices
* REST APIs
* PostgreSQL
* JPA/Hibernate
* Kafka event streaming
* Redis caching
* API Gateway
* Service-to-service communication
* Exception handling
* Input validation
* Docker containerization

## Enterprise Architecture Features

* Database-per-service approach
* Event-driven architecture
* Asynchronous communication through Kafka
* AI and traditional backend service separation
* Document ingestion pipeline
* Vector-based knowledge retrieval
* Auditability of AI-generated decisions
* Containerized infrastructure

---

# 3. Technology Stack

| Category         | Technology                  |
| ---------------- | --------------------------- |
| Language         | Java 17                     |
| Backend          | Spring Boot 4.1.1           |
| AI Framework     | Spring AI 2.0.1             |
| LLM              | Ollama                      |
| Chat Model       | Qwen3                       |
| Embedding Model  | nomic-embed-text            |
| Architecture     | Microservices               |
| API              | REST                        |
| API Gateway      | Spring Cloud Gateway        |
| Database         | PostgreSQL                  |
| Vector Database  | PGVector                    |
| Messaging        | Apache Kafka 4.0.0          |
| Cache            | Redis                       |
| ORM              | Spring Data JPA / Hibernate |
| Security         | Spring Security / JWT       |
| Containerization | Docker                      |
| Orchestration    | Docker Compose              |
| Build Tool       | Maven                       |
| Testing          | JUnit 5                     |
| Version Control  | Git / GitHub                |

---

# 4. System Architecture

```text
                         ┌──────────────────────┐
                         │       Client         │
                         │   Postman / Frontend │
                         └──────────┬───────────┘
                                    │
                                    ▼
                         ┌──────────────────────┐
                         │     API Gateway      │
                         │       :8080          │
                         └──────────┬───────────┘
                                    │
              ┌─────────────────────┼─────────────────────┐
              │                     │                     │
              ▼                     ▼                     ▼
   ┌──────────────────┐  ┌──────────────────┐  ┌──────────────────┐
   │ AI Assistant     │  │ Document Service │  │ Transaction      │
   │ :8081            │  │ :8083            │  │ Service :8082    │
   └────────┬─────────┘  └────────┬─────────┘  └────────┬─────────┘
            │                     │                     │
            │                     ▼                     │
            │              ┌──────────────┐             │
            │              │  PGVector    │             │
            │              │ Vector Store │             │
            │              └──────────────┘             │
            │                                           │
            ▼                                           ▼
   ┌──────────────────┐                       ┌──────────────────┐
   │   Risk Service   │                       │     Kafka        │
   │      :8084       │                       │     :9092        │
   └────────┬─────────┘                       └────────┬─────────┘
            │                                          │
            │                                          ▼
            │                                ┌──────────────────┐
            │                                │  Audit Service   │
            │                                │      :8085       │
            │                                └──────────────────┘
            │
            ▼
   ┌─────────────────────────────────────────────────────────────┐
   │                        PostgreSQL                            │
   │                                                             │
   │  finassist_ai                                               │
   │  finassist_transaction                                      │
   │  finassist_risk                                             │
   │  finassist_audit                                            │
   │  finassist_document                                         │
   └─────────────────────────────────────────────────────────────┘

                         ┌──────────────────┐
                         │      Redis       │
                         │      :6379       │
                         └──────────────────┘

                         ┌──────────────────┐
                         │     Ollama       │
                         │     :11434       │
                         │                  │
                         │     Qwen3        │
                         │ nomic-embed-text │
                         └──────────────────┘
```

---

# 5. Microservices

## 5.1 API Gateway

**Port:** `8080`

Responsibilities:

* Single entry point for clients
* Routes requests to appropriate microservices
* Hides internal service topology
* Provides centralized gateway layer

Example:

```text
Client
  │
  ▼
API Gateway :8080
  │
  ├── /api/ai/**           → AI Assistant
  ├── /api/document/**     → Document Service
  ├── /api/transactions/** → Transaction Service
  ├── /api/risk/**         → Risk Service
  └── /api/audit/**        → Audit Service
```

---

# 6. AI Assistant Service

**Port:** `8081`

This is the central AI orchestration service.

Responsibilities:

* LLM interaction
* Prompt management
* RAG
* Vector similarity search
* Tool calling
* Financial risk analysis
* AI response generation
* Kafka event publishing

### Main AI capabilities

```text
User Question
     │
     ▼
AI Assistant
     │
     ├── LLM
     │
     ├── RAG / PGVector
     │
     └── Tool Calling
             │
             ├── Customer Service
             ├── Transaction Service
             └── Risk Service
```

---

# 7. Document Service

**Port:** `8083`

Responsibilities:

* Upload financial documents
* Store document metadata
* Extract PDF content
* Split documents into chunks
* Generate embeddings
* Store embeddings in PGVector

Document processing flow:

```text
PDF
 │
 ▼
Document Service
 │
 ├── Extract Text
 │
 ├── Split into Chunks
 │
 ├── Generate Embeddings
 │
 └── Store in PGVector
```

Example upload endpoint:

```http
POST /api/document/upload
```

Multipart parameter:

```text
file
```

---

# 8. Transaction Service

**Port:** `8082`

Responsibilities:

* Store customer transactions
* Retrieve customer transactions
* Provide transaction information to AI tools
* Publish transaction-related events through Kafka

Example endpoint:

```http
GET /api/transactions/customer/{customerId}
```

---

# 9. Risk Service

**Port:** `8084`

Responsibilities:

* Store customer risk assessments
* Retrieve current risk information
* Process transaction-driven risk events
* Provide risk information to AI Assistant
* Participate in Kafka event processing

Example endpoint:

```http
GET /api/risk/customer/{customerId}
```

---

# 10. Audit Service

**Port:** `8085`

Responsibilities:

* Consume AI risk-analysis events
* Maintain audit records
* Track AI-generated risk analysis
* Provide an auditable record of AI activity

Example Kafka event:

```text
finassist.ai.risk.analysis
```

---

# 11. AI Architecture

FinAssist AI combines multiple AI techniques rather than relying only on a chatbot.

```text
                     ┌──────────────────┐
                     │      User        │
                     └────────┬─────────┘
                              │
                              ▼
                    ┌────────────────────┐
                    │   AI Assistant     │
                    └─────────┬──────────┘
                              │
                ┌─────────────┼─────────────┐
                │             │             │
                ▼             ▼             ▼
             LLM          RAG Search    Tool Calling
                │             │             │
                │             ▼             │
                │          PGVector         │
                │                           │
                │                  ┌────────┼────────┐
                │                  │        │        │
                │                  ▼        ▼        ▼
                │              Customer Transaction Risk
                │               Service    Service  Service
                │
                └───────────────┬───────────────────
                                ▼
                         Structured Response
```

---

# 12. LLM Integration

The project uses **Ollama** for local LLM inference.

This allows the project to run without requiring a paid hosted LLM API.

### Chat Model

```text
qwen3
```

### Embedding Model

```text
nomic-embed-text
```

The embedding model converts document chunks and user queries into numerical vectors that can be compared semantically.

---

# 13. Prompt Templates

The AI Assistant uses dedicated system prompts to define the behavior of the financial assistant.

The prompt provides instructions such as:

* Analyze financial information carefully.
* Use available tools when customer information is required.
* Use retrieved policy context when performing policy-related analysis.
* Avoid inventing customer or transaction information.
* Return structured results when requested.

This separates **AI behavior/prompt design** from normal business logic.

---

# 14. Retrieval-Augmented Generation (RAG)

FinAssist AI implements RAG so that the LLM can answer questions using project-specific financial documents.

## RAG Pipeline

```text
Financial PDF
      │
      ▼
Document Extraction
      │
      ▼
Text Chunking
      │
      ▼
Embedding Generation
      │
      ▼
PGVector
      │
      │
      │       User Question
      │             │
      │             ▼
      │       Query Embedding
      │             │
      └─────────────┤
                    ▼
             Similarity Search
                    │
                    ▼
             Relevant Chunks
                    │
                    ▼
              Prompt Context
                    │
                    ▼
                   LLM
                    │
                    ▼
               AI Response
```

### Vector Store

PGVector is used for semantic document retrieval.

Example search configuration:

```text
Top K: 5
Similarity Threshold: 0.60
```

The system retrieves the most relevant document chunks before sending the final prompt to the LLM.

---

# 15. AI Tool Calling

Tool calling allows the LLM to request real backend information instead of relying only on its internal knowledge.

Available tools include:

### Customer Tool

```text
getCustomerProfile(customerId)
```

### Transaction Tool

```text
getCustomerTransactions(customerId)
```

### Risk Tool

```text
getCustomerRiskAssessment(customerId)
```

Architecture:

```text
User
 │
 ▼
LLM
 │
 ├── getCustomerProfile()
 │
 ├── getCustomerTransactions()
 │
 └── getCustomerRiskAssessment()
          │
          ▼
    Backend Microservices
```

This demonstrates how an LLM can operate as an intelligent orchestration layer over traditional enterprise APIs.

---

# 16. AI Risk Analysis

The main AI business capability is financial risk analysis.

Endpoint:

```http
POST /api/ai/risk-analysis
```

Request:

```json
{
  "customerId": 1
}
```

The AI Assistant combines:

```text
Customer Profile
       +
Customer Transactions
       +
Current Risk Assessment
       +
Financial Policy Documents
       +
LLM Reasoning
       ↓
AI Risk Analysis
```

Example response:

```json
{
  "customerId": 1,
  "riskLevel": "HIGH",
  "riskScore": 85,
  "summary": "Customer demonstrates transaction patterns requiring additional monitoring.",
  "keyFindings": [
    "High-value transactions detected",
    "Unusual transaction frequency"
  ],
  "transactionPatterns": [
    "Repeated high-value transactions"
  ],
  "policyFindings": [
    "Transactions require enhanced monitoring"
  ],
  "recommendedActions": [
    "Perform enhanced customer due diligence",
    "Review recent transactions"
  ]
}
```

---

# 17. Kafka Event-Driven Architecture

Apache Kafka is used for asynchronous communication between services.

## Kafka Topics

```text
finassist.transaction.created
finassist.risk.assessment.created
finassist.ai.audit
finassist.ai.risk.analysis
```

## Transaction → Risk Flow

```text
Transaction Service
       │
       │ transaction event
       ▼
     Kafka
       │
       ▼
 Risk Service
       │
       │ risk assessment event
       ▼
     Kafka
       │
       ▼
 Audit Service
```

## AI Risk Analysis → Audit Flow

```text
AI Assistant
     │
     │ AI Risk Analysis Event
     ▼
Kafka
     │
     ▼
Audit Service
     │
     ▼
Audit Record
```

This provides asynchronous and loosely coupled communication between services.

---

# 18. Database Architecture

FinAssist AI follows a **database-per-service approach**.

Each microservice maintains its own logical database.

```text
PostgreSQL
│
├── finassist_ai
├── finassist_transaction
├── finassist_risk
├── finassist_audit
└── finassist_document
```

This reduces direct database coupling between microservices.

### PGVector

The AI database also hosts the vector store used by the RAG pipeline.

```text
finassist_ai
      │
      ├── Application Data
      │
      └── PGVector
             │
             └── Document Embeddings
```

---

# 19. Redis

Redis is used as a caching layer for frequently accessed information.

Typical architecture:

```text
Client
  │
  ▼
Service
  │
  ├── Cache Hit ─────► Redis
  │
  └── Cache Miss
          │
          ▼
       PostgreSQL
```

Caching reduces unnecessary database calls and improves response performance for frequently requested data.

---

# 20. Security

The architecture includes:

* Spring Security
* JWT-based authentication
* API Gateway security layer
* Protected backend endpoints

The intended request flow is:

```text
Client
   │
   ▼
JWT
   │
   ▼
API Gateway
   │
   ▼
Authenticated Microservice
```

Security can be extended with role-based authorization such as:

```text
ADMIN
ANALYST
AUDITOR
USER
```

---

# 21. REST API Overview

## AI Assistant

### Normal Chat

```http
POST /api/ai/chat
```

Example:

```json
{
  "message": "What is financial risk?"
}
```

### RAG Chat

```http
POST /api/ai/rag
```

Example:

```json
{
  "message": "What does the financial risk policy say about suspicious transactions?"
}
```

### Tool Calling

```http
POST /api/ai/tool
```

Example:

```json
{
  "message": "Show me the transaction information for customer 1."
}
```

### AI Risk Analysis

```http
POST /api/ai/risk-analysis
```

Example:

```json
{
  "customerId": 1
}
```

---

# 22. Document APIs

### Upload Document

```http
POST /api/document/upload
```

Content type:

```text
multipart/form-data
```

Field:

```text
file
```

### List Documents

```http
GET /api/document/documents
```

### Get Document

```http
GET /api/document/{documentId}
```

---

# 23. Transaction APIs

Example:

```http
GET /api/transactions/customer/{customerId}
```

Used by the AI tool-calling layer to retrieve customer transaction information.

---

# 24. Risk APIs

Example:

```http
GET /api/risk/customer/{customerId}
```

Used by the AI tool-calling layer to retrieve the customer's current risk assessment.

---

# 25. Project Structure

The repository follows a microservices structure similar to:

```text
finassist-ai/
│
├── api-gateway/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
│
├── ai-assistant-service/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/finassist/
│   │       │       └── ai_assistant_service/
│   │       │           ├── config/
│   │       │           ├── controller/
│   │       │           ├── dto/
│   │       │           ├── event/
│   │       │           ├── service/
│   │       │           └── tool/
│   │       └── resources/
│   │           └── application.properties
│   ├── pom.xml
│   └── Dockerfile
│
├── transaction-service/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
│
├── risk-service/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
│
├── document-service/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
│
├── audit-service/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
│
├── postgres/
│   └── init.sql
│
├── docker-compose.yml
│
└── README.md
```

---

# 26. Important Configuration

## Local Development

When running services directly from IntelliJ/Maven:

```properties
spring.kafka.bootstrap-servers=localhost:9092
```

Ollama:

```properties
spring.ai.ollama.base-url=http://localhost:11434
```

PostgreSQL:

```text
localhost:5432
```

---

# 27. Docker Environment

When running inside Docker Compose, services communicate using Docker service names.

For example:

```text
PostgreSQL → postgres:5432
Kafka      → kafka:9092
Ollama     → ollama:11434
Redis      → redis:6379
```

Microservice-to-microservice communication should also use Docker service names rather than `localhost`.

Example:

```text
AI Assistant
      │
      ▼
transaction-service:8082
```

instead of:

```text
localhost:8082
```

---

# 28. Prerequisites

Install the following before running the project:

* Java 17+
* Maven
* Docker Desktop
* Git
* Ollama (optional when using the Docker Ollama container)

Verify:

```bash
java -version
```

```bash
mvn -version
```

```bash
docker --version
```

---

# 29. Running the Project

## Step 1 — Clone Repository

```bash
git clone <YOUR-GITHUB-REPOSITORY-URL>
```

```bash
cd finassist-ai
```

---

## Step 2 — Build Microservices

Build each service:

```bash
mvn clean package -DskipTests
```

If using separate service directories:

```bash
cd api-gateway
mvn clean package -DskipTests
```

Repeat for the microservices.

---

# 30. Start Infrastructure

Start PostgreSQL, Kafka, Redis and Ollama using Docker Compose:

```bash
docker compose up -d postgres kafka redis ollama
```

Check containers:

```bash
docker compose ps
```

---

# 31. Download Ollama Models

If using the Ollama Docker container:

```bash
docker exec -it finassist-ollama ollama pull qwen3
```

Embedding model:

```bash
docker exec -it finassist-ollama ollama pull nomic-embed-text
```

Verify:

```bash
docker exec -it finassist-ollama ollama list
```

Expected models:

```text
qwen3
nomic-embed-text
```

---

# 32. Start All Services

Build and start the complete application:

```bash
docker compose up -d --build
```

Check:

```bash
docker compose ps
```

Expected services:

```text
finassist-api-gateway
finassist-ai-assistant
finassist-transaction
finassist-risk
finassist-document
finassist-audit
finassist-postgres
finassist-kafka
finassist-ollama
finassist-redis
```

---

# 33. Check Logs

AI Assistant:

```bash
docker logs -f finassist-ai-assistant
```

Transaction Service:

```bash
docker logs -f finassist-transaction
```

Risk Service:

```bash
docker logs -f finassist-risk
```

Document Service:

```bash
docker logs -f finassist-document
```

Audit Service:

```bash
docker logs -f finassist-audit
```

Kafka:

```bash
docker logs -f finassist-kafka
```

---

# 34. Verify PostgreSQL Databases

```bash
docker exec -it finassist-postgres psql -U postgres -l
```

The expected application databases are:

```text
finassist_ai
finassist_audit
finassist_risk
finassist_transaction
finassist_document
```

The exact Document Service database name should match the database configured in its `application.properties`.

---

# 35. Verify PGVector

Connect to the AI database:

```bash
docker exec -it finassist-postgres psql -U postgres -d finassist_ai
```

Enable the extension if required:

```sql
CREATE EXTENSION IF NOT EXISTS vector;
```

---

# 36. Testing the Application

## Test 1 — Normal AI Chat

```http
POST http://localhost:8081/api/ai/chat
```

Request:

```json
{
  "message": "Explain financial risk assessment."
}
```

---

## Test 2 — RAG

First upload a financial policy PDF:

```http
POST http://localhost:8083/api/document/upload
```

Use:

```text
form-data
key: file
type: File
```

Then ask:

```http
POST http://localhost:8081/api/ai/rag
```

Example:

```json
{
  "message": "What does the financial policy say about suspicious transactions?"
}
```

---

# 37. Test Tool Calling

Endpoint:

```http
POST http://localhost:8081/api/ai/tool
```

Example:

```json
{
  "message": "Get the profile and transactions for customer 1."
}
```

The LLM can invoke the registered tools and retrieve information from the appropriate backend services.

---

# 38. Test AI Risk Analysis

Endpoint:

```http
POST http://localhost:8081/api/ai/risk-analysis
```

Request:

```json
{
  "customerId": 1
}
```

Expected processing:

```text
Request
   │
   ▼
AI Assistant
   │
   ├── Customer Tool
   │
   ├── Transaction Tool
   │
   ├── Risk Tool
   │
   ├── PGVector Policy Search
   │
   └── LLM
         │
         ▼
  RiskAnalysisResponse
         │
         ▼
       Kafka
         │
         ▼
   Audit Service
```

---

# 39. Docker Commands

Start:

```bash
docker compose up -d
```

Start with rebuild:

```bash
docker compose up -d --build
```

Stop:

```bash
docker compose down
```

View containers:

```bash
docker compose ps
```

View logs:

```bash
docker compose logs -f
```

Restart a service:

```bash
docker compose restart ai-assistant-service
```

Remove containers:

```bash
docker compose down
```

> Avoid `docker compose down -v` unless you intentionally want to remove persistent volumes and database data.

---

# 40. Error Handling

The services use backend-level error handling for common application failures.

Examples include:

* Invalid request data
* Missing resources
* Invalid customer IDs
* Database failures
* Service communication failures
* AI processing failures

The objective is to prevent raw internal exceptions from being exposed directly to API consumers.

---

# 41. Scalability Considerations

The architecture is designed so individual services can be scaled independently.

For example:

```text
AI Assistant × 3
Transaction × 2
Risk Service × 2
Document Service × 2
Audit Service × 2
```

Kafka allows asynchronous workloads to be distributed across consumer instances.

Redis reduces repeated database access.

PGVector allows semantic retrieval without requiring the LLM to contain all enterprise knowledge in its model parameters.

---

# 42. Why This Architecture?

The project intentionally combines **traditional backend engineering with modern AI engineering**.

Traditional backend:

```text
Spring Boot
REST
JPA
PostgreSQL
Kafka
Redis
Docker
Security
```

AI engineering:

```text
LLM
Prompt Engineering
Embeddings
Vector Database
RAG
Tool Calling
Structured Output
AI Risk Analysis
```

Together:

```text
Enterprise Backend
        +
Generative AI
        +
Event-Driven Architecture
        +
Microservices
```

---

# 43. Key Engineering Concepts Demonstrated

This project demonstrates practical experience with:

* Microservice decomposition
* REST API design
* Service-to-service communication
* Database-per-service architecture
* Event-driven architecture
* Kafka producers and consumers
* Asynchronous processing
* Caching
* Vector databases
* Semantic search
* RAG pipelines
* LLM integration
* Prompt engineering
* AI tool calling
* Structured AI responses
* AI orchestration
* Document processing
* API Gateway
* JWT/Spring Security
* Docker containerization
* Environment-based configuration
* Backend exception handling
* Input validation

---

# 44. Future Enhancements

Potential production enhancements include:

* OAuth2/OpenID Connect
* Keycloak integration
* Role-based authorization
* Kafka Schema Registry
* Dead Letter Topics
* Retry mechanisms
* Circuit breakers with Resilience4j
* Distributed tracing
* Prometheus/Grafana monitoring
* Centralized logging
* OpenTelemetry
* Kubernetes deployment
* CI/CD pipeline
* AWS deployment
* Advanced fraud detection models
* Human approval workflow for high-risk customers
* AI response evaluation
* Prompt/version management
* Model observability
* Explainable AI dashboards

---

# 45. Limitations

This project is a portfolio/learning implementation and is **not intended to make real-world financial, regulatory, lending, AML, or investment decisions**.

AI-generated risk analysis should be treated as an assistive output requiring appropriate human review in real financial systems.

The sample data and policies used by the application are demonstration data.

---

# 46. Project Learning Outcomes

By building FinAssist AI, the project demonstrates the integration of:

```text
Java 17
   ↓
Spring Boot
   ↓
Microservices
   ↓
REST APIs
   ↓
PostgreSQL
   ↓
Kafka
   ↓
Redis
   ↓
Spring AI
   ↓
LLM
   ↓
Embeddings
   ↓
PGVector
   ↓
RAG
   ↓
Tool Calling
   ↓
AI Risk Analysis
   ↓
Docker
```

The major objective is to demonstrate how **AI capabilities can be integrated into a real backend architecture rather than building only a standalone chatbot.**

---

# 47. Author

**Mizan Sheikh**

Java Backend Developer | Spring Boot | Microservices | Kafka | Spring AI

---

# 48. License

This project is intended for educational, portfolio and demonstration purposes.

Add an appropriate license to the repository if the project is intended to be distributed publicly.
