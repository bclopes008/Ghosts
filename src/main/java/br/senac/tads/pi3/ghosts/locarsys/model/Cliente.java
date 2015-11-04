package br.senac.tads.pi3.ghosts.locarsys.model;

import java.util.Date;

/**
 *
 * @author Bruno
 */
public class Cliente extends Endereco{

    private String cnh;
    private String celular;
    private String email;

    public Cliente() {
    }

    public Cliente(String cnh, String celular, String email, String endereco, String numero, String complemento, String cep, String bairro, String estado, String cidade, String obs, String nome, char sexo, Date dataNascimento, String cpf) {
        super(endereco, numero, complemento, cep, bairro, estado, cidade, obs, nome, sexo, dataNascimento, cpf);
        this.cnh = cnh;
        this.celular = celular;
        this.email = email;
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
    
}
