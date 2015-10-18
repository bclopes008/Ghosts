package br.senac.tads.pi3.ghosts.locarsys.model;

import br.senac.tads.pi3.ghosts.locarsys.model.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UsuarioDAO {

    private Connection obterConexao() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        // Passo 1: Registrar driver JDBC.
        Class.forName("org.apache.derby.jdbc.ClientDataSource");

        // Passo 2: Abrir a conexão
        conn = DriverManager.getConnection("jdbc:derby://localhost:1527/locarsys;SecurityMechanism=3",
                "app", // usuario
                "app"); // senha
        return conn;
    }
    
    public Usuario verificaUsuario(String login , String senha) {
        Statement stmt = null;
        Connection conn = null;
        Usuario u = new Usuario();

        String sql = "SELECT * FROM USUARIO WHERE LOGIN_USUARIO = '" + login
                    + "' AND SENHA_USUARIO = '" + senha + "'";
        try {
            conn = obterConexao();
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
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }
}
