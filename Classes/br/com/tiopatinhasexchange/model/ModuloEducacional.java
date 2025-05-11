package br.com.tiopatinhasexchange.model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelo módulo educacional interativo,
 * oferecendo conteúdo para aprendizado sobre criptoativos e mercado financeiro.
 */
public class ModuloEducacional {
    
    // Atributos
    private int id;
    private String tipo; // ARTIGO, VIDEO, CURSO
    private String titulo;
    private String conteudo;
    private String nivel; // INICIANTE, INTERMEDIARIO, AVANCADO
    private LocalDateTime dataPublicacao;
    private int visualizacoes;
    private List<String> comentarios;
    private double avaliacaoMedia; // Média de avaliações (1-5)
    private int autor; // ID do autor (pode ser um admin ou especialista)

    // Construtores
    public ModuloEducacional() {
        this.comentarios = new ArrayList<>();
        this.dataPublicacao = LocalDateTime.now();
        this.visualizacoes = 0;
        this.avaliacaoMedia = 0.0;
    }
    
    public ModuloEducacional(String tipo, String titulo, String conteudo, String nivel) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.nivel = nivel;
        this.comentarios = new ArrayList<>();
        this.dataPublicacao = LocalDateTime.now();
        this.visualizacoes = 0;
        this.avaliacaoMedia = 0.0;
    }

    // Métodos
    /**
     * Adiciona um comentário ao conteúdo educacional
     * @param comentario Comentário a ser adicionado
     * @return true se o comentário foi adicionado com sucesso
     */
    public boolean adicionarComentario(String comentario) {
        if (comentario == null || comentario.isEmpty()) {
            return false;
        }
        
        return this.comentarios.add(comentario);
    }
    
    /**
     * Registra uma visualização do conteúdo
     */
    public void registrarVisualizacao() {
        this.visualizacoes++;
    }
    
    /**
     * Adiciona uma avaliação ao conteúdo
     * @param avaliacao Valor da avaliação (1-5)
     * @return true se a avaliação foi registrada com sucesso
     * @throws IllegalArgumentException se a avaliação estiver fora do intervalo válido
     */
    public boolean avaliar(int avaliacao) {
        if (avaliacao < 1 || avaliacao > 5) {
            throw new IllegalArgumentException("A avaliação deve estar entre 1 e 5");
        }
        
        // Em uma implementação real, seria mantido um histórico de avaliações
        // Aqui, apenas atualizamos a média
        this.avaliacaoMedia = (this.avaliacaoMedia * this.visualizacoes + avaliacao) / (this.visualizacoes + 1);
        return true;
    }
    
    /**
     * Verifica se o conteúdo é adequado para um determinado nível de conhecimento
     * @param nivelUsuario Nível do usuário
     * @return true se o conteúdo é adequado
     */
    public boolean verificarNivelAdequado(String nivelUsuario) {
        if (nivelUsuario.equals("INICIANTE")) {
            return this.nivel.equals("INICIANTE");
        } else if (nivelUsuario.equals("INTERMEDIARIO")) {
            return this.nivel.equals("INICIANTE") || this.nivel.equals("INTERMEDIARIO");
        } else if (nivelUsuario.equals("AVANCADO")) {
            return true; // Avançado pode acessar qualquer nível
        }
        
        return false;
    }
    
    /**
     * Exibe o conteúdo educacional
     */
    public void exibirConteudo() {
        System.out.println("=== " + this.titulo + " ===");
        System.out.println("Tipo: " + this.tipo);
        System.out.println("Nível: " + this.nivel);
        System.out.println("Publicado em: " + this.dataPublicacao);
        System.out.println("Visualizações: " + this.visualizacoes);
        System.out.println("Avaliação média: " + String.format("%.1f", this.avaliacaoMedia));
        
        System.out.println("\nConteúdo:");
        System.out.println(this.conteudo);
        
        System.out.println("\nComentários (" + this.comentarios.size() + "):");
        for (String comentario : this.comentarios) {
            System.out.println("- " + comentario);
        }
        
        System.out.println("=====================");
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

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public LocalDateTime getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDateTime dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public int getVisualizacoes() {
        return visualizacoes;
    }

    public void setVisualizacoes(int visualizacoes) {
        this.visualizacoes = visualizacoes;
    }

    public List<String> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<String> comentarios) {
        this.comentarios = comentarios;
    }

    public double getAvaliacaoMedia() {
        return avaliacaoMedia;
    }

    public void setAvaliacaoMedia(double avaliacaoMedia) {
        this.avaliacaoMedia = avaliacaoMedia;
    }

    public int getAutor() {
        return autor;
    }

    public void setAutor(int autor) {
        this.autor = autor;
    }
}
