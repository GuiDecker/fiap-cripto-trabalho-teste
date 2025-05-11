package br.com.tiopatinhasexchange.model;
/*
 * Classe que representa um alerta para o usuário sobre eventos importantes,
 * como variações bruscas de preço, execução de estratégias automáticas, etc.
*/
public abstract class Alerta {

    // Atributos
    private int id;
    private String tipo; // Tipo de alerta: VOLATILIDADE, ESTRATEGIA, SEGURANCA, INFORMATIVO
    private String titulo;
    private String conteudo;
    private String prioridade; // ALTA, MEDIA, BAIXA

    // Construtores
    public Alerta() {
    }
    
    public Alerta(String tipo, String titulo, String conteudo) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.conteudo = conteudo;
        
        // Define prioridade com base no tipo
        if (tipo.equals("SEGURANCA") || tipo.equals("VOLATILIDADE")) {
            this.prioridade = "ALTA";
        } else if (tipo.equals("ESTRATEGIA")) {
            this.prioridade = "MEDIA";
        } else {
            this.prioridade = "BAIXA";
        }
    }

    // Métodos
    
    /**
     * Exibe os detalhes do alerta
    */
    public void exibirDetalhes() {
        System.out.println("=== Alerta ===");
        System.out.println("ID: " + this.id);
        System.out.println("Tipo: " + this.tipo);
        System.out.println("Prioridade: " + this.prioridade);
        System.out.println("Título: " + this.titulo);
        System.out.println("Conteúdo: " + this.conteudo);
        // System.out.println("==============");
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }
}