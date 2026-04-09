<div align="center">
  <img src="src/main/resources/static/images/insignia_fuzileiros.png" width="150px" />
  
  # SISDIS — Sistema de Gestão de Dispensas Médicas
  ### Corpo de Fuzileiros Navais — Marinha do Brasil
  
  ![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
  ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.5-green?style=for-the-badge&logo=springboot)
  ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=for-the-badge&logo=postgresql)
  ![Railway](https://img.shields.io/badge/Deploy-Railway-purple?style=for-the-badge)
</div>

---

## 📋 Sobre o Projeto

O **SISDIS** é um sistema web desenvolvido para solucionar um problema real do Corpo de Fuzileiros Navais: o controle manual e desorganizado de dispensas médicas de militares.

O sistema permite registrar, consultar e gerenciar o histórico de dispensas médicas de até 800 militares, com upload de PDF, dashboard com métricas e controle de acesso por perfil.

---

## ✨ Funcionalidades

- 🪖 **Cadastro de Militares** — NIP com máscara automática, Posto e Companhia
- 📄 **Registro de Dispensas** — com upload de PDF, data/hora automática e observações
- 🔍 **Busca por NIP** — histórico completo por militar
- 📊 **Dashboard** — métricas por período (hoje, semana, mês, ano), por companhia e ranking de militares
- 👥 **Controle de Perfis** — Moderador, Administrador, Consultor e Afilhado
- 🔐 **Autenticação segura** — login com BCrypt + 2FA via Google Authenticator
- 📧 **Recuperação de senha** — por email com token de 30 minutos
- 🌙 **Modo Dark** — alternância de tema salvo no navegador

---

## 🛠️ Stack Tecnológica

| Tecnologia | Uso |
|-----------|-----|
| Java 17 | Linguagem principal |
| Spring Boot 3.2.5 | Framework backend |
| Spring Security | Autenticação e autorização |
| Spring Data JPA | Persistência de dados |
| PostgreSQL | Banco de dados |
| Thymeleaf | Templates HTML |
| Bootstrap 5 | Estilização |
| Chart.js | Gráficos do dashboard |
| BCrypt | Criptografia de senhas |
| Google Authenticator (TOTP) | Autenticação 2FA |
| JavaMailSender | Envio de emails |
| Railway | Deploy em produção |

---

## 🔒 Perfis de Acesso

| Perfil | Permissões |
|--------|-----------|
| **Moderador** | Acesso total + gerenciar usuários (único no sistema) |
| **Administrador** | Criar, alterar e excluir registros |
| **Afilhado** | Criar e alterar (sem excluir) |
| **Consultor** | Somente visualização |

---

## 🚀 Como Executar Localmente

### Pré-requisitos
- Java 17
- PostgreSQL
- Maven

### Configuração

1. Clone o repositório:
```bash
git clone https://github.com/JordanAguiar/dispensas-militares.git
```

2. Crie o banco de dados:
```sql
CREATE DATABASE dispensas_militares;
```

3. Configure o `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/dispensas_militares
spring.datasource.username=postgres
spring.datasource.password=sua_senha

spring.mail.username=seu_email@gmail.com
spring.mail.password=sua_senha_de_app
```

4. Execute o projeto:
```bash
mvn spring-boot:run
```

5. Crie o usuário admin no banco:
```sql
INSERT INTO usuarios (username, password, perfil, totp_ativo, email)
VALUES ('admin', '$2a$10$HASH_AQUI', 'MODERADOR', false, 'seu@email.com');
```

6. Acesse: `http://localhost:8080`

---

## 📸 Screenshots

### Tela de Login
![Login](<img width="1915" height="908" alt="image" src="https://github.com/user-attachments/assets/7aabef82-cb9a-4476-a77e-6a763aba2dd3" />
)

### 2FA
![Dashboard](<img width="1915" height="905" alt="image" src="https://github.com/user-attachments/assets/4e1e8a8f-7d09-469f-bb20-a1309ddeb4f1" />

)

### Lista de Militares
![Militares](<img width="1917" height="907" alt="image" src="https://github.com/user-attachments/assets/5be76a42-3f99-4342-a25a-7e4659f486d3" />
)

### Dashboard
![Dashboard](<img width="1919" height="908" alt="image" src="https://github.com/user-attachments/assets/7a872ae3-7cba-4938-814b-79184b3ceee3" />
)

### Usuários
![Dashboard](<img width="1918" height="904" alt="image" src="https://github.com/user-attachments/assets/6fb8df6b-47d9-4d3e-9766-cb8bc256dc82" />

)

### Cadastro de Militar
![Dashboard](<img width="1919" height="909" alt="image" src="https://github.com/user-attachments/assets/a8ba061d-7b6f-4a50-924d-4f0b223808fd" />

)

---

## 👨‍💻 Autor

**Jordan da Silva Aguiar**
Militar do Corpo de Fuzileiros Navais — Marinha do Brasil

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Jordan_Aguiar-blue?style=for-the-badge&logo=linkedin)](https://linkedin.com/in/seu-perfil)
[![GitHub](https://img.shields.io/badge/GitHub-JordanAguiar-black?style=for-the-badge&logo=github)](https://github.com/JordanAguiar)

---

## 📄 Licença

Este projeto está sob a licença MIT.
