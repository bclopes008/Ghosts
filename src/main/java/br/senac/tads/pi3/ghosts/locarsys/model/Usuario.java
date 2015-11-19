package br.senac.tads.pi3.ghosts.locarsys.model; 

import java.util.Date;

/**
 *
 * @author Bruno
 */
public class Usuario extends Funcionario{

    private String id;
    private String login;
    private String senha;
    private char tipoUsuario;

    public Usuario() {
    }

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public Usuario(String id, String login, String senha, char tipoUsuario, String funcao, String filial, String nome, char sexo, String dataNascimento, String cpf) {
        super(funcao, filial, nome, sexo, dataNascimento, cpf);
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setTipoUsuario(char tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public char getTipoUsuario() {
        return tipoUsuario;
    }
}
