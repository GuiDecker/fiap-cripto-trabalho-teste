package br.com.tiopatinhasexchange.model;
/*
 * Classe que representa um alerta de confirmação da execução de uma estratégia.
*/

public class AlertaExecucaoEstrategia extends Alerta {
    
    // Atributos
    private int idUsuario; // Usuário que executou a estratégia
    private int idExecucaoEstrategia; // Execução da estratégia
    
    // Métodos

    // Construtores
    public AlertaExecucaoEstrategia() {
        super();
        this.setTipo("ESTRATEGIA");
        this.setPrioridade("MEDIA");
    }

    public AlertaExecucaoEstrategia(String titulo, String conteudo, int idUsuario, int idExecucaoEstrategia) {
        super("ESTRATEGIA", titulo, conteudo);
        this.idUsuario = idUsuario;
        this.idExecucaoEstrategia = idExecucaoEstrategia;
    }

    // Getters e Setters
    
    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    public int getIdExecucaoEstrategia() {
        return idExecucaoEstrategia;
    }
    public void setIdExecucaoEstrategia(int idExecucaoEstrategia) {
        this.idExecucaoEstrategia = idExecucaoEstrategia;
    }

    /**
     * Cria um alerta de execução de estratégia
     * @param idExecucaoEstrategia ID da execução da estratégia
     * @return Alerta de estratégia criado
     */
    public static AlertaExecucaoEstrategia criarAlertaExecucaoEstrategia( 
        int idUsuario, 
        int idCriptoativo, 
        String acao, 
        double quantidade,
        int idExecucaoEstrategia
        ) {

        String titulo = "Estratégia automática executada";
        String conteudo = "A estratégia automática executou uma operação de " + acao + " de " + quantidade + " unidades do criptoativo ID:" + idCriptoativo + ".";
        
        AlertaExecucaoEstrategia alerta = new AlertaExecucaoEstrategia(titulo, conteudo, idUsuario, idExecucaoEstrategia);
        
        return alerta;
    }

    /**
     * Exibe os detalhes do alerta informativo
    */
    @Override
    public void exibirDetalhes() {
        super.exibirDetalhes();
        System.out.println("Execução da Estratégia: " + this.idExecucaoEstrategia);
        System.out.println("Usuário: " + this.idUsuario);
        System.out.println("==============");
    }
    

}