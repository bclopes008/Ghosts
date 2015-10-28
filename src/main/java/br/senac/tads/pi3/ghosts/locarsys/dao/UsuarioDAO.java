package br.senac.tads.pi3.ghosts.locarsys.dao;

import br.senac.tads.pi3.ghosts.locarsys.model.Usuario;
import br.senac.tads.pi3.ghosts.locarsys.controller.Conexoes;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO {

    public boolean autenticaUsuario(Usuario user) {
        Statement stmt = null;
        Connection conn = null;
        System.out.println(user.getLogin());
        String sql = "SELECT * FROM USUARIO WHERE LOGIN_USUARIO = '" + user.getLogin()
                + "' AND SENHA_USUARIO = '" + user.getSenha() + "'";

        try {
            ResultSet rs = Conexoes.cmd.executeQuery(sql);

            String login = null, senha = null;

            while (rs.next()) {
                user.setId(rs.getString("ID_USUARIO"));
                login = rs.getString("LOGIN_USUARIO");
                senha = rs.getString("SENHA_USUARIO");
                user.setTipoUsuario(rs.getString("TIPO_USUARIO").charAt(0));
            }
            conn.close();
            return true;

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return false;
    }

    public ArrayList<Usuario> consultaUsuario() {
        ArrayList<Usuario> users = new ArrayList<>();

        Statement stmt = null;
        Connection conn = null;
        Usuario u = new Usuario();

        String sql = "SELECT * FROM USUARIO";
        try {
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                u.setId(rs.getString("ID_USUARIO"));
                u.setLogin(rs.getString("LOGIN_USUARIO"));
                u.setSenha(rs.getString("SENHA_USUARIO"));
                u.setTipoUsuario(rs.getString("TIPO_USUARIO").charAt(0));
                users.add(u);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }

        return users;
    }

}
