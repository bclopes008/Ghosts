package br.senac.tads.pi3.ghosts.locarsys.model;

/**
 *
 * @author Bruno
 */
public abstract class Produto {
    
    private String modelo;
    private String marca;
    private String combustivel;
    private int anoFabricacao;
    private String cor;
    private char grupo;

    public Produto() {
    }
    
    public Produto(String modelo, String marca, String combustivel, int anoFabricacao, String cor, char grupo) {
        this.modelo = modelo;
        this.marca = marca;
        this.combustivel = combustivel;
        this.anoFabricacao = anoFabricacao;
        this.cor = cor;
        this.grupo = grupo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    public int getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(int anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public char getGrupo() {
        return grupo;
    }

    public void setGrupo(char grupo) {
        this.grupo = grupo;
    }
    
    
    
}
