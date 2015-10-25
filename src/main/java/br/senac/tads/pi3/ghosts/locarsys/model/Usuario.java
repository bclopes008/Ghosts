package br.senac.tads.pi3.ghosts.locarsys.model; 

/**
 *
 * @author Bruno
 */
public class Usuario {

    private String id;
    private String login;
    private String senha;
    private char tipoUsuario;

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public Usuario() {
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
