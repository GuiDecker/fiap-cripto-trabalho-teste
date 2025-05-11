package br.com.tiopatinhasexchange.model;
import java.time.LocalDateTime;

/*
 * Classe que representa uma execução de alguma estratégia.
*/

public class ExecucaoEstrategia {
    
    // Atributos
    private int id;
    private int idEstrategiaAutomatica; // EstrategiaExecutada
    private int idCarteira; // Carteira em que foi executada a estratégia
    private LocalDateTime dataHoraExecucao;

    // Métodos

    // Construtores

    public ExecucaoEstrategia() {
        this.dataHoraExecucao = LocalDateTime.now();
    }
    
    public ExecucaoEstrategia(int idEstrategiaAutomatica, int idCarteira) {
        this.idEstrategiaAutomatica = idEstrategiaAutomatica;
        this.idCarteira = idCarteira;
        this.dataHoraExecucao = LocalDateTime.now();
    }

    // Getters e Setters

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getIdEstrategiaAutomatica() {
        return idEstrategiaAutomatica;
    }
    public void setIdEstrategiaAutomatica(int idEstrategiaAutomatica) {
        this.idEstrategiaAutomatica = idEstrategiaAutomatica;
    }
    public int getIdCarteira() {
        return idCarteira;
    }
    public void setIdCarteira(int idCarteira) {
        this.idCarteira = idCarteira;
    }
    public LocalDateTime getDataHoraExecucao() {
        return dataHoraExecucao;
    }
    public void setDataHoraExecucao(LocalDateTime dataHoraExecucao) {
        this.dataHoraExecucao = dataHoraExecucao;
    }

    // Exibir detalhes
    /**
     * Exibe os detalhes do alerta personalizado
     */
    public void exibirDetalhes() {
        System.out.println("=== Execução de Estratégia ===");
        System.out.println("ID: " + this.id);
        System.out.println("Estratégia Automática: " + this.idEstrategiaAutomatica);
        System.out.println("Carteira: " + this.idCarteira);
        System.out.println("Data e Hora da Execução: " + this.dataHoraExecucao);
        System.out.println("==============");
    }
}
