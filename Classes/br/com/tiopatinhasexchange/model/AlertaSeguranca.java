package br.com.tiopatinhasexchange.model;
/*
 * Classe que representa um alerta de segurança destinado a algum usuário.
*/

public class AlertaSeguranca extends Alerta {
    
    // Atributos
    private int idUsuario; // Usuário destinatário
    
    // Métodos

    // Construtores
    public AlertaSeguranca() {
        super();
        this.setTipo("SEGURANCA");
        this.setPrioridade("ALTA");
    }

    public AlertaSeguranca(String titulo, String conteudo, int idUsuario) {
        super("SEGURANCA", titulo, conteudo);
        this.idUsuario = idUsuario;
    }

    // Getters e Setters
    
    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Cria um alerta de segurança
     * @param idUsuario ID do usuário
     * @param mensagem Mensagem de segurança
     * @return Alerta de segurança criado
     */
    public static AlertaSeguranca criarAlertaSeguranca(int idUsuario, String mensagem) {
        AlertaSeguranca alerta = new AlertaSeguranca();
        alerta.setIdUsuario(idUsuario);
        alerta.setTipo("SEGURANCA");
        alerta.setPrioridade("ALTA");
        
        String titulo = "Alerta de Segurança";
        
        alerta.setTitulo(titulo);
        alerta.setConteudo(mensagem);
        
        return alerta;
    }

    @Override
    public void exibirDetalhes() {
        super.exibirDetalhes();
        System.out.println("Usuário: " + this.idUsuario);
        System.out.println("==============");
    }
}
