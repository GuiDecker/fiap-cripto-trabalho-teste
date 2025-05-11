package br.com.tiopatinhasexchange.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.tiopatinhasexchange.exceptions.ValorInvalidoException;

/**
 * Classe que representa uma carteira de criptoativos do usuário.
 */
public class Carteira {
    
    // Atributos
    private int id;
    private int idUsuario;
    private double saldo;
    private Map<Integer, Double> posicoes; // Mapa de criptoativos: <ID do criptoativo, quantidade>
    private List<Transacao> transacoes;

    // Construtores
    public Carteira() {
    }
    
    public Carteira(int id, int idUsuario) {
        this.id = id;
        this.idUsuario = idUsuario;
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
    public double depositar(double valor) throws ValorInvalidoException {
        if (valor > 0) {
            this.saldo += valor;
        }
        else if (valor < 0) {
            throw new ValorInvalidoException("O valor do depósito deve ser positivo");
        }
        return this.saldo;
    }
    
    /**
     * Remove saldo da carteira (saque)
     * @param valor Valor a ser removido
     * @return Novo saldo da carteira ou -1 se saldo insuficiente
     */
    public double sacar(double valor) throws ValorInvalidoException {
        if (valor > 0 && valor <= this.saldo) {
            this.saldo -= valor;
        }
        else if (valor < 0) {
            throw new ValorInvalidoException("O valor do saque deve ser positivo");            
        }
        return this.saldo;
    }
    
    /**
     * Compra um criptoativo
     * @param ativo Criptoativo a ser comprado
     * @param quantidade Quantidade a ser comprada
     * @param preco Preço unitário da compra
     * @return true se a compra foi bem-sucedida
     */
    public boolean comprarAtivo(Criptoativo ativo, double quantidade, double preco) {
        double custo = quantidade * preco;
        if (custo <= this.saldo) {
            this.saldo -= custo;
            
            if (this.posicoes == null) {
                this.posicoes = new HashMap<>();
            }
            
            Double atual = this.posicoes.getOrDefault(ativo.getId(), 0.0);
            this.posicoes.put(ativo.getId(), atual + quantidade);
            
            // Registrar transação
            Transacao transacao = new Transacao();
            transacao.setIdUsuario(this.idUsuario);
            transacao.setIdAtivo(ativo.getId());
            transacao.setTipo("compra");
            transacao.setVolumeTransacao(quantidade);
            this.transacoes.add(transacao);
            
            return true;
        }
        return false;
    }
    
    /**
     * Vende um criptoativo
     * @param ativo Criptoativo a ser vendido
     * @param quantidade Quantidade a ser vendida
     * @param preco Preço unitário da venda
     * @return true se a venda foi bem-sucedida
     */
    public boolean venderAtivo(Criptoativo ativo, double quantidade, double preco) {
        if (this.posicoes != null) {
            Double possuido = this.posicoes.getOrDefault(ativo.getId(), 0.0);
            if (possuido >= quantidade) {
                this.saldo += quantidade * preco;
                this.posicoes.put(ativo.getId(), possuido - quantidade);
                
                // Registrar transação
                Transacao transacao = new Transacao();
                transacao.setIdUsuario(this.idUsuario);
                transacao.setIdAtivo(ativo.getId());
                transacao.setTipo("venda");
                transacao.setVolumeTransacao(quantidade);
                this.transacoes.add(transacao);
                
                return true;
            }
        }
        return false;
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
        System.out.println("=== Carteira ID: " + this.id + " ===");
        System.out.println("Saldo: " + this.saldo);
        System.out.println("Quantidade de ativos: " + 
                (this.posicoes != null ? this.posicoes.size() : 0));
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