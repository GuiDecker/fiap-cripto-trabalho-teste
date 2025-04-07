import java.time.LocalDateTime;

/**
 * Classe que representa uma estratégia automática para compra e venda de criptoativos
 * com base em condições predefinidas pelo usuário.
 */
public class EstrategiaAutomatica {

    // Atributos
    private int id;
    private int idUsuario;
    private int idCriptoativo;
    private String condicao; // PRECO_ACIMA, PRECO_ABAIXO, VARIACAO_ACIMA, VARIACAO_ABAIXO
    private String acao; // COMPRAR, VENDER
    private double valorReferencia; // Valor de referência para a condição
    private double quantidade; // Quantidade a ser comprada ou vendida
    private boolean ativa; // Se a estratégia está ativa ou não
    private LocalDateTime dataCriacao;
    private LocalDateTime ultimaExecucao;
    private int idCarteira; // Carteira onde a estratégia será executada

    // Construtores
    public EstrategiaAutomatica() {
        this.dataCriacao = LocalDateTime.now();
        this.ativa = true;
    }
    
    public EstrategiaAutomatica(int idUsuario, int idCriptoativo, String condicao, String acao, 
                               double valorReferencia, double quantidade, int idCarteira) {
        this.idUsuario = idUsuario;
        this.idCriptoativo = idCriptoativo;
        this.condicao = condicao;
        this.acao = acao;
        this.valorReferencia = valorReferencia;
        this.quantidade = quantidade;
        this.idCarteira = idCarteira;
        this.dataCriacao = LocalDateTime.now();
        this.ativa = true;
    }

    // Métodos
    /**
     * Verifica se a estratégia deve ser executada com base nas condições de mercado
     * @param mercado Objeto Mercado com os dados atuais
     * @return true se a estratégia deve ser executada
     */
    public boolean verificarCondicao(Mercado mercado) {
        if (!this.ativa) {
            return false;
        }
        
        double precoAtual = mercado.obterPrecoAtual(this.idCriptoativo);
        
        // Verificar se o ativo existe no mercado
        if (precoAtual < 0) {
            return false;
        }
        
        // Verificar a condição
        switch (this.condicao) {
            case "PRECO_ACIMA":
                return precoAtual >= this.valorReferencia;
                
            case "PRECO_ABAIXO":
                return precoAtual <= this.valorReferencia;
                
            case "VARIACAO_ACIMA":
                double variacao = mercado.calcularVariacao(this.idCriptoativo, 24);
                return variacao >= this.valorReferencia;
                
            case "VARIACAO_ABAIXO":
                double variacaoNegativa = mercado.calcularVariacao(this.idCriptoativo, 24);
                return variacaoNegativa <= this.valorReferencia;
                
            default:
                return false;
        }
    }
    
    /**
     * Executa a estratégia na carteira do usuário
     * @param carteira Carteira do usuário
     * @param ativo Criptoativo a ser negociado
     * @param mercado Mercado para obter o preço atual
     * @return true se a estratégia foi executada com sucesso
     */
    public boolean executar(Carteira carteira, Criptoativo ativo, Mercado mercado) {
        if (!this.ativa || carteira.getId() != this.idCarteira) {
            return false;
        }
        
        double precoAtual = mercado.obterPrecoAtual(this.idCriptoativo);
        
        if (precoAtual < 0) {
            return false;
        }
        
        boolean resultado = false;
        
        // Executar a ação
        if (this.acao.equals("COMPRAR")) {
            resultado = carteira.comprarAtivo(ativo, this.quantidade, precoAtual);
        } else if (this.acao.equals("VENDER")) {
            resultado = carteira.venderAtivo(ativo, this.quantidade, precoAtual);
        }
        
        if (resultado) {
            this.ultimaExecucao = LocalDateTime.now();
            
            // Criar alerta para notificar o usuário
            Alerta alerta = Alerta.criarAlertaEstrategia(
                this.idUsuario, 
                this.idCriptoativo, 
                this.acao.toLowerCase(), 
                this.quantidade
            );
            
            // Em uma implementação real, o alerta seria salvo em banco de dados
            System.out.println("Alerta criado: " + alerta.getTitulo());
        }
        
        return resultado;
    }
    
    /**
     * Ativa ou desativa a estratégia
     * @param ativar true para ativar, false para desativar
     * @return true se a operação foi bem-sucedida
     */
    public boolean configurarAtivacao(boolean ativar) {
        this.ativa = ativar;
        return true;
    }
    
    /**
     * Exibe os detalhes da estratégia
     */
    public void exibirDetalhes() {
        System.out.println("=== Estratégia Automática ===");
        System.out.println("ID: " + this.id);
        System.out.println("Criptoativo: " + this.idCriptoativo);
        System.out.println("Condição: " + this.condicao);
        System.out.println("Valor de Referência: " + this.valorReferencia);
        System.out.println("Ação: " + this.acao);
        System.out.println("Quantidade: " + this.quantidade);
        System.out.println("Status: " + (this.ativa ? "Ativa" : "Inativa"));
        System.out.println("Data de Criação: " + this.dataCriacao);
        if (this.ultimaExecucao != null) {
            System.out.println("Última Execução: " + this.ultimaExecucao);
        }
        System.out.println("===========================");
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

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public double getValorReferencia() {
        return valorReferencia;
    }

    public void setValorReferencia(double valorReferencia) {
        this.valorReferencia = valorReferencia;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getUltimaExecucao() {
        return ultimaExecucao;
    }

    public void setUltimaExecucao(LocalDateTime ultimaExecucao) {
        this.ultimaExecucao = ultimaExecucao;
    }

    public int getIdCarteira() {
        return idCarteira;
    }

    public void setIdCarteira(int idCarteira) {
        this.idCarteira = idCarteira;
    }
}