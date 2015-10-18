package br.senac.tads.pi3.ghosts.locarsys.model;

import java.util.Date;

/**
 *
 * @author Bruno
 */
public class Cliente extends Endereco{

    private int cpf;
    private int cnh;
    private String celular;
    private String email;

    public Cliente(int cpf, int cnh, String celular, String email, String endereco, int numero, String complemento, int cep, String bairro, String estado, String cidade, String nome, char sexo, Date dataNascimento) {
        super(endereco, numero, complemento, cep, bairro, estado, cidade, nome, sexo, dataNascimento);
        this.cpf = cpf;
        this.cnh = cnh;
        this.celular = celular;
        this.email = email;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public int getCnh() {
        return cnh;
    }

    public void setCnh(int cnh) {
        this.cnh = cnh;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
}
