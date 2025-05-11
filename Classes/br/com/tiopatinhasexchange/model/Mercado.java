package br.com.tiopatinhasexchange.model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe responsável por coletar, processar e fornecer dados em tempo real sobre
 * o mercado de criptoativos, como preços, tendências e eventos críticos.
 */
public class Mercado {
    /* Coletar, processar e fornecer dados em tempo real sobre o mercado de criptoativos, como preços, tendências e eventos críticos. Essas informações são essenciais para reduzir o impacto da volatilidade e apoiar decisões de investimento. */

    // Atributos
    private Map<Integer, Double> precosAtuais; // Mapa de preços atuais: <ID do ativo, preço>
    private List<Map<Integer, Double>> historicoPrecos; // Histórico de preços
    private List<LocalDateTime> timestampsHistorico; // Timestamps correspondentes ao histórico
    private Map<Integer, Double> volumeNegociacao24h; // Volume negociado nas últimas 24h por ativo
    private Map<String, Double> indicadoresMercado; // Indicadores gerais do mercado (dominância BTC, etc.)
    private List<String> eventos; // Eventos importantes (notícias, regulamentações, etc.)
    private LocalDateTime ultimaAtualizacao; // Timestamp da última atualização de dados

    // Construtores
    public Mercado() {
        this.precosAtuais = new HashMap<>();
        this.historicoPrecos = new ArrayList<>();
        this.timestampsHistorico = new ArrayList<>();
        this.volumeNegociacao24h = new HashMap<>();
        this.indicadoresMercado = new HashMap<>();
        this.eventos = new ArrayList<>();
        this.ultimaAtualizacao = LocalDateTime.now();
    }

    // Métodos
    /**
     * Atualiza os preços dos criptoativos a partir de uma fonte externa (API)
     * @param dadosAtualizados Mapa com os preços atualizados
     * @return true se a atualização foi bem-sucedida
     */
    public boolean atualizarPrecos(Map<Integer, Double> dadosAtualizados) {
        if (dadosAtualizados == null || dadosAtualizados.isEmpty()) {
            return false;
        }
        
        // Salvar preços anteriores no histórico
        Map<Integer, Double> precosAnteriores = new HashMap<>(this.precosAtuais);
        this.historicoPrecos.add(precosAnteriores);
        this.timestampsHistorico.add(this.ultimaAtualizacao);
        
        // Atualizar preços
        this.precosAtuais.putAll(dadosAtualizados);
        this.ultimaAtualizacao = LocalDateTime.now();
        
        return true;
    }
    
    /**
     * Atualiza volume de negociação nas últimas 24h
     * @param dadosVolume Mapa com os volumes atualizados
     * @return true se a atualização foi bem-sucedida
     */
    public boolean atualizarVolumeNegociacao(Map<Integer, Double> dadosVolume) {
        if (dadosVolume == null || dadosVolume.isEmpty()) {
            return false;
        }
        
        this.volumeNegociacao24h.putAll(dadosVolume);
        return true;
    }
    
    /**
     * Adiciona um evento importante ao mercado
     * @param evento Descrição do evento
     * @return true se o evento foi adicionado
     */
    public boolean adicionarEvento(String evento) {
        if (evento == null || evento.isEmpty()) {
            return false;
        }
        
        this.eventos.add(evento);
        return true;
    }
    
    /**
     * Obtém o preço atual de um criptoativo
     * @param idAtivo ID do criptoativo
     * @return Preço atual ou -1 se o ativo não existir
     */
    public double obterPrecoAtual(Integer idAtivo) {
        return this.precosAtuais.getOrDefault(idAtivo, -1.0);
    }
    
