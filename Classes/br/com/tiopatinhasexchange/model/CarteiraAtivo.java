package br.com.tiopatinhasexchange.model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe associativa que representa o relacionamento entre Carteira e Criptoativo.
 * Gerencia a quantidade de cada criptoativo em uma carteira e seu histórico de transações.
 */
public class CarteiraAtivo {
    
    // Atributos - Chaves Primária e Estrangeiras
    private int id;                  // Chave primária
    private int idCarteira;          // Chave estrangeira referenciando Carteira
    private int idCriptoativo;       // Chave estrangeira referenciando Criptoativo
    
    // Atributos de relacionamento
    private double quantidade;       // Quantidade do criptoativo na carteira
    private double precoMedio;       // Preço médio de compra
    private double valorTotalInvestido; // Valor total investido neste ativo
    private LocalDateTime ultimaAtualizacao;
    private List<Integer> idTransacoes; // IDs das transações relacionadas a este ativo na carteira
    
    // Construtores
    public CarteiraAtivo() {
        this.quantidade = 0.0;
        this.precoMedio = 0.0;
        this.valorTotalInvestido = 0.0;
        this.ultimaAtualizacao = LocalDateTime.now();
        this.idTransacoes = new ArrayList<>();
    }
    
    public CarteiraAtivo(int idCarteira, int idCriptoativo) {
        this.idCarteira = idCarteira;
        this.idCriptoativo = idCriptoativo;
        this.quantidade = 0.0;
        this.precoMedio = 0.0;
        this.valorTotalInvestido = 0.0;
        this.ultimaAtualizacao = LocalDateTime.now();
        this.idTransacoes = new ArrayList<>();
    }
    
    // Métodos
    /**
     * Adiciona quantidade de criptoativo à carteira (compra)
     * @param quantidade Quantidade a ser adicionada
     * @param precoUnitario Preço unitário da compra
     * @param idTransacao ID da transação associada
     * @return true se a operação foi bem-sucedida
     */
    public boolean adicionarQuantidade(double quantidade, double precoUnitario, int idTransacao) {
        if (quantidade <= 0) {
            return false;
        }
        
        double valorCompra = quantidade * precoUnitario;
        double novoValorTotal = this.valorTotalInvestido + valorCompra;
        double novaQuantidadeTotal = this.quantidade + quantidade;
        
        // Atualiza preço médio de compra
        if (novaQuantidadeTotal > 0) {
            this.precoMedio = novoValorTotal / novaQuantidadeTotal;
        }
        
        this.quantidade = novaQuantidadeTotal;
        this.valorTotalInvestido = novoValorTotal;
        this.ultimaAtualizacao = LocalDateTime.now();
        
        // Registra a transação
        if (!this.idTransacoes.contains(idTransacao)) {
            this.idTransacoes.add(idTransacao);
        }
        
        return true;
    }
    
    /**
     * Remove quantidade de criptoativo da carteira (venda)
     * @param quantidade Quantidade a ser removida
     * @param precoUnitario Preço unitário da venda
     * @param idTransacao ID da transação associada
     * @return Lucro ou prejuízo realizado na operação, ou -1 se falhar
     */
    public double removerQuantidade(double quantidade, double precoUnitario, int idTransacao) {
        if (quantidade <= 0 || quantidade > this.quantidade) {
            return -1.0;
        }
        
        // Calcula resultado da operação
        double valorVenda = quantidade * precoUnitario;
        double custoMedio = quantidade * this.precoMedio;
        double resultado = valorVenda - custoMedio;
        
        // Atualiza os valores da carteira
        this.quantidade -= quantidade;
        
        // Proporção do investimento que foi realizada
        double proporcaoVendida = quantidade / (quantidade + this.quantidade);
        this.valorTotalInvestido -= (this.valorTotalInvestido * proporcaoVendida);
        
        this.ultimaAtualizacao = LocalDateTime.now();
        
        // Registra a transação
        if (!this.idTransacoes.contains(idTransacao)) {
            this.idTransacoes.add(idTransacao);
        }
        
        return resultado;
    }
    
    /**
     * Sobrecarga do método removerQuantidade (polimorfismo estático)
     * @param percentual Percentual da posição a ser vendido (0-100)
     * @param precoUnitario Preço unitário da venda
     * @param idTransacao ID da transação associada
     * @return Lucro ou prejuízo realizado na operação, ou -1 se falhar
     */
    public double removerQuantidade(double percentual, double precoUnitario, 
                                  int idTransacao, boolean isPercentual) {
        if (!isPercentual || percentual <= 0 || percentual > 100) {
            return -1.0;
        }
        
        double quantidadeVendida = this.quantidade * (percentual / 100.0);
        return removerQuantidade(quantidadeVendida, precoUnitario, idTransacao);
    }
    
    /**
     * Calcula o valor atual da posição baseado no preço atual do mercado
     * @param precoAtual Preço atual do criptoativo
     * @return Valor atual da posição
     */
    public double calcularValorAtual(double precoAtual) {
        return this.quantidade * precoAtual;
    }
    
    /**
     * Calcula o lucro ou prejuízo não realizado (papel)
     * @param precoAtual Preço atual do criptoativo
     * @return Lucro ou prejuízo não realizado
     */
    public double calcularResultadoNaoRealizado(double precoAtual) {
        double valorAtual = calcularValorAtual(precoAtual);
        double custoTotal = this.quantidade * this.precoMedio;
        return valorAtual - custoTotal;
    }
    
    /**
     * Exibe os detalhes da posição
     * @param nomeAtivo Nome do criptoativo
     * @param precoAtual Preço atual do criptoativo
     */
    public void exibirDetalhes(String nomeAtivo, double precoAtual) {
        System.out.println("=== Posição: " + nomeAtivo + " ===");
        System.out.println("Quantidade: " + this.quantidade);
        System.out.println("Preço Médio: " + String.format("%.2f", this.precoMedio));
        System.out.println("Valor Investido: " + String.format("%.2f", this.valorTotalInvestido));
        
        double valorAtual = calcularValorAtual(precoAtual);
        System.out.println("Valor Atual: " + String.format("%.2f", valorAtual));
        
        double resultadoNaoRealizado = calcularResultadoNaoRealizado(precoAtual);
        System.out.println("Resultado Não Realizado: " + String.format("%.2f", resultadoNaoRealizado));
        
        double percentualRetorno = (resultadoNaoRealizado / this.valorTotalInvestido) * 100;
        System.out.println("Retorno: " + String.format("%.2f%%", percentualRetorno));
        System.out.println("Última Atualização: " + this.ultimaAtualizacao);
        System.out.println("Número de Transações: " + this.idTransacoes.size());
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCarteira() {
        return idCarteira;
    }

    public void setIdCarteira(int idCarteira) {
        this.idCarteira = idCarteira;
    }

    public int getIdCriptoativo() {
        return idCriptoativo;
    }

    public void setIdCriptoativo(int idCriptoativo) {
        this.idCriptoativo = idCriptoativo;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoMedio() {
        return precoMedio;
    }

    public void setPrecoMedio(double precoMedio) {
        this.precoMedio = precoMedio;
    }

    public double getValorTotalInvestido() {
        return valorTotalInvestido;
    }

    public void setValorTotalInvestido(double valorTotalInvestido) {
        this.valorTotalInvestido = valorTotalInvestido;
    }

    public LocalDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public List<Integer> getIdTransacoes() {
        return idTransacoes;
    }

    public void setIdTransacoes(List<Integer> idTransacoes) {
        this.idTransacoes = idTransacoes;
    }
}