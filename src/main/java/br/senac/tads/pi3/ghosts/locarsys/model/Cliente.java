package br.senac.tads.pi3.ghosts.locarsys.model;

import java.util.Date;

/**
 *
 * @author Bruno
 */
public class Cliente extends Pessoa{

    private int id;
    private String cnh;
    private String celular;
    private String email;
    private Endereco endereco;

    public Cliente() {
    }

    public Cliente(String cnh, String celular, String email, Endereco endereco, String nome, char sexo, String dataNascimento, String cpf) {
        super(nome, sexo, dataNascimento, cpf);
        this.cnh = cnh;
        this.celular = celular;
        this.email = email;
        this.endereco = endereco;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
