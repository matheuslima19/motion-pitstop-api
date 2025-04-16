# 🛠️ Pitstop - API

O **Pitstop** é o sistema  desenvolvido para atender exclusivamente **donos e gerentes de oficinas mecânicas**, oferecendo uma solução robusta para a **gestão eficiente dos processos internos da oficina**. Este repositório concentra a lógica de negócio e acesso a dados da aplicação, estruturado como uma **API REST** baseada em **Clean Architecture**.

Organização https://github.com/auto-motion-io

---

## 🎯 Principais funcionalidades

- 📦 **Módulo de estoque**

  - Cadastro, edição e controle de peças

- 🔧 **Serviços**

  - Registro de tipos de serviços prestados pela oficina

- 🧾 **Ordem de Serviço**

  - Geração de O.S com associação de clientes e seus respectivos veículos

- 👨‍🔧 **Cadastro de mecânicos**

  - Registro de profissionais e alocação em ordens de serviço

- 💰 **Painel financeiro**

  - Controle de entradas e saídas financeiras da oficina

- 📤 **Upload de arquivos**

  - Integração com bucket do **Supabase**

- 🐛 **Logs de erro** e tratamento de exceções

- ✅ **Testes automatizados com JUnit**

---

## ⚙️ Tecnologias utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security (se houver autenticação/autorização)
- MySQL
- Supabase (armazenamento de arquivos)
- JUnit
- Clean Architecture
- NGINX
- Terraform (provisionamento da infraestrutura)
- AWS (EC2, RDS, etc.)
- CI/CD (esteiras de deploy automatizadas)

---

## 🚀 Como executar localmente

> Requisitos:
>
> - Java 17
> - Maven ou Gradle
> - MySQL

```bash
# Clone o repositório
git clone https://github.com/auto-motion-io/pitstop_api.git

# Configure o application.properties ou application.yml com os dados do banco

# Execute a aplicação
./mvnw spring-boot:run
# ou
./gradlew bootRun
```

A API será iniciada em: `http://localhost:8080`

---

## 🧪 Testes

Para executar os testes automatizados:

```bash
./mvnw test
# ou
./gradlew test
```

---

## 👥 Desenvolvedores

Projeto desenvolvido pelo grupo **Motion** – Estudantes de Análise e Desenvolvimento de Sistemas:

- Matheus Santos de Lima - @matheuslima19
- Thaisa Nobrega Costa - @nobregathsa
- David Silva - @Davidnmsilva
- Kauã Juhrs - @KauaJuhrs
- Leonardo Bento da Silva - @leopls07

 ---

## 🏷️ Licença

Projeto acadêmico. Uso comercial sujeito à permissão dos autores.

