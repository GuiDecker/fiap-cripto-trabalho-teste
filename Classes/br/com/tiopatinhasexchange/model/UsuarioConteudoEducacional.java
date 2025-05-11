package br.com.tiopatinhasexchange.model;
import java.time.LocalDateTime;

/**
 * Classe associativa que representa o relacionamento entre Usuário e ModuloEducacional.
 * Armazena informações sobre a inscrição e progresso de um usuário em um módulo educacional.
 */
public class UsuarioConteudoEducacional {
    
    // Atributos - Chaves Primária e Estrangeiras
    private int id;                    // Chave primária
    private int idUsuario;             // Chave estrangeira referenciando Usuario
    private int idModuloEducacional;   // Chave estrangeira referenciando ModuloEducacional
    
    // Atributos de relacionamento
    private LocalDateTime dataInscricao;
    private double progresso;            // Percentual de conclusão (0-100%)
    private int avaliacao;               // Avaliação do usuário (1-5 estrelas)
    private boolean concluido;
    private LocalDateTime dataConclusao;
    private String comentarioUsuario;
    
    // Construtores
    public UsuarioConteudoEducacional() {
        this.dataInscricao = LocalDateTime.now();
        this.progresso = 0.0;
        this.concluido = false;
    }
    
    public UsuarioConteudoEducacional(int idUsuario, int idModuloEducacional) {
        this.idUsuario = idUsuario;
        this.idModuloEducacional = idModuloEducacional;
        this.dataInscricao = LocalDateTime.now();
        this.progresso = 0.0;
        this.concluido = false;
    }
    
    // Métodos
    /**
     * Atualiza o progresso do usuário no módulo educacional
     * @param novoProgresso Novo percentual de progresso (0-100%)
     * @return true se o progresso foi atualizado com sucesso
     */
    public boolean atualizarProgresso(double novoProgresso) {
        if (novoProgresso < 0 || novoProgresso > 100) {
            throw new IllegalArgumentException("O progresso deve estar entre 0 e 100%");
        }
        
        this.progresso = novoProgresso;
        
        // Se chegou a 100%, marca como concluído
        if (novoProgresso >= 100.0 && !this.concluido) {
            this.concluido = true;
            this.dataConclusao = LocalDateTime.now();
        }
        
        return true;
    }
    
    /**
     * Método de sobrecarga (polimorfismo estático) que atualiza o progresso com passos
     * @param passosConcluidos Número de passos concluídos
     * @param totalPassos Total de passos do módulo
     * @return true se o progresso foi atualizado com sucesso
     */
    public boolean atualizarProgresso(int passosConcluidos, int totalPassos) {
        if (passosConcluidos < 0 || totalPassos <= 0 || passosConcluidos > totalPassos) {
            throw new IllegalArgumentException("Passos inválidos");
        }
        
        double percentual = ((double) passosConcluidos / totalPassos) * 100.0;
        return atualizarProgresso(percentual);
    }
    
    /**
     * Registra a avaliação do usuário para o módulo educacional
     * @param avaliacao Valor da avaliação (1-5)
     * @param comentario Comentário opcional do usuário
     */
    public void avaliar(int avaliacao, String comentario) {
        if (avaliacao < 1 || avaliacao > 5) {
            throw new IllegalArgumentException("A avaliação deve estar entre 1 e 5");
        }
        
        this.avaliacao = avaliacao;
        this.comentarioUsuario = comentario;
    }
    
    /**
     * Exibe os detalhes da relação usuário-conteúdo
     */
    public void exibirDetalhes() {
        System.out.println("=== Progresso no Módulo Educacional ===");
        System.out.println("ID Usuário: " + this.idUsuario);
        System.out.println("ID Módulo: " + this.idModuloEducacional);
        System.out.println("Data de Inscrição: " + this.dataInscricao);
        System.out.println("Progresso: " + String.format("%.1f%%", this.progresso));
        System.out.println("Status: " + (this.concluido ? "Concluído" : "Em andamento"));
        
        if (this.concluido) {
            System.out.println("Data de Conclusão: " + this.dataConclusao);
        }
        
        if (this.avaliacao > 0) {
            System.out.println("Avaliação: " + this.avaliacao + "/5");
            if (this.comentarioUsuario != null && !this.comentarioUsuario.isEmpty()) {
                System.out.println("Comentário: " + this.comentarioUsuario);
            }
        }
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdModuloEducacional() {
        return idModuloEducacional;
    }

    public void setIdModuloEducacional(int idModuloEducacional) {
        this.idModuloEducacional = idModuloEducacional;
    }

    public LocalDateTime getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(LocalDateTime dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public double getProgresso() {
        return progresso;
    }

    public void setProgresso(double progresso) {
        this.progresso = progresso;
    }

    public int getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public String getComentarioUsuario() {
        return comentarioUsuario;
    }

    public void setComentarioUsuario(String comentarioUsuario) {
        this.comentarioUsuario = comentarioUsuario;
    }
}