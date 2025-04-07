import java.time.LocalDate; //Importa LocalDate
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um usuário do sistema de investimento em criptoativos.
 */
public class Usuario {

    // Atributos
    private int id;
    private String nome;
    private String email;
    private String senhaHash;
    // int idade;
    private LocalDate dataNascimento;
    // String cpf;
    private String telefone;
    // String pais; //País
    private List<Carteira> carteiras;
    private boolean autenticacaoDoisFatores; //Habilitação de 2FA pelo usuário
    private List<EstrategiaAutomatica> estrategias;
    private List<Alerta> alertas;

    // Construtores
    public Usuario() {
        this.carteiras = new ArrayList<>();
        this.estrategias = new ArrayList<>();
        this.alertas = new ArrayList<>();
    }

    public Usuario(int id, String nome, String email, String senhaHash, LocalDate dataNascimento, 
                  String telefone, boolean autenticacaoDoisFatores) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.autenticacaoDoisFatores = autenticacaoDoisFatores;
        this.carteiras = new ArrayList<>();
        this.estrategias = new ArrayList<>();
        this.alertas = new ArrayList<>();
    }

    // Métodos
    /**
     * Registra um novo usuário no sistema
     * @param nome Nome completo do usuário
     * @param email Email do usuário (será usado para login)
     * @param senha Senha não criptografada
     * @param dataNascimento Data de nascimento
     * @param telefone Telefone para contato
     * @return true se o registro foi bem-sucedido
     */
    public boolean registrar(String nome, String email, String senha, LocalDate dataNascimento, String telefone) {
        // Validação dos dados
        if (nome == null || email == null || senha == null || dataNascimento == null) {
            return false;
        }
        
        // Verificar se email já existe
        // Esta lógica seria implementada com acesso a banco de dados
        
        // Hash da senha - normalmente seria feito com uma biblioteca de criptografia
        String hashSenha = gerarHashSenha(senha);
        
        // Criar nova carteira padrão para o usuário
        Carteira carteiraHot = new Carteira();
        carteiraHot.setIdUsuario(this.id);
        carteiraHot.setTipo("Hot Wallet");
        carteiraHot.setSaldo(0.0);
        
        this.nome = nome;
        this.email = email;
        this.senhaHash = hashSenha;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.autenticacaoDoisFatores = false; // Por padrão, 2FA desativado
        this.carteiras.add(carteiraHot);
        
        return true;
    }
    
    /**
     * Simula a geração de um hash de senha
     * Em um ambiente real, seria usado bcrypt, PBKDF2 ou algoritmo similar
     */
    private String gerarHashSenha(String senha) {
        // Esta é uma simulação de hash, não deve ser usada em produção
        return "hash_" + senha;
    }

    /**
     * Realiza o login do usuário
     * @param email Email do usuário
     * @param senha Senha não criptografada
     * @return true se as credenciais estiverem corretas
     */
    public boolean login(String email, String senha) {
        // Verificar se o email corresponde ao do usuário
        if (!this.email.equals(email)) {
            return false;
        }
        
        // Comparar o hash da senha fornecida com o hash armazenado
        String senhaHash = gerarHashSenha(senha);
        if (!this.senhaHash.equals(senhaHash)) {
            return false;
        }
        
        // Verificar 2FA se estiver ativado
        if (this.autenticacaoDoisFatores) {
            // Lógica para verificar segundo fator
            // Em implementação real, enviaria código por email/SMS
        }
        
        return true;
    }
    
    /**
     * Configura a autenticação de dois fatores
     * @param ativar true para ativar, false para desativar
     * @return true se a operação foi bem-sucedida
     */
    public boolean configurarAutenticacaoDoisFatores(boolean ativar) {
        this.autenticacaoDoisFatores = ativar;
        return true;
    }
    
    /**
     * Adiciona uma nova carteira ao usuário
     * @param tipo Tipo da carteira (Hot ou Cold)
     * @return A carteira criada
     */
    public Carteira adicionarCarteira(String tipo) {
        Carteira novaCarteira = new Carteira();
        novaCarteira.setIdUsuario(this.id);
        novaCarteira.setTipo(tipo);
        novaCarteira.setSaldo(0.0);
        this.carteiras.add(novaCarteira);
        return novaCarteira;
    }
    
    /**
     * Adiciona um alerta para o usuário
     * @param alerta Objeto Alerta a ser adicionado
     */
    public void adicionarAlerta(Alerta alerta) {
        this.alertas.add(alerta);
    }
    
    /**
     * Configura uma estratégia automática para compra/venda
     * @param estrategia Estratégia a ser configurada
     * @return true se a estratégia foi adicionada com sucesso
     */
    public boolean configurarEstrategia(EstrategiaAutomatica estrategia) {
        if (estrategia != null) {
            estrategia.setIdUsuario(this.id);
            this.estrategias.add(estrategia);
            return true;
        }
        return false;
    }
    
    /**
     * Visualiza o dashboard do usuário com informações resumidas
     */
    public void visualizarDashboard() {
        System.out.println("=== Dashboard do Usuário ===");
        System.out.println("ID: " + this.id);
        System.out.println("Nome: " + this.nome);
        System.out.println("Email: " + this.email);
        System.out.println("Número de Carteiras: " + this.carteiras.size());
        
        double saldoTotal = 0.0;
        for (Carteira carteira : carteiras) {
            saldoTotal += carteira.getSaldo();
        }
        
        System.out.println("Saldo Total: " + saldoTotal);
        System.out.println("Alertas não lidos: " + contarAlertasNaoLidos());
        System.out.println("Estratégias configuradas: " + this.estrategias.size());
    }
    
    /**
     * Conta quantos alertas não foram lidos
     * @return Número de alertas não lidos
     */
    private int contarAlertasNaoLidos() {
        int naoLidos = 0;
        for (Alerta alerta : alertas) {
            if (!alerta.isLido()) {
                naoLidos++;
            }
        }
        return naoLidos;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    protected void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }
    
    public void alterarSenha(String senhaAtual, String novaSenha) {
        if (this.senhaHash.equals(gerarHashSenha(senhaAtual))) {
            this.senhaHash = gerarHashSenha(novaSenha);
        }
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Carteira> getCarteiras() {
        return carteiras;
    }

    public void setCarteiras(List<Carteira> carteiras) {
        this.carteiras = carteiras;
    }

    public boolean isAutenticacaoDoisFatores() {
        return autenticacaoDoisFatores;
    }

    public List<EstrategiaAutomatica> getEstrategias() {
        return estrategias;
    }

    public void setEstrategias(List<EstrategiaAutomatica> estrategias) {
        this.estrategias = estrategias;
    }

    public List<Alerta> getAlertas() {
        return alertas;
    }

    public void setAlertas(List<Alerta> alertas) {
        this.alertas = alertas;
    }
}