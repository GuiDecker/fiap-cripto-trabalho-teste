package br.com.tiopatinhasexchange.model;
import java.time.LocalDateTime;

/**
 * Classe associativa que representa o histórico de cotações de um criptoativo.
 * Registra o valor do ativo em diferentes momentos no tempo.
 */
public class AtivoCotacao {
    
    // Atributos - Chaves Primária e Estrangeiras
    private int id;                  // Chave primária
    private int idCriptoativo;       // Chave estrangeira referenciando Criptoativo
    
    // Atributos de valores e tempo
    private LocalDateTime timestamp;  // Momento da cotação
    private double precoAbertura;     // Preço de abertura do período
    private double precoFechamento;   // Preço de fechamento do período
    private double precoMaximo;       // Preço máximo do período
    private double precoMinimo;       // Preço mínimo do período
    private double volume;            // Volume negociado no período
    private double volumeFinanceiro;  // Volume financeiro negociado no período
    private String fonteDados;        // Fonte da cotação (API, exchange)
    
    // Construtores
    public AtivoCotacao() {
        this.timestamp = LocalDateTime.now();
    }
    
    public AtivoCotacao(int idCriptoativo, double preco) {
        this.idCriptoativo = idCriptoativo;
        this.timestamp = LocalDateTime.now();
        this.precoAbertura = preco;
        this.precoFechamento = preco;
        this.precoMaximo = preco;
        this.precoMinimo = preco;
    }
    
    public AtivoCotacao(int idCriptoativo, double precoAbertura, double precoFechamento, 
                       double precoMaximo, double precoMinimo) {
        this.idCriptoativo = idCriptoativo;
        this.timestamp = LocalDateTime.now();
        this.precoAbertura = precoAbertura;
        this.precoFechamento = precoFechamento;
        this.precoMaximo = precoMaximo;
        this.precoMinimo = precoMinimo;
    }
    
    // Métodos
    /**
     * Calcula a variação percentual do preço no período
     * @return Variação percentual
     */
    public double calcularVariacao() {
        if (precoAbertura == 0) {
            return 0.0;
        }
        return ((precoFechamento - precoAbertura) / precoAbertura) * 100;
    }
    
    /**
     * Verifica se o período teve movimento de alta
     * @return true se fechou acima da abertura
     */
    public boolean isAlta() {
        return precoFechamento > precoAbertura;
    }
    
    /**
     * Verifica se é um período de grande volatilidade
     * @return true se a amplitude for maior que 5% do preço médio
     */
    public boolean isVolatil() {
        double precoMedio = (precoMaximo + precoMinimo) / 2;
        double amplitude = precoMaximo - precoMinimo;
        return (amplitude / precoMedio) > 0.05; // 5% de volatilidade
    }
    
    /**
     * Método com sobrecarga (polimorfismo estático) para consolidar cotações
     * @param outraCotacao Outra cotação do mesmo período
     */
    public void consolidarCom(AtivoCotacao outraCotacao) {
        if (this.idCriptoativo != outraCotacao.idCriptoativo) {
            throw new IllegalArgumentException("Cotações de ativos diferentes não podem ser consolidadas");
        }
        
        // Mantém o preço de abertura do mais antigo
        if (outraCotacao.timestamp.isBefore(this.timestamp)) {
            this.precoAbertura = outraCotacao.precoAbertura;
        }
        
        // Fechamento é o do mais recente (que será o atual)
        // Mantém o máximo e mínimo
        this.precoMaximo = Math.max(this.precoMaximo, outraCotacao.precoMaximo);
        this.precoMinimo = Math.min(this.precoMinimo, outraCotacao.precoMinimo);
        
        // Soma os volumes
        this.volume += outraCotacao.volume;
        this.volumeFinanceiro += outraCotacao.volumeFinanceiro;
    }
    
    /**
     * Método com sobrecarga (polimorfismo estático) para consolidar cotações
     * com uma nova cotação simples (preço único)
     * @param novoPreco Nova cotação
     * @param novoVolume Volume adicional
     */
    public void consolidarCom(double novoPreco, double novoVolume) {
        // Ajusta o preço de fechamento para o mais recente
        this.precoFechamento = novoPreco;
        
        // Atualiza máximo e mínimo
        if (novoPreco > this.precoMaximo) {
            this.precoMaximo = novoPreco;
        }
        if (novoPreco < this.precoMinimo) {
            this.precoMinimo = novoPreco;
        }
        
        // Adiciona volume
        this.volume += novoVolume;
        this.volumeFinanceiro += (novoPreco * novoVolume);
    }
    
    /**
     * Exibe as informações da cotação
     */
    public void exibirDados() {
        System.out.println("=== Cotação do Ativo ID: " + this.idCriptoativo + " ===");
        System.out.println("Data/Hora: " + this.timestamp);
        System.out.println("Abertura: " + this.precoAbertura);
        System.out.println("Fechamento: " + this.precoFechamento);
        System.out.println("Máxima: " + this.precoMaximo);
        System.out.println("Mínima: " + this.precoMinimo);
        System.out.println("Variação: " + String.format("%.2f%%", calcularVariacao()));
        System.out.println("Volume: " + this.volume);
        if (this.fonteDados != null && !this.fonteDados.isEmpty()) {
            System.out.println("Fonte: " + this.fonteDados);
        }
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCriptoativo() {
        return idCriptoativo;
    }

    public void setIdCriptoativo(int idCriptoativo) {
        this.idCriptoativo = idCriptoativo;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getPrecoAbertura() {
        return precoAbertura;
    }

    public void setPrecoAbertura(double precoAbertura) {
        this.precoAbertura = precoAbertura;
    }

    public double getPrecoFechamento() {
        return precoFechamento;
    }

    public void setPrecoFechamento(double precoFechamento) {
        this.precoFechamento = precoFechamento;
    }

    public double getPrecoMaximo() {
        return precoMaximo;
    }

    public void setPrecoMaximo(double precoMaximo) {
        this.precoMaximo = precoMaximo;
    }

    public double getPrecoMinimo() {
        return precoMinimo;
    }

    public void setPrecoMinimo(double precoMinimo) {
        this.precoMinimo = precoMinimo;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getVolumeFinanceiro() {
        return volumeFinanceiro;
    }

    public void setVolumeFinanceiro(double volumeFinanceiro) {
        this.volumeFinanceiro = volumeFinanceiro;
    }

    public String getFonteDados() {
        return fonteDados;
    }

    public void setFonteDados(String fonteDados) {
        this.fonteDados = fonteDados;
    }
}