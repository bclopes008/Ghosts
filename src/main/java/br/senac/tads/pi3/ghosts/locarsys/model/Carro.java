package br.senac.tads.pi3.ghosts.locarsys.model;

/**
 *
 * @author Bruno
 */
public class Carro extends Produto{
    
    private String chassi;
    private String placa;
    private String estado;
    private String cidade;
    private int ano;
    private int renavam;
    private float kilometragem;

    public Carro() {
    }
    
    public Carro(String chassi, String placa, String estado, String cidade, int ano, int renavam, float kilometragem, String modelo, String marca, char combustivel, int anoFabricacao, String cor, char grupo) {
        super(modelo, marca, combustivel, anoFabricacao, cor, grupo);
        this.chassi = chassi;
        this.placa = placa;
        this.estado = estado;
        this.cidade = cidade;
        this.ano = ano;
        this.renavam = renavam;
        this.kilometragem = kilometragem;
    }
    
    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getRenavam() {
        return renavam;
    }

    public void setRenavam(int renavam) {
        this.renavam = renavam;
    }

    public float getKilometragem() {
        return kilometragem;
    }

    public void setKilometragem(float kilometragem) {
        this.kilometragem = kilometragem;
    }

}
