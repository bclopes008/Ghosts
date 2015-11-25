package br.senac.tads.pi3.ghosts.locarsys.dao;

import br.senac.tads.pi3.ghosts.locarsys.model.Usuario;
import br.senac.tads.pi3.ghosts.locarsys.controller.Conexoes;
import br.senac.tads.pi3.ghosts.locarsys.model.VerificacoesUsuario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO implements VerificacoesUsuario {

    public static Usuario usuario;
    //public static char tipoUsuario;

    public static boolean autenticaUsuario(Usuario user) {
        Statement stmt = null;
        Connection conn = null;

        /*String sql = "SELECT * FROM USUARIO WHERE LOGIN_USUARIO = '" + user.getLogin()
         + "' AND SENHA_USUARIO = '" + user.getSenha() + "'";*/
        String sql = "SELECT * FROM USUARIO US "
                + "INNER JOIN FUNCIONARIO FU ON US.ID_FUNCIONARIO = FU.ID_FUNCIONARIO "
                + "INNER JOIN FILIAL FL ON FL.ID_FILIAL = FU.ID_FILIAL "
                + "WHERE LOGIN_USUARIO = '" + user.getLogin() + "' AND SENHA_USUARIO = '" + user.getSenha() + "'";
        try {
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                user.setId(rs.getInt("ID_USUARIO"));
                user.setTipoUsuario(rs.getString("TIPO_USUARIO").charAt(0));
                user.setFilial(rs.getString("NOME_FILIAL"));
                conn.close();
                usuario = user;
                return true;
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return false;
    }

    public static List<Usuario> pesquisarUsuario(String nome, String login) {

        Statement stmt = null;
        Connection conn = null;

        String sql = "SELECT * FROM FUNCIONARIO FU "
                + "INNER JOIN USUARIO US ON FU.ID_FUNCIONARIO = US.ID_USUARIO "
                + "WHERE FU.NOME_FUNCIONARIO LIKE '%" + nome + "%' "
                + "AND US.LOGIN_USUARIO LIKE '%" + login + "%'";
        try {
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ArrayList<Usuario> usuarios = new ArrayList<>();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("ID_USUARIO"));
                u.setNome(rs.getString("NOME_FUNCIONARIO"));
                u.setLogin(rs.getString("LOGIN_USUARIO"));
                usuarios.add(u);
            }
            conn.close();
            return usuarios;
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return null;
    }

    public static boolean cadastraUsuario(Usuario u) {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        int filial = 0;

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

            sql = "SELECT ID_FUNCIONARIO FROM FUNCIONARIO WHERE NOME_FUNCIONARIO = '" + u.getNome() + "'";
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                u.setId(rs.getInt("ID_FUNCIONARIO"));
            }
            conn.close();

            sql = "INSERT INTO USUARIO (ID_FUNCIONARIO,LOGIN_USUARIO,SENHA_USUARIO,TIPO_USUARIO) VALUES "
                    + "( " + u.getId() + ",'" + u.getLogin() + "','" + u.getSenha() + "','" + u.getTipoUsuario() + "')";
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

    public static Usuario verUsuario(int id) {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;

        /* Pesquisa Usuario com o id para ir na tela de alteração*/
        String sql = "SELECT * FROM FUNCIONARIO FU "
                + "INNER JOIN USUARIO US ON FU.ID_FUNCIONARIO = US.ID_USUARIO "
                + "INNER JOIN FILIAL FI ON FU.ID_FILIAL = FI.ID_FILIAL "
                + "WHERE FU.ID_FUNCIONARIO = " + id + "";
        try {
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            Usuario u = new Usuario();
            while (rs.next()) {
                u.setId(rs.getInt("ID_USUARIO"));
                u.setNome(rs.getString("NOME_FUNCIONARIO"));
                u.setDataNascimento(rs.getString("DATA_NASC_FUNCIONARIO"));
                u.setCpf(rs.getString("CPF_FUNCIONARIO"));
                u.setFuncao(rs.getString("FUNCAO_FUNCIONARIO"));
                u.setSexo(rs.getString("SEXO_FUNCIONARIO").charAt(0));
                u.setLogin(rs.getString("LOGIN_USUARIO"));
                u.setTipoUsuario(rs.getString("TIPO_USUARIO").charAt(0));
                u.setFilial(rs.getString("NOME_FILIAL"));
            }
            conn.close();
            return u;
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Erro: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return null;
    }

    public static boolean alteraUsuario(Usuario u) {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        int filial = 0;

        String sql = "SELECT ID_FILIAL FROM FILIAL WHERE NOME_FILIAL = '" + u.getFilial() + "'";
        try {
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                filial = rs.getInt("ID_FILIAL");
            }
            conn.close();

            sql = "UPDATE FUNCIONARIO SET ID_FILIAL = " + filial + ", NOME_FUNCIONARIO = '" + u.getNome() + "', DATA_NASC_FUNCIONARIO = '" + u.getDataNascimento() + "', "
                    + "CPF_FUNCIONARIO = '" + u.getCpf() + "', FUNCAO_FUNCIONARIO = '" + u.getFuncao() + "', SEXO_FUNCIONARIO = '" + u.getSexo() + "' "
                    + "WHERE ID_FUNCIONARIO = " + u.getId() + "";
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.close();

            sql = "UPDATE USUARIO SET LOGIN_USUARIO = '" + u.getLogin() + "', SENHA_USUARIO = '" + u.getSenha() + "', TIPO_USUARIO = '" + u.getTipoUsuario() + "' "
                    + "WHERE ID_USUARIO = " + u.getId() + "";
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

    @Override
    public boolean verificaCPF(String cpf) {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;

        String sql = "SELECT CPF_FUNCIONARIO FROM FUNCIONARIO "
                + "WHERE CPF_FUNCIONARIO = '" + cpf + "'";
        try {
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("ID_FUNCIONARIO");
                return false;
            }
            conn.close();
            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Erro: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean verificaLogin(String login) {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;

        String sql = "SELECT LOGIN_USUARIO FROM USUARIO "
                + "WHERE LOGIN_USUARIO = '" + login + "'";
        try {
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("ID_USUARIO");
                return false;
            }
            conn.close();
            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Erro: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return false;
    }

    public static String verificaoes(Usuario u) {
        UsuarioDAO dao = new UsuarioDAO();
        if (dao.verificaCPF(u.getCpf())) {
            return "Já existe esse Login cadastrado!";
        } else if (dao.verificaLogin(u.getLogin())) {
            return "Já existe esse CPF cadastrado!";
        }
        return null;
    }
}
