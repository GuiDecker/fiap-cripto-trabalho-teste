package br.com.tiopatinhasexchange.model;
import java.time.LocalDateTime;

/**
 * Classe que representa uma transação de compra ou venda de criptoativos.
 * Implementa polimorfismo através do campo tipo e métodos que se comportam diferentemente
 * baseados no tipo da transação.
 */
public class Transacao {
    
    // Atributos principais
    private int id;
    private int idUsuario;
    private int idCarteira;
    private int idAtivo;
    private String tipo; // "COMPRA" ou "VENDA"
    private double precoUnitario;
    private double volumeTransacao;
    private double valorTotal;
    private LocalDateTime dataHoraTransacao;
    private String status; // "PENDENTE", "CONCLUIDA", "CANCELADA"
    
    // Atributos específicos para compras
    private double taxaCompra;
    private String metodoPagamento;
    private String origemFundos;
    
    // Atributos específicos para vendas
    private double taxaVenda;
    private double lucroPrejuizo;
    private String destinoFundos;
    private boolean vendaTotal; // Se foi vendida toda a posição

    // Construtores
    public Transacao() {
        this.dataHoraTransacao = LocalDateTime.now();
        this.status = "PENDENTE";
    }
    
    public Transacao(int idUsuario, int idCarteira, int idAtivo, String tipo) {
        this.idUsuario = idUsuario;
        this.idCarteira = idCarteira;
        this.idAtivo = idAtivo;
        this.tipo = tipo;
        this.dataHoraTransacao = LocalDateTime.now();
        this.status = "PENDENTE";
    }
    
    // Construtor específico para transação de compra
    public Transacao(int idUsuario, int idCarteira, int idAtivo, 
                     double precoUnitario, double volumeTransacao, 
                     double taxaCompra, String metodoPagamento) {
        this(idUsuario, idCarteira, idAtivo, "COMPRA");
        this.precoUnitario = precoUnitario;
        this.volumeTransacao = volumeTransacao;
        this.valorTotal = precoUnitario * volumeTransacao;
        this.taxaCompra = taxaCompra;
        this.metodoPagamento = metodoPagamento;
    }
    
    // Construtor específico para transação de venda
    public Transacao(int idUsuario, int idCarteira, int idAtivo, 
                     double precoUnitario, double volumeTransacao,
                     double taxaVenda, double lucroPrejuizo) {
        this(idUsuario, idCarteira, idAtivo, "VENDA");
        this.precoUnitario = precoUnitario;
        this.volumeTransacao = volumeTransacao;
        this.valorTotal = precoUnitario * volumeTransacao;
        this.taxaVenda = taxaVenda;
        this.lucroPrejuizo = lucroPrejuizo;
    }
    
    // Métodos
    /**
     * Calcula o valor total da transação
     * @return Valor total (preço unitário * volume)
     */
    public double calcularValorTotal() {
        return this.precoUnitario * this.volumeTransacao;
    }
    
    /**
     * Calcula o valor líquido da transação (após taxas)
     * @return Valor líquido
     */
    public double calcularValorLiquido() {
        if ("COMPRA".equals(this.tipo)) {
            return this.valorTotal + this.taxaCompra;
        } else if ("VENDA".equals(this.tipo)) {
            return this.valorTotal - this.taxaVenda;
        }
        return this.valorTotal;
    }
    
    /**
     * Confirma a transação, mudando seu status para "CONCLUÍDA"
     * @return true se a confirmação foi bem-sucedida
     */
    public boolean confirmar() {
        if (!"PENDENTE".equals(this.status)) {
            return false;
        }
        
        this.status = "CONCLUIDA";
        return true;
    }
    
    /**
     * Cancela a transação, mudando seu status para "CANCELADA"
     * @return true se o cancelamento foi bem-sucedido
     */
    public boolean cancelar() {
        if (!"PENDENTE".equals(this.status)) {
            return false;
        }
        
        this.status = "CANCELADA";
        return true;
    }
    
