import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe principal para demonstração do sistema
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("=== Tio Patinhas Exchange - Sistema de Investimento em Criptoativos ===");
        
        // Criar um usuário
        Usuario usuario = new Usuario(1, "Tio Patinhas", "patinhas@email.com", "hash_senha123", 
                LocalDate.of(1947, 1, 1), "+1-555-MONEY", true);
        
        // Criar criptoativos
        Criptoativo bitcoin = new Criptoativo(1, "Bitcoin", "BTC", "Criptomoeda", 50000.0, 19000000);
        Criptoativo ethereum = new Criptoativo(2, "Ethereum", "ETH", "Criptomoeda", 2500.0, 120000000);
        
        // Criar carteira para o usuário
        Carteira carteira = new Carteira(1, usuario.getId(), "Hot Wallet");
        usuario.getCarteiras().add(carteira);
        
        // Depositar saldo na carteira
        carteira.depositar(100000.0);
        
        // Comprar criptoativos
        System.out.println("\n--- Realizando compras ---");
        carteira.comprarAtivo(bitcoin, 0.5, 50000.0);
        carteira.comprarAtivo(ethereum, 10.0, 2500.0);
        
        // Criar mercado
        Mercado mercado = new Mercado();
        Map<Integer, Double> precos = new HashMap<>();
        precos.put(bitcoin.getId(), 52000.0); // Bitcoin subiu
        precos.put(ethereum.getId(), 2400.0); // Ethereum caiu
        mercado.atualizarPrecos(precos);
        
        // Criar alerta de volatilidade
        System.out.println("\n--- Criando alertas ---");
        Alerta alerta = Alerta.criarAlertaVolatilidade(usuario.getId(), bitcoin.getId(), 4.0);
        usuario.adicionarAlerta(alerta);
        alerta.exibirDetalhes();
        
        // Configurar estratégia automática
        System.out.println("\n--- Configurando estratégia automática ---");
        EstrategiaAutomatica estrategia = new EstrategiaAutomatica(
            usuario.getId(), ethereum.getId(), "PRECO_ABAIXO", "COMPRAR", 2300.0, 5.0, carteira.getId()
        );
        usuario.configurarEstrategia(estrategia);
        estrategia.exibirDetalhes();
        
        // Criar simulação
        System.out.println("\n--- Iniciando simulação ---");
        Simulador simulador = new Simulador(usuario.getId(), 50000.0);
        simulador.simularCompra(bitcoin.getId(), 0.2, 50000.0);
        simulador.exibirResumoSimulacao();
        
        // Criar módulo educacional
        System.out.println("\n--- Módulo Educacional ---");
        ModuloEducacional artigo = new ModuloEducacional(
            "ARTIGO", "Introdução ao Bitcoin", "Bitcoin é uma criptomoeda descentralizada...", "INICIANTE"
        );
        artigo.adicionarTag("Bitcoin");
        artigo.adicionarTag("Blockchain");
        artigo.registrarVisualizacao();
        artigo.exibirConteudo();
        
        // Dashboard do usuário
        System.out.println("\n--- Dashboard do Usuário ---");
        usuario.visualizarDashboard();
        
        // Resumo da carteira
        System.out.println("\n--- Resumo da Carteira ---");
        carteira.exibirResumo();
        
        // Valor total da carteira (com preços atualizados)
        double valorTotal = carteira.calcularValorTotal(mercado);
        System.out.println("Valor total da carteira (preços atualizados): " + valorTotal);
        
        System.out.println("\n=== Fim da Demonstração ===");
    }
}
