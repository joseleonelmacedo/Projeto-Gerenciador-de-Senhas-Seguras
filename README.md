# Projeto-Gerenciador-de-Senhas-Seguras

# 🔐 Gerenciador de Senhas em Java

Este é um projeto simples de **Gerenciador de Senhas** escrito em Java. Ele permite:

- Autenticação via código 2FA (simulado);
- Geração de senhas seguras;
- Verificação se a senha foi exposta em vazamentos através da API do Have I Been Pwned;
- Criptografia da senha com AES;
- Geração de hash com BCrypt;
- Armazenamento seguro das senhas em um arquivo `.txt`.

---

## 📂 Estrutura do Projeto

GerenciadorDeSenhas/
├── src/
│ ├── GerenciadorDeSenhas/
│ │ └── GerenciadorDeSenhas.java
│ └── org/
│ └── mindrot/
│ └── jbcrypt/
│ └── BCrypt.java
├── module-info.java
├── Referenced Libraries/
│ └── jbcrypt-0.4.jar
└── senhas.txt

## ⚙️ Requisitos

- Java 17 ou superior (Java 21 recomendado)
- Biblioteca `jbcrypt-0.4.jar` (já incluída no projeto)

---

## ▶️ Como Executar

1. Clone o repositório ou baixe os arquivos.
2. Importe o projeto em sua IDE (recomendo IntelliJ ou Eclipse).
3. Adicione o JAR `jbcrypt-0.4.jar` como biblioteca referenciada.
4. Execute o arquivo `GerenciadorDeSenhas.java`.

---

## 🔑 Exemplo de Funcionamento

```text
Digite o código 2FA (123456 para teste): 123456
Digite o nome do serviço: facebook
Deseja gerar uma senha segura automaticamente? (s/n): s
Senha gerada: p^mVJMD@JaF0
Verificando se a senha foi exposta...
✅ Senha segura.
Hash gerado com BCrypt: $2a$10$76y0GiRq4ovx38rov...
Senha armazenada com sucesso para o serviço: facebook

🔍 Verificando Senha com Hash
Para verificar se uma senha bate com o hash armazenado:
if (BCrypt.checkpw(senhaInserida, hashArmazenado)) {
    System.out.println("✅ Senha válida!");
} else {
    System.out.println("❌ Senha incorreta.");
}
📁 Arquivo de Saída (senhas.txt)
servico;senhaCriptografadaAES;base64;hashBCrypt
Exemplo:
facebook;FgiHskl2s...==$; $2a$10$76y0GiRq4ovx38rov...
🛡️ Observações
O código 2FA usado aqui é apenas simulado para fins de teste.
A chave AES está fixa no código apenas como exemplo. Para uso real, use uma chave gerada dinamicamente e protegida.
Não é possível "descriptografar" o hash do BCrypt. Para comparar, use BCrypt.checkpw().
📚 Créditos
bcrypt — Biblioteca para hashing seguro de senhas.
Have I Been Pwned API — Utilizada para verificar se senhas já foram expostas.
📜 Licença
Este projeto é de uso educacional. Sinta-se livre para usar, estudar e modificar.
