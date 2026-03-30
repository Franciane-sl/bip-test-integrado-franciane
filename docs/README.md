#  Desafio Fullstack Integrado

##  Descrição

Este projeto tem como objetivo implementar uma solução completa em camadas, contemplando:

* Banco de dados
* Serviço EJB (com correção de bug)
* Backend em Spring Boot
* Integração entre camadas
* Frontend

A proposta do desafio é avaliar conhecimentos em arquitetura, persistência, regras de negócio, boas práticas e tomada de decisão técnica.

---

## Arquitetura do Projeto

O sistema foi estruturado em múltiplos módulos Maven, seguindo separação de responsabilidades:

```
bip-test-integrado/
│
├── db/                  # Scripts de banco de dados
├── domain-module/       # Entidade
├── ejb-module/          # Serviço EJB
├── backend-module/      # API REST
├── frontend/            # Frontend
└── docs/                # Documentação adicional
```

---

## Tecnologias Utilizadas

* Java 21
* Spring Boot
* Spring Data JPA
* Jakarta EJB
* Hibernate
* PostgreSQL
* Maven
* Lombok

---

## Banco de Dados

Os scripts estão disponíveis em:

```
db/schema.sql
db/seed.sql
```

Responsáveis por:

* Criação da tabela `beneficio`
* Inserção de dados iniciais

---

## Camada de Domínio

O módulo `domain-module` contém a entidade principal:

* `Beneficio`

Características:

* Mapeamento JPA
* Controle de concorrência com `@Version` 

---

## Serviço EJB (ejb-module)

O módulo EJB representa a camada de negócio original.

### Bug identificado (original)

* Não validava saldo
* Não validava valores
* Não utilizava controle de concorrência
* Possibilidade de inconsistência de dados

---

### Correções implementadas

* Validação de IDs iguais
* Validação de valor positivo
* Verificação de saldo
* Uso de `LockModeType.OPTIMISTIC`
* Tratamento de entidades inexistentes

---

## Backend

O módulo `backend-module` implementa a API REST com:

### Funcionalidades

* CRUD completo de Benefícios
* Transferência de valores entre benefícios
* Validações com Bean Validation
* Uso de DTOs
* Separação em camadas:

  * Controller
  * Service
  * Repository

---

## Integração com EJB

### Importante

A integração direta com o EJB **não foi realizada em tempo de execução**.

### Motivo técnico

O EJB depende de um **container Java EE/Jakarta EE**, como:

* WildFly
* Payara
* GlassFish

Já o backend foi desenvolvido utilizando **Spring Boot**, que:

* NÃO fornece suporte nativo a EJB
* NÃO executa beans EJB
* NÃO permite injeção via `@EJB`

---

### Decisão adotada

Para manter o sistema funcional e coerente:

* O EJB foi **corrigido conforme solicitado**
* A lógica de negócio foi **reimplementada no backend**
* A arquitetura em camadas foi preservada

---

### Justificativa

Essa abordagem garante:

* Aplicação executável sem necessidade de container adicional
* Separação clara de responsabilidades
* Fidelidade à regra de negócio original
* Simplicidade de execução e testes

---

## Endpoints da API

Base URL:

```
/api/v1/beneficios
```

###  Listar todos

```
GET /
```

### Buscar por ID

```
GET /{id}
```

### Criar

```
POST /
```

Body:

```json
{
  "nome": "Vale Alimentação",
  "descricao": "Benefício mensal",
  "valor": 500.00,
  "ativo": true
}
```

---

### Atualizar

```
PUT /{id}
```

---

### Deletar

```
DELETE /{id}
```

---

### Transferência

```
POST /transfer?fromId=1&toId=2&amount=100
```

---

## Como Executar

### 1. Banco de dados

Executar scripts:

```
db/schema.sql
db/seed.sql
```

---

### 2. Build do projeto

Na raiz:

```
mvn clean install
```

---

### 3. Rodar backend

```
cd backend-module
mvn spring-boot:run
```

---

## Testes

Os testes da aplicação foram realizados utilizando o Postman, validando manualmente os principais fluxos da API.

### Cenários testados

- Criação de benefício
- Listagem de benefícios
- Busca por ID
- Atualização de benefício
- Remoção de benefício
- Transferência entre benefícios

### Validações verificadas

- Não permitir transferência para o mesmo benefício
- Não permitir valores negativos ou zero
- Verificação de saldo insuficiente
- Tratamento de entidades inexistentes

### Observação

Não foram implementados testes automatizados (JUnit), porém a aplicação foi validada manualmente garantindo o correto funcionamento das regras de negócio e endpoints.

Além disso, a documentação da API foi integrada com Swagger, permitindo visualizar e testar todos os endpoints de forma interativa:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Todos os endpoints testados no Swagger retornam **200 OK**.

---

## Critérios Atendidos

✔ Arquitetura em camadas
✔ Correção do EJB
✔ CRUD completo
✔ Transferência com validações
✔ Uso de DTOs
✔ Controle de concorrência
✔ Organização de projeto multi-módulo

---

## Considerações Finais

Este projeto foi desenvolvido com foco em:

* Clareza arquitetural
* Separação de responsabilidades
* Boas práticas de desenvolvimento
* Tomada de decisão técnica consciente

A limitação de integração com EJB foi tratada de forma adequada, respeitando o contexto tecnológico do projeto.

---

## Autora

Desenvolvido por **Franciane Schier Leite**
