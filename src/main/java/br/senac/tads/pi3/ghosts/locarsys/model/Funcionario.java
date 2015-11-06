package br.senac.tads.pi3.ghosts.locarsys.model;

import java.util.Date;

/**
 *
 * @author Bruno
 */
public class Funcionario extends Pessoa{
    
    private String funcao;
    private String filial;

    public Funcionario() {
    }

    public Funcionario(String funcao, String filial, String nome, char sexo, String dataNascimento, String cpf) {
        super(nome, sexo, dataNascimento, cpf);
        this.funcao = funcao;
        this.filial = filial;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getFilial() {
        return filial;
    }

    public void setFilial(String filial) {
        this.filial = filial;
    }
}
