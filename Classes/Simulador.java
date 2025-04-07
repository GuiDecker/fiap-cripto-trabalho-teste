import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Classe responsável por simular investimentos em criptoativos sem risco real,
 * permitindo que usuários testem estratégias antes de investir com dinheiro real.
 */
public class Simulador {
    
    // Atributos
    private int id;
    private int idUsuario;
    private double saldoVirtual;
    private Map<Integer, Double> posicoes; // Mapa de posições: <ID do ativo, quantidade>
    private List<Map<String, Object>> historico; // Histórico de operações simuladas
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private double rendimentoTotal;
    private boolean ativo;
    private Map<String, Object> parametrosSimulacao; // Parâmetros personalizáveis da simulação

    // Construtores
    public Simulador() {
        this.posicoes = new HashMap<>();
        this.historico = new ArrayList<>();
        this.dataInicio = LocalDateTime.now();
        this.saldoVirtual = 10000.0; // Saldo inicial padrão
        this.rendimentoTotal = 0.0;
        this.ativo = true;
        this.parametrosSimulacao = new HashMap<>();
    }
    
    public Simulador(int idUsuario, double saldoInicial) {
        this.idUsuario = idUsuario;
        this.posicoes = new HashMap<>();
        this.historico = new ArrayList<>();
        this.dataInicio = LocalDateTime.now();
        this.saldoVirtual = saldoInicial;
        this.rendimentoTotal = 0.0;
        this.ativo = true;
        this.parametrosSimulacao = new HashMap<>();
    }

    // Métodos
    /**
     * Simula a compra de um criptoativo
     * @param idCriptoativo ID do criptoativo
     * @param quantidade Quantidade a ser comprada
     * @param valorUnitario Valor unitário da compra
     * @return true se a compra simulada foi bem-sucedida
     */
    public boolean simularCompra(int idCriptoativo, double quantidade, double valorUnitario) {
        if (!this.ativo) {
            return false;
        }
        
        double valorTotal = quantidade * valorUnitario;
        
        // Verificar se há saldo suficiente
        if (valorTotal > this.saldoVirtual) {
            return false;
        }
        
        // Realizar a compra simulada
        this.saldoVirtual -= valorTotal;
        
        // Atualizar posição do criptoativo
        Double posicaoAtual = this.posicoes.getOrDefault(idCriptoativo, 0.0);
        this.posicoes.put(idCriptoativo, posicaoAtual + quantidade);
        
        // Registrar operação no histórico
        Map<String, Object> operacao = new HashMap<>();
        operacao.put("tipo", "compra");
        operacao.put("idCriptoativo", idCriptoativo);
        operacao.put("quantidade", quantidade);
        operacao.put("valorUnitario", valorUnitario);
        operacao.put("valorTotal", valorTotal);
        operacao.put("dataHora", LocalDateTime.now());
        this.historico.add(operacao);
        
        return true;
    }
    
    /**
     * Simula a venda de um criptoativo
     * @param idCriptoativo ID do criptoativo
     * @param quantidade Quantidade a ser vendida
     * @param valorUnitario Valor unitário da venda
     * @return true se a venda simulada foi bem-sucedida
     */
    public boolean simularVenda(int idCriptoativo, double quantidade, double valorUnitario) {
        if (!this.ativo) {
            return false;
        }
        
        // Verificar se o usuário possui o ativo na quantidade desejada
        Double posicaoAtual = this.posicoes.getOrDefault(idCriptoativo, 0.0);
        if (posicaoAtual < quantidade) {
            return false;
        }
        
        // Realizar a venda simulada
        double valorTotal = quantidade * valorUnitario;
        this.saldoVirtual += valorTotal;
        
        // Atualizar posição do criptoativo
        this.posicoes.put(idCriptoativo, posicaoAtual - quantidade);
        
        // Se a posição ficou zerada, remover o ativo da carteira
        if (posicaoAtual - quantidade <= 0) {
            this.posicoes.remove(idCriptoativo);
        }
        
        // Registrar operação no histórico
        Map<String, Object> operacao = new HashMap<>();
        operacao.put("tipo", "venda");
        operacao.put("idCriptoativo", idCriptoativo);
        operacao.put("quantidade", quantidade);
        operacao.put("valorUnitario", valorUnitario);
        operacao.put("valorTotal", valorTotal);
        operacao.put("dataHora", LocalDateTime.now());
        this.historico.add(operacao);
        
        return true;
    }
    
    /**
     * Simula a variação de preço de um ativo ao longo do tempo
     * @param idCriptoativo ID do criptoativo
     * @param precoInicial Preço inicial do ativo
     * @param volatilidade Volatilidade do ativo (0.0 a 1.0)
     * @param diasSimulados Número de dias a simular
     * @return Lista de preços simulados para cada dia
     */
    public List<Double> simularVariacaoPreco(int idCriptoativo, double precoInicial, double volatilidade, int diasSimulados) {
        List<Double> precos = new ArrayList<>();
        double precoAtual = precoInicial;
        precos.add(precoAtual);
        
        Random random = new Random();
        
        for (int i = 0; i < diasSimulados; i++) {
            // Gerar variação aleatória com base na volatilidade
            double variacao = (random.nextDouble() * 2 - 1) * volatilidade;
            precoAtual = precoAtual * (1 + variacao);
            
            // Garantir que o preço não fique negativo
            if (precoAtual < 0.01) {
                precoAtual = 0.01;
            }
            
            precos.add(precoAtual);
        }
        
        return precos;
    }
    
