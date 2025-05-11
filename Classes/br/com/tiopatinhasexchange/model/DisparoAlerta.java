package br.com.tiopatinhasexchange.model;
import java.time.LocalDateTime;

/**
 * Classe associativa que representa o disparo de um alerta para um usuário.
 * É uma entidade associativa que conecta Alertas e Usuários, controlando o envio e
 * recebimento de notificações de alertas.
 */
public class DisparoAlerta {
    
    // Atributos - chaves e dados básicos
    private int id;                      // Chave primária
    private int idAlerta;                // Chave estrangeira - referência ao Alerta
    private int idUsuario;               // Chave estrangeira - referência ao Usuário destinatário
    private LocalDateTime dataHoraEnvio; // Quando o alerta foi disparado
    
    // Status e rastreamento
    private boolean visualizado;         // Se o usuário visualizou o alerta
    private LocalDateTime dataHoraVisualizacao; // Quando o usuário visualizou
    private String canalEnvio;           // EMAIL, SMS, PUSH, APP
    private int tentativasEnvio;         // Número de tentativas de envio
    private boolean envioBemSucedido;    // Status do envio
    private String mensagem;             // Mensagem específica para este disparo
    private String dadosContexto;        // Dados adicionais/contextuais para o alerta
    
    // Construtores
    public DisparoAlerta() {
        this.dataHoraEnvio = LocalDateTime.now();
        this.visualizado = false;
        this.tentativasEnvio = 0;
        this.envioBemSucedido = false;
    }
    
    public DisparoAlerta(int idAlerta, int idUsuario, String canalEnvio) {
        this.idAlerta = idAlerta;
        this.idUsuario = idUsuario;
        this.canalEnvio = canalEnvio;
        this.dataHoraEnvio = LocalDateTime.now();
        this.visualizado = false;
        this.tentativasEnvio = 1;
        this.envioBemSucedido = false;
    }
    
    public DisparoAlerta(int idAlerta, int idUsuario, String canalEnvio, String mensagem) {
        this.idAlerta = idAlerta;
        this.idUsuario = idUsuario;
        this.canalEnvio = canalEnvio;
        this.mensagem = mensagem;
        this.dataHoraEnvio = LocalDateTime.now();
        this.visualizado = false;
        this.tentativasEnvio = 1;
        this.envioBemSucedido = false;
    }
    
    // Métodos
    /**
     * Marca o alerta como visualizado pelo usuário
     */
    public void marcarComoVisualizado() {
        this.visualizado = true;
        this.dataHoraVisualizacao = LocalDateTime.now();
    }
    
    /**
     * Registra uma tentativa de envio do alerta
     * @param sucesso Se a tentativa foi bem-sucedida
     * @return Número atualizado de tentativas
     */
    public int registrarTentativaEnvio(boolean sucesso) {
        this.tentativasEnvio++;
        this.envioBemSucedido = sucesso;
        return this.tentativasEnvio;
    }
    
    /**
     * Sobrecarga do método de registrar tentativa (polimorfismo estático)
     * @param sucesso Se a tentativa foi bem-sucedida
     * @param novoCanalEnvio Novo canal usado para a tentativa
     * @return Número atualizado de tentativas
     */
    public int registrarTentativaEnvio(boolean sucesso, String novoCanalEnvio) {
        this.tentativasEnvio++;
        this.envioBemSucedido = sucesso;
        this.canalEnvio = novoCanalEnvio;
        return this.tentativasEnvio;
    }
    
    /**
     * Adiciona dados de contexto ao alerta
     * @param chave Nome do dado contextual
     * @param valor Valor do dado contextual
     */
    public void adicionarContexto(String chave, String valor) {
        if (this.dadosContexto == null) {
            this.dadosContexto = "";
        }
        
        if (!this.dadosContexto.isEmpty()) {
            this.dadosContexto += ";";
        }
        
        this.dadosContexto += chave + "=" + valor;
    }
    
    /**
     * Verifica se o alerta tem alta prioridade com base no tempo desde o envio
     * @return true se o alerta tiver alta prioridade para visualização
     */
    public boolean verificarPrioridadeAlta() {
        LocalDateTime agora = LocalDateTime.now();
        // Se não visualizado e mais de 1 hora desde o envio, é alta prioridade
        return !this.visualizado && 
               this.envioBemSucedido && 
               this.dataHoraEnvio.plusHours(1).isBefore(agora);
    }
    
    /**
     * Exibe os detalhes do disparo do alerta
     */
    public void exibirDetalhes() {
        System.out.println("=== Disparo de Alerta ID: " + this.id + " ===");
        System.out.println("Alerta ID: " + this.idAlerta);
        System.out.println("Usuário ID: " + this.idUsuario);
        System.out.println("Data/Hora Envio: " + this.dataHoraEnvio);
        if (this.mensagem != null && !this.mensagem.isEmpty()) {
            System.out.println("Mensagem: " + this.mensagem);
        }
        System.out.println("Visualizado: " + (this.visualizado ? "Sim" : "Não"));
        if (this.visualizado) {
            System.out.println("Data/Hora Visualização: " + this.dataHoraVisualizacao);
        }
        System.out.println("Canal Envio: " + this.canalEnvio);
        System.out.println("Tentativas: " + this.tentativasEnvio);
        System.out.println("Status: " + (this.envioBemSucedido ? "Enviado com sucesso" : "Falha no envio"));
        
        if (this.dadosContexto != null && !this.dadosContexto.isEmpty()) {
            System.out.println("Dados de Contexto: " + this.dadosContexto);
        }
        
        System.out.println("==============================");
    }
    
    // Getters e Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getIdAlerta() {
        return idAlerta;
    }
    
    public void setIdAlerta(int idAlerta) {
        this.idAlerta = idAlerta;
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public LocalDateTime getDataHoraEnvio() {
        return dataHoraEnvio;
    }
    
    public void setDataHoraEnvio(LocalDateTime dataHoraEnvio) {
        this.dataHoraEnvio = dataHoraEnvio;
    }
    
    public boolean isVisualizado() {
        return visualizado;
    }
    
    public void setVisualizado(boolean visualizado) {
        this.visualizado = visualizado;
    }
    
    public LocalDateTime getDataHoraVisualizacao() {
        return dataHoraVisualizacao;
    }
    
    public void setDataHoraVisualizacao(LocalDateTime dataHoraVisualizacao) {
        this.dataHoraVisualizacao = dataHoraVisualizacao;
    }
    
    public String getCanalEnvio() {
        return canalEnvio;
    }
    
    public void setCanalEnvio(String canalEnvio) {
        this.canalEnvio = canalEnvio;
    }
    
    public int getTentativasEnvio() {
        return tentativasEnvio;
    }
    
    public void setTentativasEnvio(int tentativasEnvio) {
        this.tentativasEnvio = tentativasEnvio;
    }
    
    public boolean isEnvioBemSucedido() {
        return envioBemSucedido;
    }
    
    public void setEnvioBemSucedido(boolean envioBemSucedido) {
        this.envioBemSucedido = envioBemSucedido;
    }
    
    public String getMensagem() {
        return mensagem;
    }
    
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    public String getDadosContexto() {
        return dadosContexto;
    }
    
    public void setDadosContexto(String dadosContexto) {
        this.dadosContexto = dadosContexto;
    }
}
