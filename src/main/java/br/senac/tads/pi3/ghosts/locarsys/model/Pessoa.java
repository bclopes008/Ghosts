package br.senac.tads.pi3.ghosts.locarsys.model;

import java.util.Date;

/**
 *
 * @author Bruno
 */
public abstract class Pessoa {
    
    private String nome;
    private char sexo;
    private String dataNascimento;
    private String cpf;

    public Pessoa() {
    }

    public Pessoa(String nome, char sexo, String dataNascimento, String cpf) {
        this.nome = nome;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    
}