    /**
     * Simula o resultado de uma estratégia automática ao longo do tempo
     * @param estrategia Estratégia a ser simulada
     * @param mercado Mercado simulado
     * @param diasSimulados Número de dias a simular
     * @return Rendimento percentual da estratégia
     */
    public double simularEstrategia(EstrategiaAutomatica estrategia, Map<Integer, List<Double>> mercadoSimulado, int diasSimulados) {
        double saldoInicial = this.saldoVirtual;
        int idCriptoativo = estrategia.getIdCriptoativo();
        
        // Verificar se o ativo existe no mercado simulado
        if (!mercadoSimulado.containsKey(idCriptoativo)) {
            return 0.0;
        }
        
        List<Double> precos = mercadoSimulado.get(idCriptoativo);
        
        // Simular cada dia
        for (int dia = 0; dia < Math.min(diasSimulados, precos.size() - 1); dia++) {
            double precoAtual = precos.get(dia);
            
            // Simular verificação da condição da estratégia
            boolean executar = false;
            
            if (estrategia.getCondicao().equals("PRECO_ACIMA")) {
                executar = precoAtual >= estrategia.getValorReferencia();
            } else if (estrategia.getCondicao().equals("PRECO_ABAIXO")) {
                executar = precoAtual <= estrategia.getValorReferencia();
            }
            
            // Executar a estratégia se a condição for atendida
            if (executar) {
                if (estrategia.getAcao().equals("COMPRAR")) {
                    simularCompra(idCriptoativo, estrategia.getQuantidade(), precoAtual);
                } else if (estrategia.getAcao().equals("VENDER")) {
                    simularVenda(idCriptoativo, estrategia.getQuantidade(), precoAtual);
                }
            }
        }
        
        // Calcular rendimento
        double saldoFinal = this.saldoVirtual;
        for (Map.Entry<Integer, Double> posicao : this.posicoes.entrySet()) {
            int id = posicao.getKey();
            double quantidade = posicao.getValue();
            
            if (mercadoSimulado.containsKey(id)) {
                List<Double> precosAtivo = mercadoSimulado.get(id);
                double ultimoPreco = precosAtivo.get(precosAtivo.size() - 1);
                saldoFinal += quantidade * ultimoPreco;
            }
        }
        
        this.rendimentoTotal = ((saldoFinal - saldoInicial) / saldoInicial) * 100;
        return this.rendimentoTotal;
    }
    
    /**
     * Finaliza a simulação
     */
    public void finalizarSimulacao() {
        this.ativo = false;
        this.dataFim = LocalDateTime.now();
    }
    
    /**
     * Reinicia a simulação com os mesmos parâmetros
     * @param saldoInicial Novo saldo inicial
     * @return true se a simulação foi reiniciada com sucesso
     */
    public boolean reiniciarSimulacao(double saldoInicial) {
        this.posicoes.clear();
        this.historico.clear();
        this.dataInicio = LocalDateTime.now();
        this.dataFim = null;
        this.saldoVirtual = saldoInicial;
        this.rendimentoTotal = 0.0;
        this.ativo = true;
        
        return true;
    }
    
    /**
     * Exibe um resumo da simulação
     */
    public void exibirResumoSimulacao() {
        System.out.println("=== Resumo da Simulação ===");
        System.out.println("ID do Usuário: " + this.idUsuario);
        System.out.println("Data de Início: " + this.dataInicio);
        if (this.dataFim != null) {
            System.out.println("Data de Fim: " + this.dataFim);
        }
        System.out.println("Status: " + (this.ativo ? "Em andamento" : "Finalizada"));
        System.out.println("Saldo Virtual: " + this.saldoVirtual);
        System.out.println("Posições: " + this.posicoes.size());
        System.out.println("Operações: " + this.historico.size());
        System.out.println("Rendimento Total: " + String.format("%.2f", this.rendimentoTotal) + "%");
        System.out.println("==========================");
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

    public double getSaldoVirtual() {
        return saldoVirtual;
    }

    public void setSaldoVirtual(double saldoVirtual) {
        this.saldoVirtual = saldoVirtual;
    }

    public Map<Integer, Double> getPosicoes() {
        return posicoes;
    }

    public void setPosicoes(Map<Integer, Double> posicoes) {
        this.posicoes = posicoes;
    }

    public List<Map<String, Object>> getHistorico() {
        return historico;
    }

    public void setHistorico(List<Map<String, Object>> historico) {
        this.historico = historico;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public double getRendimentoTotal() {
        return rendimentoTotal;
    }

    public void setRendimentoTotal(double rendimentoTotal) {
        this.rendimentoTotal = rendimentoTotal;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Map<String, Object> getParametrosSimulacao() {
        return parametrosSimulacao;
    }

    public void setParametrosSimulacao(Map<String, Object> parametrosSimulacao) {
        this.parametrosSimulacao = parametrosSimulacao;
    }
}