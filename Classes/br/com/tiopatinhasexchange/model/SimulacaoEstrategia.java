package br.com.tiopatinhasexchange.model;
/*
 * Classe que representa uma simulação de alguma estratégia em algum simulador.
*/

public class SimulacaoEstrategia {
    // Atributos
    private int id;
    private int idSimulador; // Simulador em que ocorrerá a simulação
    private int idEstrategiaAutomatica; // Estratégia que será simulada


    // Construtores
    public SimulacaoEstrategia() {
    }

    public SimulacaoEstrategia(int idEstrategiaAutomatica, int idSimulador) {
        this.idEstrategiaAutomatica = idEstrategiaAutomatica;
        this.idSimulador = idSimulador;
    }

    /**
     * Exibe os detalhes da simulação da estratégia
    */

    public void exibirDetalhes() {
        System.out.println("=== Simulação de Estratégia ===");
        System.out.println("ID: " + this.id);
        System.out.println("Estratégia: " + this.idEstrategiaAutomatica);
        System.out.println("Simulador: ");
        System.out.println("==============");
    }
}