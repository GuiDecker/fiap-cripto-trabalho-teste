package br.com.tiopatinhasexchange.model;
import java.time.LocalDateTime;

/*
 * Classe que representa um alerta personalizado pelo usuário sobre volatilidade de algum criptoativo.
*/

public class AlertaPersonalizado extends Alerta {

    // Atributos
    private int idUsuario; // Usuário que configurará o alerta
    private int idCriptoativo; // Criptoativo sobre o qual será o alerta
    private LocalDateTime dataHoraCriacao;
    private String condicao;
    private double valorReferencia;

    // Métodos

    // Construtores

    public AlertaPersonalizado() {
        super();
        this.setTipo("VOLATILIDADE");
        this.setPrioridade("ALTA");
    }

    public AlertaPersonalizado(String titulo, String conteudo, int idUsuario, int idCriptoativo, String condicao,
            double valorReferencia) {
        super("VOLATILIDADE", titulo, conteudo);
        this.idUsuario = idUsuario;
        this.idCriptoativo = idCriptoativo;
        this.dataHoraCriacao = LocalDateTime.now();
        this.condicao = condicao;
        this.valorReferencia = valorReferencia;
    }

    // Getters e Setters

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

    public LocalDateTime getDataHoraCriacao() {
        return dataHoraCriacao;
    }

    public void setDataHoraCriacao(LocalDateTime dataHoraCriacao) {
        this.dataHoraCriacao = dataHoraCriacao;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    public double getValorReferencia() {
        return valorReferencia;
    }

    public void setValorReferencia(double valorReferencia) {
        this.valorReferencia = valorReferencia;
    }

    /**
     * Cria um alerta de volatilidade
     * 
     * @param idUsuario     ID do usuário
     * @param idCriptoativo ID do criptoativo
     * @param variacao      Variação percentual do preço
     * @return Alerta de volatilidade criado
     */
    public static Alerta criarAlertaVolatilidade(int idUsuario, int idCriptoativo, double variacao) {
        AlertaPersonalizado alerta = new AlertaPersonalizado();
        alerta.setIdUsuario(idUsuario);
        alerta.setIdCriptoativo(idCriptoativo);
        alerta.setTipo("VOLATILIDADE");
        alerta.setPrioridade("ALTA");

        String direcao = variacao > 0 ? "aumento" : "queda";
        String titulo = "Variação brusca de preço detectada!";
        String conteudo = "O criptoativo ID:" + idCriptoativo + " sofreu " + direcao +
                " de " + Math.abs(variacao) + "% nas últimas 24 horas.";

        alerta.setTitulo(titulo);
        alerta.setConteudo(conteudo);

        return alerta;
    }

    // Exibir detalhes
    /**
     * Exibe os detalhes do alerta personalizado
     */
    @Override
    public void exibirDetalhes() {
        super.exibirDetalhes();
        System.out.println("Usuário: " + this.idUsuario);
        System.out.println("Criptoativo: " + this.idCriptoativo);
        System.out.println("Data e Hora da Criação: " + this.dataHoraCriacao);
        System.out.println("Condição: " + this.condicao);
        System.out.println("Valor de Referência: " + this.valorReferencia);
        System.out.println("==============");
    }
}