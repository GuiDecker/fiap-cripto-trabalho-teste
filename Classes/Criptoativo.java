import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um criptoativo no sistema.
 * Pode ser uma criptomoeda, token, stablecoin, etc.
 */
public class Criptoativo {

    // Atributos
    private int id;
    private String nome; // Bitcoin, Ethereum, etc.
    private String sigla; // BTC, ETH, etc.
    private String tipo; // Criptomoeda, Stablecoin, Token, etc.
    private double valorAtual;
    private List<Double> historicoValores; // Lista de preços históricos
    private double volume; // Total de ativos em circulação
    private double capitalizacaoMercado; // Valor total do ativo (volume * valor)
    private List<LocalDateTime> dataHistorico; // Datas correspondentes aos valores históricos
    private double variacaoUltimas24h; // Variação percentual nas últimas 24h

    // Construtores
    public Criptoativo() {
        this.historicoValores = new ArrayList<>();
        this.dataHistorico = new ArrayList<>();
    }
    
    public Criptoativo(int id, String nome, String sigla, String tipo, double valorAtual, double volume) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
        this.tipo = tipo;
        this.valorAtual = valorAtual;
        this.volume = volume;
        this.capitalizacaoMercado = valorAtual * volume;
        this.historicoValores = new ArrayList<>();
        this.dataHistorico = new ArrayList<>();
        this.historicoValores.add(valorAtual);
        this.dataHistorico.add(LocalDateTime.now());
        this.variacaoUltimas24h = 0.0;
    }

    // Métodos
    /**
     * Atualiza o preço atual do criptoativo
     * @param novoValor Novo valor do criptoativo
     * @return true se o preço foi atualizado com sucesso
     */
    public boolean atualizarPreco(double novoValor) {
        if (novoValor <= 0) {
            return false;
        }
        
        double valorAnterior = this.valorAtual;
        this.valorAtual = novoValor;
        
        // Calcular a nova capitalização de mercado
        this.capitalizacaoMercado = this.valorAtual * this.volume;
        
        // Adicionar ao histórico
        this.historicoValores.add(novoValor);
        this.dataHistorico.add(LocalDateTime.now());
        
        // Calcular variação
        if (valorAnterior > 0) {
            double variacao = ((novoValor - valorAnterior) / valorAnterior) * 100;
            this.variacaoUltimas24h = variacao;
        }
        
        return true;
    }
    
    /**
     * Calcula a variação de preço em um período
     * @param dias Número de dias para calcular a variação
     * @return Variação percentual do preço no período especificado
     */
    public double calcularVariacao(int dias) {
        if (dias <= 0 || this.historicoValores.size() <= 1) {
            return 0.0;
        }
        
        // Limitar aos dados disponíveis
        int indiceInicial = Math.max(0, this.historicoValores.size() - dias - 1);
        double valorInicial = this.historicoValores.get(indiceInicial);
        double valorFinal = this.valorAtual;
        
        // Calcular variação percentual
        if (valorInicial == 0) {
            return 0.0;
        }
        
        return ((valorFinal - valorInicial) / valorInicial) * 100;
    }
    
    /**
     * Verifica se houve uma variação brusca no preço nas últimas 24h
     * @param limiteVariacao Limite percentual para considerar uma variação brusca
     * @return true se a variação ultrapassou o limite especificado
     */
    public boolean detectarVariacaoBrusca(double limiteVariacao) {
        return Math.abs(this.variacaoUltimas24h) > limiteVariacao;
    }
    
    /**
     * Exibe informações detalhadas sobre o criptoativo
     */
    public void exibirDetalhes() {
        System.out.println("=== Detalhes do Criptoativo ===");
        System.out.println("ID: " + this.id);
        System.out.println("Nome: " + this.nome + " (" + this.sigla + ")");
        System.out.println("Tipo: " + this.tipo);
        System.out.println("Valor Atual: " + this.valorAtual);
        System.out.println("Variação 24h: " + String.format("%.2f", this.variacaoUltimas24h) + "%");
        System.out.println("Volume: " + this.volume);
        System.out.println("Capitalização de Mercado: " + this.capitalizacaoMercado);
        System.out.println("Pontos no histórico: " + this.historicoValores.size());
        System.out.println("==============================");
    }
    
    /**
     * Compara o desempenho com outro criptoativo
     * @param outro Outro criptoativo a ser comparado
     * @param dias Período de comparação em dias
     * @return Diferença na variação percentual (atual - outro)
     */
    public double compararDesempenho(Criptoativo outro, int dias) {
        double variacaoAtual = this.calcularVariacao(dias);
        double variacaoOutro = outro.calcularVariacao(dias);
        
        return variacaoAtual - variacaoOutro;
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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(double valorAtual) {
        this.valorAtual = valorAtual;
        this.capitalizacaoMercado = this.valorAtual * this.volume;
    }

    public List<Double> getHistoricoValores() {
        return historicoValores;
    }

    public void setHistoricoValores(List<Double> historicoValores) {
        this.historicoValores = historicoValores;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
        this.capitalizacaoMercado = this.valorAtual * this.volume;
    }

    public double getCapitalizacaoMercado() {
        return capitalizacaoMercado;
    }

    public List<LocalDateTime> getDataHistorico() {
        return dataHistorico;
    }

    public void setDataHistorico(List<LocalDateTime> dataHistorico) {
        this.dataHistorico = dataHistorico;
    }

    public double getVariacaoUltimas24h() {
        return variacaoUltimas24h;
    }

    public void setVariacaoUltimas24h(double variacaoUltimas24h) {
        this.variacaoUltimas24h = variacaoUltimas24h;
    }
}