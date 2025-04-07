import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe que representa uma carteira de criptoativos do usuário.
 * Pode ser do tipo Hot Wallet (online) ou Cold Wallet (armazenamento offline).
 */
public class Carteira {
    
    // Atributos
    private int id;
    private int idUsuario;
    private String tipo; // Hot Wallet ou Cold Wallet
    private double saldo;
    private Map<Integer, Double> posicoes; // Mapa de criptoativos: <ID do criptoativo, quantidade>
    private List<Transacao> transacoes;

    // Construtores
    public Carteira() {
        this.posicoes = new HashMap<>();
        this.transacoes = new ArrayList<>();
    }
    
    public Carteira(int id, int idUsuario, String tipo) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.tipo = tipo;
        this.saldo = 0.0;
        this.posicoes = new HashMap<>();
        this.transacoes = new ArrayList<>();
    }

    // Métodos
    /**
     * Adiciona saldo à carteira (depósito)
     * @param valor Valor a ser adicionado
     * @return Novo saldo da carteira
     */
    public double depositar(double valor) {
        if (valor > 0) {
            this.saldo += valor;
            return this.saldo;
        }
        return -1; // Erro: valor negativo
    }
    
    /**
     * Remove saldo da carteira (saque)
     * @param valor Valor a ser removido
     * @return Novo saldo da carteira ou -1 se saldo insuficiente
     */
    public double sacar(double valor) {
        if (valor <= 0) {
            return -1; // Erro: valor negativo ou zero
        }
        
        if (valor > this.saldo) {
            return -1; // Erro: saldo insuficiente
        }
        
        this.saldo -= valor;
        return this.saldo;
    }
    
    /**
     * Compra um criptoativo
     * @param ativo Criptoativo a ser comprado
     * @param quantidade Quantidade a ser comprada
     * @param valorUnitario Valor unitário da compra
     * @return true se a compra foi bem-sucedida
     */
    public boolean comprarAtivo(Criptoativo ativo, double quantidade, double valorUnitario) {
        double valorTotal = quantidade * valorUnitario;
        
        // Verificar se há saldo suficiente
        if (valorTotal > this.saldo) {
            return false;
        }
        
        // Realizar a compra
        this.saldo -= valorTotal;
        
        // Atualizar posição do criptoativo
        Integer idAtivo = ativo.getId();
        Double posicaoAtual = this.posicoes.getOrDefault(idAtivo, 0.0);
        this.posicoes.put(idAtivo, posicaoAtual + quantidade);
        
        // Registrar transação
        Transacao transacao = new Transacao();
        transacao.setIdUsuario(this.idUsuario);
        transacao.setIdAtivo(idAtivo);
        transacao.setTipo("compra");
        transacao.setVolumeTransacao(quantidade);
        this.transacoes.add(transacao);
        
        return true;
    }
    
    /**
     * Vende um criptoativo
     * @param ativo Criptoativo a ser vendido
     * @param quantidade Quantidade a ser vendida
     * @param valorUnitario Valor unitário da venda
     * @return true se a venda foi bem-sucedida
     */
    public boolean venderAtivo(Criptoativo ativo, double quantidade, double valorUnitario) {
        Integer idAtivo = ativo.getId();
        
        // Verificar se o usuário possui o ativo na quantidade desejada
        Double posicaoAtual = this.posicoes.getOrDefault(idAtivo, 0.0);
        if (posicaoAtual < quantidade) {
            return false;
        }
        
        // Realizar a venda
        double valorTotal = quantidade * valorUnitario;
        this.saldo += valorTotal;
        
        // Atualizar posição do criptoativo
        this.posicoes.put(idAtivo, posicaoAtual - quantidade);
        
        // Se a posição ficou zerada, remover o ativo da carteira
        if (posicaoAtual - quantidade <= 0) {
            this.posicoes.remove(idAtivo);
        }
        
        // Registrar transação
        Transacao transacao = new Transacao();
        transacao.setIdUsuario(this.idUsuario);
        transacao.setIdAtivo(idAtivo);
        transacao.setTipo("venda");
        transacao.setVolumeTransacao(quantidade);
        this.transacoes.add(transacao);
        
        return true;
    }
    
    /**
     * Transfere criptoativos para outra carteira
     * @param ativo Criptoativo a ser transferido
     * @param quantidade Quantidade a ser transferida
     * @param carteiraDestino Carteira de destino
     * @return true se a transferência foi bem-sucedida
     */
    public boolean transferir(Criptoativo ativo, double quantidade, Carteira carteiraDestino) {
        Integer idAtivo = ativo.getId();
        
        // Verificar se o usuário possui o ativo na quantidade desejada
        Double posicaoAtual = this.posicoes.getOrDefault(idAtivo, 0.0);
        if (posicaoAtual < quantidade) {
            return false;
        }
        
        // Remover da carteira atual
        this.posicoes.put(idAtivo, posicaoAtual - quantidade);
        
        // Adicionar na carteira de destino
        Double posicaoDestino = carteiraDestino.getPosicoes().getOrDefault(idAtivo, 0.0);
        carteiraDestino.getPosicoes().put(idAtivo, posicaoDestino + quantidade);
        
        // Registrar transações
        Transacao transacaoSaida = new Transacao();
        transacaoSaida.setIdUsuario(this.idUsuario);
        transacaoSaida.setIdAtivo(idAtivo);
        transacaoSaida.setTipo("transferência_saída");
        transacaoSaida.setVolumeTransacao(quantidade);
        this.transacoes.add(transacaoSaida);
        
        Transacao transacaoEntrada = new Transacao();
        transacaoEntrada.setIdUsuario(carteiraDestino.getIdUsuario());
        transacaoEntrada.setIdAtivo(idAtivo);
        transacaoEntrada.setTipo("transferência_entrada");
        transacaoEntrada.setVolumeTransacao(quantidade);
        carteiraDestino.getTransacoes().add(transacaoEntrada);
        
        return true;
    }
    
    /**
     * Calcula o valor total da carteira com base nos preços atuais dos ativos
     * @param mercado Objeto Mercado com os preços atuais
     * @return Valor total da carteira
     */
    public double calcularValorTotal(Mercado mercado) {
        double valorTotal = this.saldo;
        
        for (Map.Entry<Integer, Double> posicao : this.posicoes.entrySet()) {
            Integer idAtivo = posicao.getKey();
            Double quantidade = posicao.getValue();
            
            // Obter preço atual do ativo no mercado
            double precoAtual = mercado.obterPrecoAtual(idAtivo);
            
            // Somar ao valor total
            valorTotal += quantidade * precoAtual;
        }
        
        return valorTotal;
    }
    
    /**
     * Exibe um resumo da carteira
     */
    public void exibirResumo() {
        System.out.println("=== Resumo da Carteira ===");
        System.out.println("ID: " + this.id);
        System.out.println("Tipo: " + this.tipo);
        System.out.println("Saldo disponível: " + this.saldo);
        System.out.println("Ativos: " + this.posicoes.size());
        System.out.println("Transações: " + this.transacoes.size());
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Map<Integer, Double> getPosicoes() {
        return posicoes;
    }

    public void setPosicoes(Map<Integer, Double> posicoes) {
        this.posicoes = posicoes;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }
}