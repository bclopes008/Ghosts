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
        
        String sql = "SELECT * FROM USUARIO WHERE LOGIN_USUARIO = '" + user.getLogin()
                + "' AND SENHA_USUARIO = '" + user.getSenha() + "'";

        try {
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            String login = null, senha = null;

            while (rs.next()) {
                user.setId(rs.getString("ID_USUARIO"));
                login = rs.getString("LOGIN_USUARIO");
                senha = rs.getString("SENHA_USUARIO");
                user.setTipoUsuario(rs.getString("TIPO_USUARIO").charAt(0));
                conn.close();
                return true;
            }
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
    
    public boolean cadastraUsuario(Usuario u)
    {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        int filial = 0, funcionario = 0;

        /* Busca o id da filial no banco de dados */
        String sql = "SELECT ID_FILIAL FROM FILIAL WHERE NOME_FILIAL = '" + u.getFilial() + "'";
        System.out.println("Filial: " + u.getFilial());
        
        try {
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                filial = rs.getInt("ID_FILIAL");
            }
            conn.close();
            
            sql = ("INSERT INTO FUNCIONARIO (ID_FILIAL, NOME_FUNCIONARIO, DATA_NASC_FUNCIONARIO, CPF_FUNCIONARIO, FUNCAO_FUNCIONARIO, SEXO_FUNCIONARIO) VALUES "
                    + "(" + filial + ", '" + u.getNome() + "','" + u.getDataNascimento() + "','" + u.getCpf() + "','" + u.getFuncao() + "','" + u.getSexo() + "')");
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.close();
            
            sql = "SELECT ID_FUNCIONARIO FROM FUNCIONARIO WHERE NOME_FUNCIONARIO = '" + u.getNome()+ "'";
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                funcionario = rs.getInt("ID_FUNCIONARIO");
            }
            conn.close();
            
            sql = "INSERT INTO USUARIO (ID_FUNCIONARIO,LOGIN_USUARIO,SENHA_USUARIO,TIPO_USUARIO) VALUES "
                    + "( " + funcionario + ",'" + u.getLogin() + "','" + u.getSenha() + "','" + u.getTipoUsuario() + "')";
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.close();            
            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("" + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("" + ex.getMessage());
        }
        return false;
    }

}
