import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Classe responsável por gerenciar a segurança do sistema,
 * incluindo criptografia, autenticação e proteção contra ataques.
 */
public class Seguranca {

    // Atributos
    private int id;
    private String protocolo; // HTTPS, TLS1_3
    private String tokenJWT;
    private List<String> sistemas; // DDoS_PROTECTION, INTRUSION_DETECTION, FIREWALL
    private int nivelSeguranca; // 1 a 5, onde 5 é o máximo
    private List<String> tentativasAcesso; // Registro de tentativas de acesso ao sistema
    private boolean modoProtecaoAtiva; // Modo de proteção ativa contra ataques

    // Construtores
    public Seguranca() {
        this.sistemas = new ArrayList<>();
        this.tentativasAcesso = new ArrayList<>();
        this.nivelSeguranca = 3; // Nível padrão
        this.modoProtecaoAtiva = true;
        this.protocolo = "TLS1_3";
        
        // Adicionar sistemas padrão de segurança
        this.sistemas.add("DDoS_PROTECTION");
        this.sistemas.add("INTRUSION_DETECTION");
        this.sistemas.add("FIREWALL");
    }

    // Métodos
    /**
     * Gera um hash para senha usando SHA-256
     * @param senha Senha a ser hashada
     * @param salt Salt para aumentar a segurança
     * @return Hash da senha
     */
    public String gerarHashSenha(String senha, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            
            // Adicionar salt à senha
            md.update(salt.getBytes());
            
            // Calcular hash
            byte[] bytes = md.digest(senha.getBytes());
            
            // Converter para string em base64
            return Base64.getEncoder().encodeToString(bytes);
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Gera um salt aleatório para uso no hash de senha
     * @return Salt gerado
     */
    public String gerarSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    
    /**
     * Gera um token JWT para autenticação
     * @param idUsuario ID do usuário
     * @param email Email do usuário
     * @return Token JWT
     */
    public String gerarToken(int idUsuario, String email) {
        // Em uma implementação real, seria usado uma biblioteca JWT
        // Aqui, apenas simulamos a geração de um token
        
        String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        String payload = "{\"id\":" + idUsuario + ",\"email\":\"" + email + "\"}";
        
        String headerBase64 = Base64.getEncoder().encodeToString(header.getBytes());
        String payloadBase64 = Base64.getEncoder().encodeToString(payload.getBytes());
        
        // Em uma implementação real, a assinatura seria criptografada
        String signature = "assinatura_simulada";
        
        this.tokenJWT = headerBase64 + "." + payloadBase64 + "." + signature;
        return this.tokenJWT;
    }
    
    /**
     * Valida um token JWT
     * @param token Token a ser validado
     * @return true se o token for válido
     */
    public boolean validarToken(String token) {
        // Em uma implementação real, verificaria a assinatura e a validade
        // Aqui, apenas verificamos se o token está no formato esperado
        
        String[] partes = token.split("\\.");
        return partes.length == 3;
    }
    
    /**
     * Registra uma tentativa de acesso ao sistema
     * @param ip IP da tentativa
     * @param sucesso true se a tentativa foi bem-sucedida
     */
    public void registrarTentativaAcesso(String ip, boolean sucesso) {
        String registro = LocalDateTime.now() + " | IP: " + ip + " | " + (sucesso ? "Sucesso" : "Falha");
        this.tentativasAcesso.add(registro);
        
        // Verificar padrões suspeitos (em uma implementação real)
        if (!sucesso && this.modoProtecaoAtiva) {
            int falhasRecentes = contarFalhasRecentes(ip);
            
            if (falhasRecentes >= 5) {
                // Bloquear IP ou tomar outra ação de segurança
                System.out.println("ALERTA DE SEGURANÇA: Múltiplas tentativas de acesso falhas do IP " + ip);
            }
        }
    }
    
    /**
     * Conta quantas falhas de acesso recentes ocorreram de um IP
     * @param ip IP a ser verificado
     * @return Número de falhas recentes
     */
    private int contarFalhasRecentes(String ip) {
        // Simula contar falhas recentes (últimas 24h)
        int contador = 0;
        
        for (String registro : this.tentativasAcesso) {
            if (registro.contains("IP: " + ip) && registro.contains("Falha")) {
                contador++;
            }
        }
        
        return contador;
    }
    
    /**
     * Configura o nível de segurança do sistema
     * @param nivel Nível de segurança (1-5)
     * @return true se o nível foi configurado com sucesso
     */
    public boolean configurarNivelSeguranca(int nivel) {
        if (nivel < 1 || nivel > 5) {
            return false;
        }
        
        this.nivelSeguranca = nivel;
        
        // Ajustar medidas de segurança conforme o nível
        if (nivel >= 4) {
            this.modoProtecaoAtiva = true;
            if (!this.sistemas.contains("2FA")) {
                this.sistemas.add("2FA");
            }
        }
        
        return true;
    }
    
    /**
     * Adiciona um sistema de segurança
     * @param sistema Sistema a ser adicionado
     * @return true se o sistema foi adicionado com sucesso
     */
    public boolean adicionarSistema(String sistema) {
        if (sistema == null || sistema.isEmpty() || this.sistemas.contains(sistema)) {
            return false;
        }
        
        return this.sistemas.add(sistema);
    }
    
    /**
     * Exibe um relatório de segurança
     */
    public void exibirRelatorioSeguranca() {
        System.out.println("=== Relatório de Segurança ===");
        System.out.println("Protocolo: " + this.protocolo);
        System.out.println("Nível de Segurança: " + this.nivelSeguranca + "/5");
        System.out.println("Modo de Proteção Ativa: " + (this.modoProtecaoAtiva ? "Ativado" : "Desativado"));
        
        System.out.println("\nSistemas de Segurança Ativos:");
        for (String sistema : this.sistemas) {
            System.out.println("- " + sistema);
        }
        
        System.out.println("\nÚltimas Tentativas de Acesso:");
        int limite = Math.min(5, this.tentativasAcesso.size());
        for (int i = this.tentativasAcesso.size() - 1; i >= this.tentativasAcesso.size() - limite; i--) {
            if (i >= 0) {
                System.out.println("- " + this.tentativasAcesso.get(i));
            }
        }
        
        System.out.println("=============================");
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getTokenJWT() {
        return tokenJWT;
    }

    protected void setTokenJWT(String tokenJWT) {
        this.tokenJWT = tokenJWT;
    }

    public List<String> getSistemas() {
        return sistemas;
    }

    public void setSistemas(List<String> sistemas) {
        this.sistemas = sistemas;
    }

    public int getNivelSeguranca() {
        return nivelSeguranca;
    }

    public void setNivelSeguranca(int nivelSeguranca) {
        this.nivelSeguranca = nivelSeguranca;
    }

    public List<String> getTentativasAcesso() {
        return tentativasAcesso;
    }

    public void setTentativasAcesso(List<String> tentativasAcesso) {
        this.tentativasAcesso = tentativasAcesso;
    }

    public boolean isModoProtecaoAtiva() {
        return modoProtecaoAtiva;
    }

    public void setModoProtecaoAtiva(boolean modoProtecaoAtiva) {
        this.modoProtecaoAtiva = modoProtecaoAtiva;
    }
}