    /**
     * Exibe os detalhes da transação
     */
    public void exibirDetalhes() {
        System.out.println("=== Transação ID: " + this.id + " ===");
        System.out.println("Tipo: " + this.tipo);
        System.out.println("Status: " + this.status);
        System.out.println("Data/Hora: " + this.dataHoraTransacao);
        System.out.println("Usuário ID: " + this.idUsuario);
        System.out.println("Carteira ID: " + this.idCarteira);
        System.out.println("Ativo ID: " + this.idAtivo);
        System.out.println("Preço Unitário: " + this.precoUnitario);
        System.out.println("Volume: " + this.volumeTransacao);
        System.out.println("Valor Total: " + this.valorTotal);
        
        // Exibe informações específicas baseado no tipo (polimorfismo)
        if ("COMPRA".equals(this.tipo)) {
            System.out.println("Taxa de Compra: " + this.taxaCompra);
            System.out.println("Método de Pagamento: " + this.metodoPagamento);
            if (this.origemFundos != null && !this.origemFundos.isEmpty()) {
                System.out.println("Origem dos Fundos: " + this.origemFundos);
            }
        } else if ("VENDA".equals(this.tipo)) {
            System.out.println("Taxa de Venda: " + this.taxaVenda);
            System.out.println("Lucro/Prejuízo: " + this.lucroPrejuizo);
            if (this.destinoFundos != null && !this.destinoFundos.isEmpty()) {
                System.out.println("Destino dos Fundos: " + this.destinoFundos);
            }
            System.out.println("Venda Total da Posição: " + (this.vendaTotal ? "Sim" : "Não"));
        }
    }
    
    /**
     * Método específico para processar uma compra
     * @param saldoDisponivel Saldo disponível para a compra
     * @return true se a compra pode ser realizada
     */
    public boolean processarCompra(double saldoDisponivel) {
        if (!"COMPRA".equals(this.tipo) || !"PENDENTE".equals(this.status)) {
            return false;
        }
        
        double custoTotal = calcularValorLiquido();
        if (custoTotal > saldoDisponivel) {
            return false;
        }
        
        // Aqui seria implementada a lógica de processamento da compra
        System.out.println("Processando compra de " + this.volumeTransacao + 
                           " unidades do ativo ID " + this.idAtivo + 
                           " por " + this.precoUnitario + " cada.");
        
        return true;
    }
    
    /**
     * Método específico para processar uma venda
     * @param volumeDisponivel Volume disponível do ativo para venda
     * @return true se a venda pode ser realizada
     */
    public boolean processarVenda(double volumeDisponivel) {
        if (!"VENDA".equals(this.tipo) || !"PENDENTE".equals(this.status)) {
            return false;
        }
        
        if (this.volumeTransacao > volumeDisponivel) {
            return false;
        }
        
        // Verifica se é uma venda total da posição
        this.vendaTotal = (this.volumeTransacao == volumeDisponivel);
        
        // Aqui seria implementada a lógica de processamento da venda
        System.out.println("Processando venda de " + this.volumeTransacao + 
                           " unidades do ativo ID " + this.idAtivo + 
                           " por " + this.precoUnitario + " cada.");
        
        return true;
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

    public int getIdCarteira() {
        return idCarteira;
    }

    public void setIdCarteira(int idCarteira) {
        this.idCarteira = idCarteira;
    }

    public int getIdAtivo() {
        return idAtivo;
    }

    public void setIdAtivo(int idAtivo) {
        this.idAtivo = idAtivo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public double getVolumeTransacao() {
        return volumeTransacao;
    }

    public void setVolumeTransacao(double volumeTransacao) {
        this.volumeTransacao = volumeTransacao;
        // Atualiza o valor total quando o volume é alterado
        if (this.precoUnitario > 0) {
            this.valorTotal = this.precoUnitario * volumeTransacao;
        }
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDateTime getDataHoraTransacao() {
        return dataHoraTransacao;
    }

    public void setDataHoraTransacao(LocalDateTime dataHoraTransacao) {
        this.dataHoraTransacao = dataHoraTransacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTaxaCompra() {
        return taxaCompra;
    }

    public void setTaxaCompra(double taxaCompra) {
        this.taxaCompra = taxaCompra;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getOrigemFundos() {
        return origemFundos;
    }

    public void setOrigemFundos(String origemFundos) {
        this.origemFundos = origemFundos;
    }

    public double getTaxaVenda() {
        return taxaVenda;
    }

    public void setTaxaVenda(double taxaVenda) {
        this.taxaVenda = taxaVenda;
    }

    public double getLucroPrejuizo() {
        return lucroPrejuizo;
    }

    public void setLucroPrejuizo(double lucroPrejuizo) {
        this.lucroPrejuizo = lucroPrejuizo;
    }

    public String getDestinoFundos() {
        return destinoFundos;
    }

    public void setDestinoFundos(String destinoFundos) {
        this.destinoFundos = destinoFundos;
    }

    public boolean isVendaTotal() {
        return vendaTotal;
    }

    public void setVendaTotal(boolean vendaTotal) {
        this.vendaTotal = vendaTotal;
    }
}