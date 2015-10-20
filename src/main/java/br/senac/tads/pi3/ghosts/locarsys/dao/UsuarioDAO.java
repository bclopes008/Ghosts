package br.senac.tads.pi3.ghosts.locarsys.dao;

import br.senac.tads.pi3.ghosts.locarsys.model.Usuario;
import br.senac.tads.pi3.ghosts.locarsys.controller.Conexoes;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UsuarioDAO implements DAO{
    
    public Usuario verificaUsuario(String login , String senha) {
        Statement stmt = null;
        Connection conn = null;
        Usuario u = new Usuario();

        String sql = "SELECT * FROM USUARIO WHERE LOGIN_USUARIO = '" + login
                    + "' AND SENHA_USUARIO = '" + senha + "'";
        try {
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                u.setId(rs.getString("ID_USUARIO"));
                u.setLogin(rs.getString("LOGIN_USUARIO"));
                u.setSenha(rs.getString("SENHA_USUARIO"));
                u.setTipoUsuario(rs.getString("TIPO_USUARIO").charAt(0));
            }
            return u;

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return null;
    }
}
