import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Locale;

import br.com.tiopatinhasexchange.exceptions.ValorInvalidoException;
import br.com.tiopatinhasexchange.model.*;

public class Main {

    public static void main(String[] args) {

        // Instanciar o Scanner para leitura de dados
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US); // Considera "." como separador decimal

        // Instâncias das Classes

        // Instanciar usuário
        Usuario usuario = new Usuario(
                1,
                "Ada Byron Lovelace",
                "adalo@devmail.com",
                "first_programmer",
                LocalDate.of(2000, 3, 12),
                "11912345678");
        // Métodos -> login, adicionar carteira, adicionar alerta, visualizar dashboard

        // Instanciar Carteira
        Carteira carteira = new Carteira(1, usuario.getId());

        // Instanciar criptoativos
        Criptoativo btc = new Criptoativo(1, "Bitcoin", "BTC", 581757.84);
        Criptoativo eth = new Criptoativo(2, "Ethereum", "ETH", 12404.99);

        // Instanciar Mercado
        Mercado mercado = new Mercado();

        // Atualizar preços dos ativos do mercado
        mercado.atualizarPrecos(java.util.Map.of(btc.getId(), btc.getPrecoAtual()));
        mercado.atualizarPrecos(java.util.Map.of(eth.getId(), eth.getPrecoAtual()));

        // Adicionar carteira à conta do usuário
        usuario.adicionarCarteira(carteira);

        // Adicionar saldo à carteira
        carteira.depositar(26500.0);

        // Lista de estrategias
        List<EstrategiaAutomatica> estrategias = new ArrayList<>();
        
        // Lista de módulos educacionais
        List<ModuloEducacional> modulosEducacionais = new ArrayList<>();
        
        // Criar alguns módulos educacionais de exemplo
        ModuloEducacional modulo1 = new ModuloEducacional("ARTIGO", "Introdução às Criptomoedas", 
                "Este artigo introduz os conceitos básicos de criptomoedas e como elas funcionam.", "INICIANTE");
        modulo1.setId(1);
        modulo1.setAutor(1);
        
        ModuloEducacional modulo2 = new ModuloEducacional("VIDEO", "Trading Avançado", 
                "Este vídeo mostra técnicas avançadas de trading de criptoativos.", "AVANCADO");
        modulo2.setId(2);
        modulo2.setAutor(1);
        
        modulosEducacionais.add(modulo1);
        modulosEducacionais.add(modulo2);

        // Variáveis para operações

        // Armazena a opção
        int opcao = -1;

        // Armazena o simulador
        Simulador simulador = null;

        do {
            // Exibe o menu
            System.out.println("\n===== TIO PATINHAS EXCHANGE =====");
            System.out.println("0 - Sair");
            System.out.println("1 - Fazer login");
            System.out.println("2 - Fazer cadastro");
            System.out.println("3 - Login Administrativo");
            System.out.println("=================================\n");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Você deve digitar um número válido.");
                scanner.nextLine(); // Limpar o buffer
                continue;
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                scanner.nextLine(); // Limpar o buffer
                continue;
            }

