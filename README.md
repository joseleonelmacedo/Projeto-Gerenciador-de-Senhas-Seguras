# 🔐 Projeto: Gerenciador de Senhas Seguras em Java

Este é um projeto simples de **Gerenciador de Senhas** desenvolvido em Java, com foco em segurança e boas práticas. Ele permite:

- Autenticação via código 2FA (simulado);
- Geração de senhas fortes automaticamente;
- Verificação de senhas vazadas via API do **Have I Been Pwned**;
- Criptografia AES para armazenamento seguro;
- Geração de hash com **BCrypt**;
- Armazenamento das senhas em um arquivo `.txt`.

---

## 📁 Estrutura do Projeto

GerenciadorDeSenhas/
├── src/
│   ├── GerenciadorDeSenhas/
│   │   └── GerenciadorDeSenhas.java
│   └── org/
│       └── mindrot/
│           └── jbcrypt/
│               └── BCrypt.java
├── module-info.java
├── Referenced Libraries/
│   └── jbcrypt-0.4.jar
└── senhas.txt

---

## ⚙️ Requisitos

- Java 17 ou superior (**Java 21 recomendado**)
- Biblioteca `jbcrypt-0.4.jar` (já incluída no projeto)

---

## ▶️ Como Executar

1. Clone o repositório ou baixe os arquivos.
2. Importe o projeto em sua IDE preferida (recomendado: **IntelliJ** ou **Eclipse**).
3. Adicione o `jbcrypt-0.4.jar` como biblioteca referenciada no projeto.
4. Execute o arquivo `GerenciadorDeSenhas.java`.

---

## 🧪 Exemplo de Funcionamento

Digite o código 2FA (123456 para teste): 123456  
Digite o nome do serviço: facebook  
Deseja gerar uma senha segura automaticamente? (s/n): s  
Senha gerada: p^mVJMD@JaF0  
Verificando se a senha foi exposta...  
✅ Senha segura.  
Hash gerado com BCrypt: $2a$10$76y0GiRq4ovx38rov...  
Senha armazenada com sucesso para o serviço: facebook

---

## 🔍 Verificação de Senha com Hash

Para verificar se uma senha corresponde ao hash armazenado:

```java
if (BCrypt.checkpw(senhaInserida, hashArmazenado)) {
    System.out.println("✅ Senha válida!");
} else {
    System.out.println("❌ Senha incorreta.");
}
```

---

## 📄 Arquivo de Saída (`senhas.txt`)

Formato de armazenamento:

servico;senhaCriptografadaAES;base64;hashBCrypt

Exemplo:  
facebook;FgiHskl2s...==;$2a$10$76y0GiRq4ovx38rov...

---

## 🛡️ Observações Importantes

- O código 2FA é **simulado** apenas para fins de teste.
- A chave AES está **fixa no código** apenas como exemplo. Em produção, use uma chave **gerada dinamicamente** e protegida.
- O hash BCrypt **não é reversível**. Para verificar senhas, utilize `BCrypt.checkpw()`.

---

## 📚 Créditos

- **BCrypt** — Biblioteca para hashing seguro de senhas.
- **Have I Been Pwned API** — Utilizada para verificar se senhas foram expostas em vazamentos.

---

## 📜 Licença

Este projeto é de uso **educacional**. Sinta-se livre para usar, estudar e modificar conforme desejar.
