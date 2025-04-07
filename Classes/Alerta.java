import java.time.LocalDateTime;

/**
 * Classe que representa um alerta para o usuário sobre eventos importantes,
 * como variações bruscas de preço, execução de estratégias automáticas, etc.
 */
public class Alerta {

    // Atributos
    private int id;
    private int idUsuario; // Usuário que receberá o alerta
    private int idCriptoativo; // Criptoativo sobre o qual será o alerta (opcional)
    private String tipo; // Tipo de alerta: VOLATILIDADE, ESTRATEGIA, SEGURANCA, INFORMATIVO
    private String titulo;
    private String conteudo;
    private LocalDateTime dataHoraAlerta;
    private boolean lido; // Indica se o usuário leu a notificação
    private String prioridade; // ALTA, MEDIA, BAIXA
    private boolean enviadoEmail; // Se o alerta foi enviado por e-mail

    // Construtores
    public Alerta() {
        this.dataHoraAlerta = LocalDateTime.now();
        this.lido = false;
        this.enviadoEmail = false;
        this.prioridade = "MEDIA";
    }
    
    public Alerta(int idUsuario, String tipo, String titulo, String conteudo) {
        this.idUsuario = idUsuario;
        this.tipo = tipo;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.dataHoraAlerta = LocalDateTime.now();
        this.lido = false;
        this.enviadoEmail = false;
        
        // Define prioridade com base no tipo
        if (tipo.equals("SEGURANCA") || tipo.equals("VOLATILIDADE")) {
            this.prioridade = "ALTA";
        } else if (tipo.equals("ESTRATEGIA")) {
            this.prioridade = "MEDIA";
        } else {
            this.prioridade = "BAIXA";
        }
    }

    // Métodos
    /**
     * Marca o alerta como lido
     * @return true se a operação foi bem-sucedida
     */
    public boolean marcarComoLido() {
        if (!this.lido) {
            this.lido = true;
            return true;
        }
        return false;
    }
    
    /**
     * Envia o alerta por e-mail (simulação)
     * @param emailDestino Email para envio do alerta
     * @return true se o e-mail foi enviado com sucesso
     */
    public boolean enviarPorEmail(String emailDestino) {
        if (emailDestino == null || emailDestino.isEmpty()) {
            return false;
        }
        
        // Simulação de envio de e-mail
        System.out.println("Enviando alerta por e-mail para: " + emailDestino);
        System.out.println("Assunto: [" + this.prioridade + "] " + this.titulo);
        System.out.println("Conteúdo: " + this.conteudo);
        
        this.enviadoEmail = true;
        return true;
    }
    
    /**
     * Cria um alerta de volatilidade
     * @param idUsuario ID do usuário
     * @param idCriptoativo ID do criptoativo
     * @param variacao Variação percentual do preço
     * @return Alerta de volatilidade criado
     */
    public static Alerta criarAlertaVolatilidade(int idUsuario, int idCriptoativo, double variacao) {
        Alerta alerta = new Alerta();
        alerta.setIdUsuario(idUsuario);
        alerta.setIdCriptoativo(idCriptoativo);
        alerta.setTipo("VOLATILIDADE");
        alerta.setPrioridade("ALTA");
        
        String direcao = variacao > 0 ? "aumento" : "queda";
        String titulo = "Variação brusca de preço detectada!";
        String conteudo = "O criptoativo ID:" + idCriptoativo + " sofreu " + direcao + 
                          " de " + Math.abs(variacao) + "% nas últimas 24 horas.";
        
        alerta.setTitulo(titulo);
        alerta.setConteudo(conteudo);
        
        return alerta;
    }
    
    /**
     * Cria um alerta de execução de estratégia
     * @param idUsuario ID do usuário
     * @param idCriptoativo ID do criptoativo
     * @param acao Ação executada (compra/venda)
     * @param quantidade Quantidade transacionada
     * @return Alerta de estratégia criado
     */
    public static Alerta criarAlertaEstrategia(int idUsuario, int idCriptoativo, String acao, double quantidade) {
        Alerta alerta = new Alerta();
        alerta.setIdUsuario(idUsuario);
        alerta.setIdCriptoativo(idCriptoativo);
        alerta.setTipo("ESTRATEGIA");
        alerta.setPrioridade("MEDIA");
        
        String titulo = "Estratégia automática executada";
        String conteudo = "A estratégia automática executou uma operação de " + acao + 
                         " de " + quantidade + " unidades do criptoativo ID:" + idCriptoativo + ".";
        
        alerta.setTitulo(titulo);
        alerta.setConteudo(conteudo);
        
        return alerta;
    }
    
    /**
     * Cria um alerta de segurança
     * @param idUsuario ID do usuário
     * @param mensagem Mensagem de segurança
     * @return Alerta de segurança criado
     */
    public static Alerta criarAlertaSeguranca(int idUsuario, String mensagem) {
        Alerta alerta = new Alerta();
        alerta.setIdUsuario(idUsuario);
        alerta.setTipo("SEGURANCA");
        alerta.setPrioridade("ALTA");
        
        String titulo = "Alerta de Segurança";
        
        alerta.setTitulo(titulo);
        alerta.setConteudo(mensagem);
        
        return alerta;
    }
    
    /**
     * Exibe os detalhes do alerta
     */
    public void exibirDetalhes() {
        System.out.println("=== Alerta ===");
        System.out.println("ID: " + this.id);
        System.out.println("Tipo: " + this.tipo);
        System.out.println("Prioridade: " + this.prioridade);
        System.out.println("Título: " + this.titulo);
        System.out.println("Conteúdo: " + this.conteudo);
        System.out.println("Data/Hora: " + this.dataHoraAlerta);
        System.out.println("Lido: " + (this.lido ? "Sim" : "Não"));
        System.out.println("Enviado por e-mail: " + (this.enviadoEmail ? "Sim" : "Não"));
        System.out.println("==============");
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCriptoativo() {
        return idCriptoativo;
    }

    public void setIdCriptoativo(int idCriptoativo) {
        this.idCriptoativo = idCriptoativo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataHoraAlerta() {
        return dataHoraAlerta;
    }

    public void setDataHoraAlerta(LocalDateTime dataHoraAlerta) {
        this.dataHoraAlerta = dataHoraAlerta;
    }

    public boolean isLido() {
        return lido;
    }

    public void setLido(boolean lido) {
        this.lido = lido;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public boolean isEnviadoEmail() {
        return enviadoEmail;
    }

    public void setEnviadoEmail(boolean enviadoEmail) {
        this.enviadoEmail = enviadoEmail;
    }
}