            switch (opcao) {
                case 1:
                    String email, senha;
                    boolean resultadoLogin;
                    scanner.nextLine();
                    System.out.println("Digite seu email: ");
                    email = scanner.nextLine();
                    System.out.println("Digite sua senha: ");
                    senha = scanner.nextLine();
                    resultadoLogin = usuario.login(email, senha);
                    if (resultadoLogin) {
                        // Reinicia a opção
                        opcao = -1;

                        do {
                            // Exibe o menu
                            System.out.println("\n======== BEM-VINDO " + usuario.getNome() + " ========");
                            System.out.println("\n===== TIO PATINHAS EXCHANGE =====");
                            System.out.println("0 - Consultar meus dados");
                            System.out.println("1 - Consultar minhas carteiras");
                            System.out.println();
                            System.out.println("2 - Comprar/vender ativos");
                            System.out.println("3 - Consultar mercado");
                            System.out.println("4 - Consultar transações");
                            System.out.println();
                            System.out.println("5 - Criar alertas de volatilidade");
                            System.out.println("6 - Consultar alertas criados");
                            System.out.println("7 - Consultar notificações");
                            System.out.println();        
                            System.out.println("8 - Criar estratégias automáticas");
                            System.out.println("9 - Consultar estrategias criadas");
                            System.out.println("10 - Consultar execuções de estratégias");
                            System.out.println();        
                            System.out.println("11 - Ver conteúdos educacionais");
                            System.out.println("12 - Simular estratégias");
                            System.out.println("13 - Simular ativos");
                            System.out.println("14 - Consultar simulações");
                            System.out.println();
                            System.out.println("15 - Depositar fundos");
                            System.out.println("16 - Sacar fundos");
                            System.out.println();
                            System.out.println("99 - Logout");
                            System.out.println("====================================\n");
                            System.out.print("Escolha uma opção: ");

                            try {
                                opcao = scanner.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println("Você deve digitar um número válido.");
                                scanner.nextLine(); // Limpar o buffer
                                continue;
                            } catch (Exception e) {
                                System.out.println("Erro: " + e.getMessage());
                                scanner.nextLine(); // Limpar o buffer
                                continue;
                            }

                            switch (opcao) {
                                case 0:
                                    usuario.visualizarDashboard();
                                    break;
                                case 1:
                                    carteira.exibirResumo();
                                    break;
                                case 2:
                                    System.out.println("Deseja comprar (1) ou vender (2) ativos?");
                                    int escolhaOperacao = scanner.nextInt();
                                    
                                    if (escolhaOperacao == 1) {
                                        // Código para comprar ativos
                                        Criptoativo ativoCompra = null;
                                        int idAtivoCompra = -1;
                                        double quantidadeCompra = 0.0;
                                        double valorCompra = 0.0;

                                        // Exibir ativos
                                        System.out.println("====== Ativos Disponíveis ======");
                                        System.out.println("1. Bitcoin (BTC)");
                                        System.out.println(btc.getNome().toUpperCase());
                                        btc.exibirInfo();
                                        System.out.println("----------------------------------");
                                        System.out.println("2. Ethereum (ETH)");
                                        System.out.println(eth.getNome().toUpperCase());
                                        eth.exibirInfo();
                                        System.out.println("=================================\n");
                                        System.out.print("Escolha o ativo (1 ou 2): ");

                                        try {
                                            // Solicitar dados
                                            idAtivoCompra = scanner.nextInt();
                                            System.out.print("Digite a quantidade: ");
                                            quantidadeCompra = scanner.nextDouble();
                                        } catch (InputMismatchException e) {
                                            System.out.println("Entrada inválida!");
                                            continue;
                                        } catch (Exception e) {
                                            System.out.println("Erro: " + e.getMessage());
                                            continue;
                                        }
                                        finally {
                                            scanner.nextLine(); // Limpar o buffer
                                        }

                                        // Comprar ativos
                                        if (idAtivoCompra == 1) {
                                            ativoCompra = btc;
                                            valorCompra = btc.getPrecoAtual() * quantidadeCompra;
                                        } else if (idAtivoCompra == 2) {
                                            ativoCompra = eth;
                                            valorCompra = eth.getPrecoAtual() * quantidadeCompra;

                                        } else {
                                            System.out.println("Ativo inválido!");
                                            break;
                                        }
                                        System.out.println("Valor total da compra: " + valorCompra + ". Deseja confirmar? (S/N)");
                                                String confirmaCompra = scanner.nextLine();
                                                if (confirmaCompra.equalsIgnoreCase("S")) {
                                                    boolean sucessoCompra = carteira.comprarAtivo(ativoCompra, quantidadeCompra, ativoCompra.getPrecoAtual());
                                        if (sucessoCompra) {
                                            System.out.println("Compra realizada com sucesso!");
                                        } else {
                                            System.out.println("Falha na compra. Verifique seu saldo.");
                                        }                                                   
                                                }
                                                else {
                                                    System.out.println("Operação cancelada.");
                                                }
                                        
                                    } else if (escolhaOperacao == 2) {
                                        // Código para vender ativos
                                        Criptoativo ativoVenda = null;
                                        int idAtivoVenda = -1;
                                        double quantidadeVenda = 0.0;
                                        double precoVenda = 0.0;
                                        double valorVenda = 0.0;

                                        // Exibir ativos
                                        System.out.println("====== Seus Ativos ======");
                                        if (carteira.getPosicoes() != null && carteira.getPosicoes().size() > 0) {
                                            for (java.util.Map.Entry<Integer, Double> entry : carteira.getPosicoes().entrySet()) {
                                                int idAtivo = entry.getKey();
                                                double quantidade = entry.getValue();
                                                String simbolo = idAtivo == 1 ? "BTC" : idAtivo == 2 ? "ETH" : "Desconhecido";
                                                System.out.println(idAtivo + ". " + simbolo + " - Quantidade: " + quantidade);
                                            }
                                        } else {
                                            System.out.println("Você não possui ativos.");
                                            break;
                                        }
                                        System.out.println("=================================\n");
                                        System.out.print("Escolha o ativo pelo ID: ");

                                        try {
                                            // Solicitar dados
                                            idAtivoVenda = scanner.nextInt();
                                            System.out.println("Digite a quantidade a vender: ");
                                            quantidadeVenda = scanner.nextDouble();
                                        } catch (InputMismatchException e) {
                                            System.out.println("Você deve digitar um número válido.");
                                            continue;
                                        } catch (Exception e) {
                                            System.out.println("Erro: " + e.getMessage());
                                            continue;
                                        }
                                        finally {
                                            scanner.nextLine(); // Limpar o buffer
                                        }

                                        // Vender ativos
                                        if (idAtivoVenda == 1) {
                                            ativoVenda = btc;
                                            precoVenda = btc.getPrecoAtual();
                                            valorVenda = btc.getPrecoAtual() * quantidadeVenda;
                                        } else if (idAtivoVenda == 2) {
                                            ativoVenda = eth;
                                            precoVenda = eth.getPrecoAtual();
                                            valorVenda = eth.getPrecoAtual() * quantidadeVenda;
                                        } else {
                                            System.out.println("Selecione uma opção válida.");
                                            break;
                                        }
                                        System.out.println("Valor total da venda: " + valorVenda + ". Deseja confirmar? (S/N)");
                                                String confirmaVenda = scanner.nextLine();
                                                if (confirmaVenda.equalsIgnoreCase("S")) {
                                        boolean sucessoVenda = carteira.venderAtivo(ativoVenda, quantidadeVenda, precoVenda);
                                        if (sucessoVenda) {
                                            System.out.println("Venda realizada com sucesso!");
                                        } else {
                                            System.out.println("Falha na venda. Verifique sua quantidade disponível.");
                                        }
                                        }
                                                else {
                                                    System.out.println("Operação cancelada.");
                                                }
                                    } else {
                                        System.out.println("Opção inválida.");
                                    }
                                    break;
                                case 3:
                                    System.out.println("===== CONSULTA DE MERCADO =====");
                                    System.out.println("Bitcoin (BTC): " + mercado.obterPrecoAtual(btc.getId()));
                                    System.out.println("Ethereum (ETH): " + mercado.obterPrecoAtual(eth.getId()));
                                    System.out.println("=============================");
                                    break;
                                case 4:
                                    // Exibir transações da carteira
                                    if (carteira.getTransacoes() != null && carteira.getTransacoes().size() > 0) {
                                        System.out.println("===== HISTÓRICO DE TRANSAÇÕES =====");
                                        for (Transacao transacao : carteira.getTransacoes()) {
                                            transacao.exibirDetalhes();
                                        }
                                        System.out.println("================================");
                                    } else {
                                        System.out.println("Você não possui transações.");
                                    }
                                    break;
                                case 5:
                                    // Criar Alerta de Volatilidade
                                    scanner.nextLine(); // Limpar buffer
                                    
                                    System.out.println("===== CRIAR ALERTA DE VOLATILIDADE =====");
                                    System.out.println("Escolha o ativo (1-BTC, 2-ETH): ");
                                    int idAtivoAlerta = scanner.nextInt();
                                    scanner.nextLine(); // Limpar buffer
                                    
                                    System.out.println("Condição (> para acima, < para abaixo): ");
                                    String condicaoAlerta = scanner.nextLine();
                                    
                                    System.out.println("Valor de referência: ");
                                    double valorAlerta = scanner.nextDouble();
                                    scanner.nextLine(); // Limpar buffer
                                    
                                    System.out.println("Titulo do alerta: ");
                                    String tituloAlerta = scanner.nextLine();
                                    
                                    System.out.println("Descrição do alerta: ");
                                    String descricaoAlerta = scanner.nextLine();
                                    
                                    Criptoativo ativoAlerta = (idAtivoAlerta == 1) ? btc : eth;
                                    
                                    // Criar alerta personalizado 
                                    AlertaPersonalizado alerta = new AlertaPersonalizado();
                                    alerta.setIdCriptoativo(ativoAlerta.getId());
                                    alerta.setIdUsuario(usuario.getId());
                                    alerta.setCondicao(condicaoAlerta);
                                    alerta.setValorReferencia(valorAlerta);
                                    alerta.setTitulo(tituloAlerta);
                                    alerta.setConteudo(descricaoAlerta);
                                    alerta.setDataHoraCriacao(LocalDateTime.now());
                                    
                                    usuario.adicionarAlerta(alerta);
                                    System.out.println("Alerta criado com sucesso!");
                                    break;
                                case 6:
                                    // Ver Alertas
                                    if (usuario.getAlertas() != null && usuario.getAlertas().size() > 0) {
                                        System.out.println("===== SEUS ALERTAS =====");
                                        for (Alerta a : usuario.getAlertas()) {
                                            a.exibirDetalhes();
                                        }
                                        System.out.println("=======================");
                                    } else {
                                        System.out.println("Você não possui alertas.");
                                    }
                                    break;
                                case 7:
                                    // Ver Notificações (alertas disparados)
                                    System.out.println("Funcionalidade em desenvolvimento.");
                                    break;
                                case 8:
                                    Criptoativo ativoEstrategia = null;

                                    // Exibe opções de ativo
                                    System.out.println("====== SELECIONAR ATIVO ======");
                                    System.out.println("1 - Bitcoin (BTC)");
                                    System.out.println("2 - Ethereum (ETH)");
                                    System.out.print("Digite o ID do ativo (1 ou 2): ");
                                    try {
                                        // Solicita dados
                                        int idAtivoEstrategia = scanner.nextInt();
                                        scanner.nextLine(); // Limpar buffer

                                        if (idAtivoEstrategia == 1) {
                                            ativoEstrategia = btc;
                                        } else if (idAtivoEstrategia == 2) {
                                            ativoEstrategia = eth;
                                        } else {
                                            System.out.println("Ativo inválido!");
                                            break;
                                        }

                                        // Parâmetros da estratégia
                                        System.out.println("Condição (PRECO_ACIMA, PRECO_ABAIXO, VARIACAO_ACIMA, VARIACAO_ABAIXO): ");
                                        String condicao = scanner.nextLine().trim().toUpperCase();

                                        System.out.println("Ação (COMPRAR, VENDER): ");
                                        String acao = scanner.nextLine().trim().toUpperCase();

                                        System.out.println("Valor de referência: ");
                                        double valorReferencia = scanner.nextDouble();

                                        System.out.println("Quantidade: ");
                                        double quantidade = scanner.nextDouble();
                                        scanner.nextLine(); // Limpar buffer

                                        // Criar estratégia
                                        EstrategiaAutomatica estrategia = new EstrategiaAutomatica(
                                                usuario.getId(),
                                                ativoEstrategia.getId(),
                                                condicao,
                                                acao,
                                                valorReferencia,
                                                quantidade,
                                                carteira.getId());

                                        estrategias.add(estrategia);
                                        System.out.println("Estratégia criada para " + ativoEstrategia.getSimbolo());

                                    } catch (InputMismatchException e) {
                                        System.out.println("Erro: Valor numérico inválido!");
                                        scanner.nextLine(); // Limpar buffer em caso de erro
                                    } catch (Exception e) {
                                        System.out.println("Erro inesperado: " + e.getMessage());
                                    }
                                    break;
                                case 9:
                                    // Ver Estratégias Criadas
                                    if (estrategias.size() > 0) {
                                        System.out.println("===== SUAS ESTRATÉGIAS =====");
                                        for (EstrategiaAutomatica estrategiaAtual : estrategias) {
                                            estrategiaAtual.exibirDetalhes();
                                        }
                                        System.out.println("=========================");
                                    } else {
                                        System.out.println("Você não possui estratégias.");
                                    }
                                    break;
                                case 10:
                                    System.out.println("Nenhuma execução de estratégia disponível.");
                                    break;
                                case 11:
                                    // Ver conteúdos educacionais
                                    System.out.println("===== CONTEÚDOS EDUCACIONAIS =====");
                                    for (ModuloEducacional modulo : modulosEducacionais) {
                                        System.out.println(modulo.getId() + ". " + modulo.getTitulo() + " (" + modulo.getTipo() + ") - Nível: " + modulo.getNivel());
                                    }
                                    System.out.println("Digite o ID do conteúdo para visualizar (0 para voltar): ");
                                    int idConteudo = scanner.nextInt();
                                    scanner.nextLine(); // Limpar buffer
                                    
                                    if (idConteudo > 0) {
                                        boolean encontrado = false;
                                        for (ModuloEducacional modulo : modulosEducacionais) {
                                            if (modulo.getId() == idConteudo) {
                                                modulo.exibirConteudo();
                                                modulo.registrarVisualizacao();
                                                encontrado = true;
                                                
                                                System.out.println("Deseja adicionar um comentário? (S/N)");
                                                String resposta = scanner.nextLine();
                                                if (resposta.equalsIgnoreCase("S")) {
                                                    System.out.println("Digite seu comentário:");
                                                    String comentario = scanner.nextLine();
                                                    modulo.adicionarComentario(comentario);
                                                    System.out.println("Comentário adicionado!");
                                                }
                                                
                                                System.out.println("Deseja avaliar este conteúdo? (S/N)");
                                                resposta = scanner.nextLine();
                                                if (resposta.equalsIgnoreCase("S")) {
                                                    System.out.println("Digite sua avaliação (1-5):");
                                                    int avaliacao = scanner.nextInt();
                                                    scanner.nextLine(); // Limpar buffer
                                                    try {
                                                        modulo.avaliar(avaliacao);
                                                        System.out.println("Avaliação registrada!");
                                                    } catch (IllegalArgumentException e) {
                                                        System.out.println(e.getMessage());
                                                    }
                                                }
                                                
                                                break;
                                            }
                                        }
                                        if (!encontrado) {
                                            System.out.println("Conteúdo não encontrado.");
                                        }
                                    }
                                    break;
                                case 12:
                                    // Simular estratégias
                                    if (estrategias.size() == 0) {
                                        System.out.println("Você não possui estratégias para simular.");
                                        break;
                                    }
                                    
                                    // Iniciar simulador se não existe
                                    if (simulador == null) {
                                        scanner.nextLine(); // Limpar buffer
                                        System.out.println("Digite o saldo a simular (deixe em branco para saldo atual): ");
                                        String entrada = scanner.nextLine();
                                        double saldoSim = entrada.isEmpty() ? carteira.getSaldo() : Double.parseDouble(entrada);
                                        simulador = new Simulador(usuario.getId(), saldoSim);
                                        System.out.println("Simulador iniciado com saldo: " + saldoSim);
                                    }
                                    
                                    // Mostrar estratégias disponíveis
                                    System.out.println("===== ESTRATÉGIAS DISPONÍVEIS =====");
                                    for (int i = 0; i < estrategias.size(); i++) {
                                        System.out.println((i+1) + ". Estratégia ID: " + estrategias.get(i).getId());
                                    }
                                    System.out.println("Digite o número da estratégia para simular: ");
                                    int estrategiaNum = scanner.nextInt();
                                    
                                    if (estrategiaNum > 0 && estrategiaNum <= estrategias.size()) {
                                        EstrategiaAutomatica est = estrategias.get(estrategiaNum-1);
                                        
                                        // Criar simulação de estratégia
                                        SimulacaoEstrategia simEstrategia = new SimulacaoEstrategia(est.getId(), simulador.getId());
                                        System.out.println("Simulação criada para estratégia ID: " + est.getId());
                                        simEstrategia.exibirDetalhes();
                                    } else {
                                        System.out.println("Opção inválida.");
                                    }
                                    break;
                                case 13:
                                    // Iniciar simulador se não existe
                                    if (simulador == null) {
                                        scanner.nextLine(); // Limpar buffer
                                        System.out.println("Digite o saldo a simular (deixe em branco para saldo atual): ");
                                        String entrada = scanner.nextLine();
                                        double saldoSim = entrada.isEmpty() ? carteira.getSaldo() : Double.parseDouble(entrada);
                                        simulador = new Simulador(usuario.getId(), saldoSim);
                                        System.out.println("Simulador iniciado com saldo: " + saldoSim);
                                    }
                                    
                                    System.out.println("Simulação de ativos iniciada com saldo: " + simulador.getSaldoVirtual());
                                    break;
                                case 14:
                                    // Visualizar Simulador
                                    if (simulador != null) {
                                        simulador.exibirResumoSimulacao();
                                    } else {
                                        System.out.println("Nenhum simulador iniciado.");
                                    }
                                    break;
                                case 15:
    try {
        System.out.print("Digite o valor a depositar: ");
        double valorDeposito = scanner.nextDouble();
        
        carteira.depositar(valorDeposito);
        System.out.println("Depósito realizado! Novo saldo: " + carteira.getSaldo());
        
    } catch (InputMismatchException e) {
        System.out.println("Valor inválido! Use somente valores numéricos.");
    } catch (ValorInvalidoException e) {
        System.out.println("Valor inválido! " + e.getMessage());
    }
    finally {
        scanner.nextLine(); // Limpar buffer
    }
    break;

case 16:
    try {
        System.out.print("Digite o valor a sacar: ");
        double valorSaque = scanner.nextDouble();
        
        if (valorSaque > carteira.getSaldo()) {
            System.out.println("Saldo insuficiente! Saldo atual: " + carteira.getSaldo());
        } else {
            carteira.sacar(valorSaque);
            System.out.println("Saque realizado! Novo saldo: " + carteira.getSaldo());
        }
        
    } catch (InputMismatchException e) {
        System.out.println("Valor inválido! Use somente valores numéricos.");
    } catch (ValorInvalidoException e) {
        System.out.println("Valor inválido! " + e.getMessage());
    }
    finally {
        scanner.nextLine(); // Limpar buffer
    }
    break;
                                case 99:
                                    System.out.println("Logout realizado com sucesso.");
                                    break;
                                default:
                                    System.out.println("Opção inválida. Tente novamente.");
                                    break;
                            }

                        } while (opcao != 99);

                    } else {
                        System.out.println("Login Inválido");
                    }
                    break;
                case 2:
                    scanner.nextLine(); // Limpar buffer
                    System.out.println("===== CADASTRO DE USUÁRIO =====");
                    System.out.println("Funcionalidade em desenvolvimento.");
                    break;
                case 3:
                    scanner.nextLine(); // Limpar buffer
                    System.out.println("===== LOGIN ADMINISTRATIVO =====");
                    System.out.println("Digite o email administrativo: ");
                    String adminEmail = scanner.nextLine();
                    System.out.println("Digite a senha administrativa: ");
                    String adminSenha = scanner.nextLine();
                    
                    if (adminEmail.equals("admin@tiopatinhas.com") && adminSenha.equals("admin123")) {
                        System.out.println("======== BEM-VINDO ADMINISTRADOR ========");
                        System.out.println(" O que você gostaria de fazer agora? Selecione uma das opções abaixo.");
                        System.out.println();
                        System.out.println("0 - Consultar meus dados");
                        System.out.println();
                        System.out.println("1 - Consultar usuarios cadastrados");
                        System.out.println("2 - Consultar ativos cadastrados");
                        System.out.println("3 - Consultar modulos educacionais");
                        System.out.println();
                        System.out.println("4 - Cadastrar ativos");
                        System.out.println("5 - Criar conteúdo educacional");
                        System.out.println("6 - Emitir alerta informativo");
                        System.out.println();      
                        System.out.println("======================================");
                        
                        System.out.println("Funcionalidade em desenvolvimento.");
                    } else {
                        System.out.println("Credenciais administrativas inválidas.");
                    }
                    break;
                case 0:
                    System.out.println("Finalizando o programa.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }

        } while (opcao != 0);

        scanner.close();
    }
}