# SIAAS API

APIs REST para gestão de perfis, consulta e auditoria de acessos.

---

## ✅ Pré-requisitos
- **Java 21**
- **Maven 3.9+**
- **PostgreSQL** (ou outro banco configurado via `DB_URL`)
- **Variáveis de ambiente**:
    - `DB_URL` → URL JDBC do banco (ex.: `jdbc:postgresql://host:5432/dbname`)
    - `DB_USER` → usuário do banco
    - `DB_PASSWORD` → senha do banco
    - `ACTUATOR_EXPOSE` → endpoints do Actuator (ex.: `health,info` ou `health,info,metrics,mappings`)
    - `ACTUATOR_HEALTH_DETAILS` → nível de detalhe do health (`always` ou `never`)
    - `SPRINGDOC_SWAGGER_UI_ENABLED` → habilitar Swagger UI (`true` ou `false`)
    - `SPRINGDOC_API_DOCS_ENABLED` → habilitar OpenAPI docs (`true` ou `false`)

---

## ▶️ Como subir localmente
1. Clone o repositório:


   ```bash
git clone https://devops.caixa/projetos/Caixa/_git/SIAAS-api
cd siaas-api
   ```
2. Configure as variáveis de ambiente (exemplo para DEV):


   ```bash
export DB_URL=jdbc:postgresql://localhost:5432/siaas
export DB_USER=usuario
export DB_PASSWORD=senha
export ACTUATOR_EXPOSE=health,info,metrics,mappings
export ACTUATOR_HEALTH_DETAILS=always
export SPRINGDOC_SWAGGER_UI_ENABLED=true
export SPRINGDOC_API_DOCS_ENABLED=true
   ```
3. Compile e rode:


   ```bash
mvn clean install
mvn spring-boot:run
   ```
4. Acesse:
   - API: `http://localhost:8080/siaas-api`
   - Swagger UI: `http://localhost:8080/siaas-api/swagger-ui.html`
   - Actuator Health: `http://localhost:8080/siaas-api/actuator/health`

---

## 🛠 Tecnologias
- Spring Boot 3.5.7
- Spring Data JPA
- Springdoc OpenAPI (Swagger)
- PostgreSQL
- Actuator para monitoramento

---

5. Profile de teste
- Configurar a VM para usar o profile local-h2
- O arquivo LocalH2DataSeeder inicializa alguns dados para teste
- http://localhost:8080/siaas-api/h2-console
