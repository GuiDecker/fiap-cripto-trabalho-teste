package br.com.tiopatinhasexchange.model;
import java.time.LocalDateTime;

/**
 * Classe que representa um criptoativo no sistema.
 * Versão simplificada que trata todos os criptoativos de forma unificada.
 */
public class Criptoativo {
    
    // Atributos básicos
    private int id;
    private String nome;
    private String simbolo;
    private double precoAtual;
    private double variacaoDia; // Variação percentual nas últimas 24h
    private double volumeNegociado;
    private LocalDateTime ultimaAtualizacao;
    
    // Atributos comuns para todos os criptoativos
    private String blockchain;         // Blockchain relacionada (ex: Bitcoin, Ethereum)
    private long ofertaCirculante;     // Quantidade atual em circulação
    private long ofertaMaxima;         // Quantidade máxima possível de emissão (se aplicável)
    
    // Construtores
    public Criptoativo() {
        this.ultimaAtualizacao = LocalDateTime.now();
    }
    
    public Criptoativo(int id, String nome, String simbolo, double precoAtual) {
        this.id = id;
        this.nome = nome;
        this.simbolo = simbolo;
        this.precoAtual = precoAtual;
        this.ultimaAtualizacao = LocalDateTime.now();
    }
    
    // Construtor completo
    public Criptoativo(int id, String nome, String simbolo, double precoAtual,
                      String blockchain, long ofertaMaxima, long ofertaCirculante) {
        this(id, nome, simbolo, precoAtual);
        this.blockchain = blockchain;
        this.ofertaMaxima = ofertaMaxima;
        this.ofertaCirculante = ofertaCirculante;
    }
    
    // Métodos
    /**
     * Atualiza o preço do criptoativo
     * @param novoPreco Novo preço
     */
    public void atualizarPreco(double novoPreco) {
        double precoAntigo = this.precoAtual;
        this.precoAtual = novoPreco;
        this.variacaoDia = ((novoPreco - precoAntigo) / precoAntigo) * 100;
        this.ultimaAtualizacao = LocalDateTime.now();
    }
    
    /**
     * Atualiza o preço e o volume do criptoativo (polimorfismo por sobrecarga)
     * @param novoPreco Novo preço
     * @param novoVolume Novo volume de negociação
     */
    public void atualizarPreco(double novoPreco, double novoVolume) {
        atualizarPreco(novoPreco);
        this.volumeNegociado = novoVolume;
    }
    
    /**
     * Atualiza o preço com informações da fonte (polimorfismo por sobrecarga)
     * @param novoPreco Novo preço
     * @param fonteDados Fonte dos dados
     */
    public void atualizarPreco(double novoPreco, String fonteDados) {
        atualizarPreco(novoPreco);
        System.out.println("Preço de " + this.simbolo + " atualizado usando dados de: " + fonteDados);
    }
    
    /**
     * Exibe as informações do criptoativo
     */
    public void exibirInfo() {
        System.out.println("=== " + this.nome + " (" + this.simbolo + ") ===");
        System.out.println("Preço atual: " + this.precoAtual);
        System.out.println("Variação 24h: " + this.variacaoDia + "%");
        System.out.println("Volume negociado: " + this.volumeNegociado);
        System.out.println("Última atualização: " + this.ultimaAtualizacao);
        System.out.println("Blockchain: " + this.blockchain);
        System.out.println("Oferta Circulante: " + this.ofertaCirculante);
        System.out.println("Oferta Máxima: " + (this.ofertaMaxima > 0 ? this.ofertaMaxima : "Sem limite"));
    }
    
    /**
     * Calcula a dominância no mercado do criptoativo
     * @param capitalizacaoTotalMercado Capitalização total do mercado
     * @return Percentual de dominância ou -1 se não for possível calcular
     */
    public double calcularDominanciaNoMercado(double capitalizacaoTotalMercado) {
        if (capitalizacaoTotalMercado <= 0 || this.ofertaCirculante <= 0) {
            return -1;
        }
        
        double capitalizacaoPropria = this.precoAtual * this.ofertaCirculante;
        return (capitalizacaoPropria / capitalizacaoTotalMercado) * 100;
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
    
    public String getSimbolo() {
        return simbolo;
    }
    
    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }
    
    public double getPrecoAtual() {
        return precoAtual;
    }
    
    public void setPrecoAtual(double precoAtual) {
        this.precoAtual = precoAtual;
    }
    
    public double getVariacaoDia() {
        return variacaoDia;
    }
    
    public void setVariacaoDia(double variacaoDia) {
        this.variacaoDia = variacaoDia;
    }
    
    public double getVolumeNegociado() {
        return volumeNegociado;
    }
    
    public void setVolumeNegociado(double volumeNegociado) {
        this.volumeNegociado = volumeNegociado;
    }
    
    public LocalDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }
    
    public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public String getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(String blockchain) {
        this.blockchain = blockchain;
    }

    public long getOfertaMaxima() {
        return ofertaMaxima;
    }

    public void setOfertaMaxima(long ofertaMaxima) {
        this.ofertaMaxima = ofertaMaxima;
    }

    public long getOfertaCirculante() {
        return ofertaCirculante;
    }

    public void setOfertaCirculante(long ofertaCirculante) {
        this.ofertaCirculante = ofertaCirculante;
    }
}