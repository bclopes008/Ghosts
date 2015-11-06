package br.senac.tads.pi3.ghosts.locarsys.model;

import java.util.Date;

/**
 *
 * @author Bruno
 */
public abstract class Endereco extends Pessoa{
    
    private String endereco;
    private String numero;
    private String complemento;
    private String cep;
    private String bairro;
    private String estado;
    private String cidade;
    private String obs;

    public Endereco() {
    }

    public Endereco(String endereco, String numero, String complemento, String cep, String bairro, String estado, String cidade, String obs, String nome, char sexo, String dataNascimento, String cpf) {
        super(nome, sexo, dataNascimento, cpf);
        this.endereco = endereco;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
        this.bairro = bairro;
        this.estado = estado;
        this.cidade = cidade;
        this.obs = obs;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
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

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
    
    
    
    
}
