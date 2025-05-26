package GerenciadorDeSenhas;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.mindrot.jbcrypt.BCrypt;

public class GerenciadorDeSenhas {

    private static final String CHAVE_AES = "1234567890123456"; // Chave de 16 caracteres
    private static final String ARQUIVO_SENHAS = "senhas.txt";

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("🔐 Bem-vindo ao Gerenciador de Senhas 🔐");

        // Autenticação 2FA
        if (!autenticar2FA(scanner)) {
            System.out.println("🚫 Autenticação de dois fatores falhou.");
            scanner.close();
            return;
        }

        // Entrada do serviço
        System.out.print("Digite o nome do serviço: ");
        String servico = scanner.nextLine();

        // Loop até que a senha seja segura (não esteja vazada)
        String senha;
        while (true) {
            System.out.print("Deseja gerar uma senha segura automaticamente? (s/n): ");
            String opcao = scanner.nextLine();

            if (opcao.equalsIgnoreCase("s")) {
                senha = gerarSenhaSegura(12);
                System.out.println("Senha gerada: " + senha);
            } else {
                System.out.print("Digite a senha desejada: ");
                senha = scanner.nextLine();
            }

            // Verificação de vazamento
            System.out.println("🔍 Verificando se a senha foi exposta...");
            if (verificarVazamento(senha)) {
                System.out.println("⚠️ Esta senha já apareceu em vazamentos! Escolha outra.\n");
            } else {
                System.out.println("✅ Senha segura.");
                break;
            }
        }

        // Criptografia AES
        String senhaCriptografada = criptografarAES(senha, CHAVE_AES);

        // Gerar hash com BCrypt
        String hashBCrypt = BCrypt.hashpw(senha, BCrypt.gensalt());

        // Salvar no arquivo
        salvarSenha(servico, senhaCriptografada, hashBCrypt);

        System.out.println("🗄️ Senha armazenada com sucesso para o serviço: " + servico);
        scanner.close();
    }

    // 1. Geração de senha segura
    public static String gerarSenhaSegura(int tamanho) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        SecureRandom random = new SecureRandom();
        StringBuilder senha = new StringBuilder();
        for (int i = 0; i < tamanho; i++) {
            int index = random.nextInt(caracteres.length());
            senha.append(caracteres.charAt(index));
        }
        return senha.toString();
    }

    // 2. Autenticação 2FA simples
    public static boolean autenticar2FA(Scanner scanner) {
        System.out.print("Digite o código 2FA (123456 para teste): ");
        String codigo = scanner.nextLine();
        return codigo.equals("123456");
    }

    // 3. Verificação de senha vazada (API HaveIBeenPwned)
    public static boolean verificarVazamento(String senha) throws Exception {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        byte[] hashBytes = sha1.digest(senha.getBytes("UTF-8"));

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02X", b));
        }
        String hashSenha = sb.toString();

        String prefixo = hashSenha.substring(0, 5);
        String sufixo = hashSenha.substring(5);

        String apiUrl = "https://api.pwnedpasswords.com/range/" + prefixo;
        HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Java Password Checker");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String linha;
        while ((linha = in.readLine()) != null) {
            String[] partes = linha.split(":");
            if (partes[0].equalsIgnoreCase(sufixo)) {
                in.close();
                return true;
            }
        }
        in.close();
        return false;
    }

    // 4. Criptografia AES
    public static String criptografarAES(String texto, String chave) throws Exception {
        SecretKeySpec key = new SecretKeySpec(chave.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(texto.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    // 5. Salvar senha em arquivo (com BufferedWriter)
    public static void salvarSenha(String servico, String senhaCriptografada, String hashBCrypt) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_SENHAS, true))) {
            writer.write(servico + ";" + senhaCriptografada + ";" + hashBCrypt);
            writer.newLine();
        }
    }
}
