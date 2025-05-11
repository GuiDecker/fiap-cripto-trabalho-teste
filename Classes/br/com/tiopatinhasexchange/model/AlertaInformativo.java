package br.com.tiopatinhasexchange.model;
/*
 * Classe que representa um alerta informativo para o usuário sobre assuntos de baixa importância.
*/

public class AlertaInformativo extends Alerta {

    // Atributos
    // String categoria;

    // Construtores
    public AlertaInformativo() {
        super();
        this.setTipo("INFORMATIVO");
        this.setPrioridade("BAIXA");

    }
    
    public AlertaInformativo(String tipo, String titulo, String conteudo) {
        super(tipo, titulo, conteudo);
    }

    // Métodos
    
    /**
     * Cria um alerta informativo
     * @param mensagem Mensagem informativa
     * @return Alerta criado
     */
    public static AlertaInformativo criarAlertaInformativo(String titulo, String mensagem) {
        AlertaInformativo alerta = new AlertaInformativo();
        alerta.setTipo("INFORMATIVO");
        alerta.setPrioridade("BAIXA");

        alerta.setTitulo(titulo);
        alerta.setConteudo(mensagem);
        
        return alerta;
    }

    /**
     * Exibe os detalhes do alerta informativo
    */
    @Override
    public void exibirDetalhes() {
        super.exibirDetalhes();
        System.out.println("==============");
    }
}