    /**
     * Calcula a variação de preço de um ativo
     * @param idAtivo ID do criptoativo
     * @param periodoHoras Período em horas para calcular a variação
     * @return Variação percentual no período
     */
    public double calcularVariacao(Integer idAtivo, int periodoHoras) {
        // Verificar se o ativo existe
        if (!this.precosAtuais.containsKey(idAtivo)) {
            return 0.0;
        }
        
        double precoAtual = this.precosAtuais.get(idAtivo);
        
        // Verificar se há histórico suficiente
        if (this.historicoPrecos.size() < 1) {
            return 0.0;
        }
        
        // Encontrar o preço mais próximo do período solicitado
        LocalDateTime alvoTimestamp = this.ultimaAtualizacao.minusHours(periodoHoras);
        int indiceAlvo = 0;
        
        for (int i = 0; i < this.timestampsHistorico.size(); i++) {
            if (this.timestampsHistorico.get(i).isAfter(alvoTimestamp)) {
                indiceAlvo = i;
                break;
            }
        }
        
        if (indiceAlvo >= this.historicoPrecos.size()) {
            indiceAlvo = this.historicoPrecos.size() - 1;
        }
        
        Map<Integer, Double> precosAnteriores = this.historicoPrecos.get(indiceAlvo);
        double precoAnterior = precosAnteriores.getOrDefault(idAtivo, precoAtual);
        
        if (precoAnterior == 0) {
            return 0.0;
        }
        
        return ((precoAtual - precoAnterior) / precoAnterior) * 100;
    }
    
    /**
     * Detecta ativos com variação brusca de preço
     * @param limiteVariacao Limite percentual para considerar variação brusca
     * @return Mapa com os ativos e suas variações que excederam o limite
     */
    public Map<Integer, Double> detectarVariacoesBruscas(double limiteVariacao) {
        Map<Integer, Double> variacoesBruscas = new HashMap<>();
        
        for (Integer idAtivo : this.precosAtuais.keySet()) {
            double variacao24h = calcularVariacao(idAtivo, 24);
            
            if (Math.abs(variacao24h) >= limiteVariacao) {
                variacoesBruscas.put(idAtivo, variacao24h);
            }
        }
        
        return variacoesBruscas;
    }
    
    /**
     * Exibe um resumo do mercado atual
     */
    public void exibirResumoMercado() {
        System.out.println("=== Resumo do Mercado ===");
        System.out.println("Última Atualização: " + this.ultimaAtualizacao);
        System.out.println("Ativos Monitorados: " + this.precosAtuais.size());
        
        // Calcular total de capitalização
        double capitalizacaoTotal = 0.0;
        for (Map.Entry<Integer, Double> entry : this.precosAtuais.entrySet()) {
            Integer idAtivo = entry.getKey();
            Double preco = entry.getValue();
            Double volume = this.volumeNegociacao24h.getOrDefault(idAtivo, 0.0);
            capitalizacaoTotal += preco * volume;
        }
        
        System.out.println("Capitalização Total: " + capitalizacaoTotal);
        System.out.println("Dominância BTC: " + this.indicadoresMercado.getOrDefault("dominancia_btc", 0.0) + "%");
        System.out.println("Eventos Recentes: " + (this.eventos.isEmpty() ? "Nenhum" : this.eventos.get(this.eventos.size() - 1)));
        System.out.println("========================");
    }

    // Getters e Setters
    public Map<Integer, Double> getPrecosAtuais() {
        return precosAtuais;
    }

    public void setPrecosAtuais(Map<Integer, Double> precosAtuais) {
        this.precosAtuais = precosAtuais;
    }

    public List<Map<Integer, Double>> getHistoricoPrecos() {
        return historicoPrecos;
    }

    public void setHistoricoPrecos(List<Map<Integer, Double>> historicoPrecos) {
        this.historicoPrecos = historicoPrecos;
    }

    public List<LocalDateTime> getTimestampsHistorico() {
        return timestampsHistorico;
    }

    public void setTimestampsHistorico(List<LocalDateTime> timestampsHistorico) {
        this.timestampsHistorico = timestampsHistorico;
    }

    public Map<Integer, Double> getVolumeNegociacao24h() {
        return volumeNegociacao24h;
    }

    public void setVolumeNegociacao24h(Map<Integer, Double> volumeNegociacao24h) {
        this.volumeNegociacao24h = volumeNegociacao24h;
    }

    public Map<String, Double> getIndicadoresMercado() {
        return indicadoresMercado;
    }

    public void setIndicadoresMercado(Map<String, Double> indicadoresMercado) {
        this.indicadoresMercado = indicadoresMercado;
    }

    public List<String> getEventos() {
        return eventos;
    }

    public void setEventos(List<String> eventos) {
        this.eventos = eventos;
    }

    public LocalDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }
}