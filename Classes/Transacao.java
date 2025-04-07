import java.time.LocalDateTime; //Importa LocalDateTime
import java.util.UUID;

/**
 * Classe que representa uma transação de criptoativos no sistema.
 * Pode ser compra, venda ou transferência entre carteiras.
 */
public class Transacao {

    // Atributos
    private int id;
    private int idUsuario;
    private int idAtivo;
    private String tipo; // compra, venda, transferência_entrada, transferência_saída
    private double volumeTransacao; // quantidade do ativo
    private LocalDateTime dataHoraTransacao;
    private double valorUnitario; // valor unitário no momento da transação
    private double valorTotal; // volume * valorUnitario
    private String hash; // hash único da transação para segurança
    private int idCarteiraOrigem; // para transferências
    private int idCarteiraDestino; // para transferências
    private String status; // pendente, concluída, cancelada

    // Construtores
    public Transacao() {
        this.dataHoraTransacao = LocalDateTime.now();
        this.hash = gerarHash();
        this.status = "pendente";
    }
    
    public Transacao(int idUsuario, int idAtivo, String tipo, double volumeTransacao, double valorUnitario) {
        this.idUsuario = idUsuario;
        this.idAtivo = idAtivo;
        this.tipo = tipo;
        this.volumeTransacao = volumeTransacao;
        this.valorUnitario = valorUnitario;
        this.valorTotal = volumeTransacao * valorUnitario;
        this.dataHoraTransacao = LocalDateTime.now();
        this.hash = gerarHash();
        this.status = "pendente";
    }

    // Métodos
    /**
     * Gera um hash único para a transação
     * @return Hash gerado
     */
    private String gerarHash() {
        // Em uma implementação real, usaria algoritmos de hash criptográficos
        return UUID.randomUUID().toString();
    }
    
    /**
     * Conclui a transação alterando seu status
     * @return true se a operação foi bem-sucedida
     */
    public boolean concluir() {
        if (this.status.equals("pendente")) {
            this.status = "concluída";
            return true;
        }
        return false;
    }
    
    /**
     * Cancela a transação alterando seu status
     * @return true se a operação foi bem-sucedida
     */
    public boolean cancelar() {
        if (this.status.equals("pendente")) {
            this.status = "cancelada";
            return true;
        }
        return false;
    }
    
    /**
     * Verifica se a transação é válida
     * @return true se a transação for válida
     */
    public boolean verificarValidade() {
        // Verificar se a transação tem valores válidos
        if (this.volumeTransacao <= 0 || this.valorUnitario <= 0) {
            return false;
        }
        
        // Verificar se o tipo é válido
        if (!this.tipo.equals("compra") && !this.tipo.equals("venda") && 
            !this.tipo.equals("transferência_entrada") && !this.tipo.equals("transferência_saída")) {
            return false;
        }
        
        // Para transferências, verificar se as carteiras origem e destino são válidas
        if (this.tipo.equals("transferência_entrada") || this.tipo.equals("transferência_saída")) {
            if (this.idCarteiraOrigem <= 0 || this.idCarteiraDestino <= 0) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Calcula o imposto sobre a transação (simulação)
     * @return Valor do imposto calculado
     */
    public double calcularImposto() {
        // Simulação de cálculo de imposto - em um sistema real seria mais complexo
        // Por exemplo, 15% sobre o lucro em vendas com valor total acima de R$ 35.000,00
        if (this.tipo.equals("venda") && this.valorTotal > 35000.0) {
            return this.valorTotal * 0.15;
        }
        return 0.0;
    }
    
    /**
     * Exibe detalhes da transação
     */
    public void exibirDetalhes() {
        System.out.println("=== Detalhes da Transação ===");
        System.out.println("ID: " + this.id);
        System.out.println("Hash: " + this.hash);
        System.out.println("Tipo: " + this.tipo);
        System.out.println("Usuário: " + this.idUsuario);
        System.out.println("Ativo: " + this.idAtivo);
        System.out.println("Volume: " + this.volumeTransacao);
        System.out.println("Valor Unitário: " + this.valorUnitario);
        System.out.println("Valor Total: " + this.valorTotal);
        System.out.println("Data/Hora: " + this.dataHoraTransacao);
        System.out.println("Status: " + this.status);
        if (this.tipo.equals("transferência_entrada") || this.tipo.equals("transferência_saída")) {
            System.out.println("Carteira Origem: " + this.idCarteiraOrigem);
            System.out.println("Carteira Destino: " + this.idCarteiraDestino);
        }
        System.out.println("============================");
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

    public int getIdAtivo() {
        return idAtivo;
    }

    public void setIdAtivo(int idAtivo) {
        this.idAtivo = idAtivo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getVolumeTransacao() {
        return volumeTransacao;
    }

    public void setVolumeTransacao(double volumeTransacao) {
        this.volumeTransacao = volumeTransacao;
        this.valorTotal = this.volumeTransacao * this.valorUnitario;
    }

    public LocalDateTime getDataHoraTransacao() {
        return dataHoraTransacao;
    }

    public void setDataHoraTransacao(LocalDateTime dataHoraTransacao) {
        this.dataHoraTransacao = dataHoraTransacao;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
        this.valorTotal = this.volumeTransacao * this.valorUnitario;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public String getHash() {
        return hash;
    }

    public int getIdCarteiraOrigem() {
        return idCarteiraOrigem;
    }

    public void setIdCarteiraOrigem(int idCarteiraOrigem) {
        this.idCarteiraOrigem = idCarteiraOrigem;
    }

    public int getIdCarteiraDestino() {
        return idCarteiraDestino;
    }

    public void setIdCarteiraDestino(int idCarteiraDestino) {
        this.idCarteiraDestino = idCarteiraDestino;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}