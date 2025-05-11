package br.com.tiopatinhasexchange.model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um usuário do sistema.
 */
public class Usuario {

    // Atributos
    private int id;
    private String nome;
    private String email;
    private String senha;
    private LocalDate dataNascimento;
    private String telefone;
    private List<Carteira> carteiras;
    private List<Alerta> alertas;
    // private List<EstrategiaAutomatica> estrategias;
    // private List<Simulador> simuladores;

    // Construtores
    public Usuario() {
    }

    public Usuario(int id, String nome, String email, String senha, LocalDate dataNascimento, String telefone) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.carteiras = new ArrayList<>();
        this.alertas = new ArrayList<>();
        // this.estrategias = new ArrayList<>();
        // this.simuladores = new ArrayList<>();
    }

    // Métodos
    /**
     * Realiza o login do usuário
     * @param email Email do usuário
     * @param senha Senha do usuário
     * @return true se as credenciais estiverem corretas
     */
    public boolean login(String email, String senha) {
        return this.email.equals(email) && this.senha.equals(senha);
    }
    
    /**
     * Adiciona uma nova carteira ao usuário
     * @param carteira A carteira a ser adicionada
     */
    public void adicionarCarteira(Carteira carteira) {
        if (this.carteiras == null) {
            this.carteiras = new ArrayList<>();
        }
        this.carteiras.add(carteira);
    }
    
    /**
     * Adiciona um alerta para o usuário
     * @param alerta Objeto Alerta a ser adicionado
     */
    public void adicionarAlerta(Alerta alerta) {
        if (this.alertas == null) {
            this.alertas = new ArrayList<>();
        }
        this.alertas.add(alerta);
    }
    
    // /**
    //  * Adiciona uma estrategia para o usuário
    //  * @param estrategia Objeto EstrategiaAutomatica a ser adicionado
    //  */
    // public void adicionarEstrategiaAutomatica(EstrategiaAutomatica estrategia) {
    //     if (this.estrategias == null) {
    //         this.estrategias = new ArrayList<>();
    //     }
    //     this.estrategias.add(estrategia);
    // }
    
    // /**
    //  * Adiciona um simulador para o usuário
    //  * @param simulador Objeto Simulador a ser adicionado
    //  */
    // public void adicionarSimulador(Simulador simulador) {
    //     if (this.simuladores == null) {
    //         this.simuladores = new ArrayList<>();
    //     }
    //     this.simuladores.add(simulador);
    // }
    
    /**
     * Visualiza o dashboard do usuário com informações resumidas
     */
    public void visualizarDashboard() {
        System.out.println("=== Dashboard do Usuário ===");
        System.out.println("ID: " + this.id);
        System.out.println("Nome: " + this.nome);
        System.out.println("Email: " + this.email);
        System.out.println("Número de Carteiras: " + 
                (this.carteiras != null ? this.carteiras.size() : 0));
        System.out.println("Alertas: " + 
                (this.alertas != null ? this.alertas.size() : 0));
        // System.out.println("Estratégias Automáticas: " + 
        //         (this.estrategias != null ? this.estrategias.size() : 0));
        // System.out.println("Simuladores: " + 
        //         (this.simuladores != null ? this.simuladores.size() : 0));
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Carteira> getCarteiras() {
        return carteiras;
    }

    public void setCarteiras(List<Carteira> carteiras) {
        this.carteiras = carteiras;
    }

    public List<Alerta> getAlertas() {
        return alertas;
    }

    public void setAlertas(List<Alerta> alertas) {
        this.alertas = alertas;
    }

    // public List<Estrategia> getEstrategias() {
    //     return estrategias;
    // }

    // public void setEstrategias(List<Estrategias> estrategias) {
    //     this.estrategias = estrategias;
    // }

    // public List<Simulador> getSimuladores() {
    //     return simuladores;
    // }

    // public void setSimuladores(List<Simulador> simuladores) {
    //     this.simuladores = simuladores;
    // }
}