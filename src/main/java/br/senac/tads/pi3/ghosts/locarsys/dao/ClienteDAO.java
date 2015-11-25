/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.ghosts.locarsys.dao;

import br.senac.tads.pi3.ghosts.locarsys.controller.Conexoes;
import br.senac.tads.pi3.ghosts.locarsys.model.Cliente;
import br.senac.tads.pi3.ghosts.locarsys.model.Endereco;
import br.senac.tads.pi3.ghosts.locarsys.model.VerificacoesCliente;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bruno
 */
public class ClienteDAO implements VerificacoesCliente {

    public static boolean cadastroCliente(Cliente c) {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        int estado = 0;
        Endereco e = c.getEndereco();

        /* INSERT do Cliente */
        String sql = "INSERT INTO CLIENTE (NOME_CLIENTE, CPF_CLIENTE, CNH_CLIENTE, DATA_NASC_CLIENTE, SEXO_CLIENTE, CELULAR_CLIENTE, EMAIL_CLIENTE) VALUES ('" + c.getNome() + "', '"
                + c.getCpf() + "', '" + c.getCnh() + "', '" + c.getDataNascimento() + "', '" + c.getSexo() + "', '" + c.getCelular() + "', '" + c.getEmail() + "')";
        try {
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.close();

            /* Buscar id do Cliente, para o INSERT do Endereço */
            sql = "SELECT ID_CLIENTE FROM CLIENTE WHERE CPF_CLIENTE = '" + c.getCpf() + "'";

            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                c.setId(rs.getInt("ID_CLIENTE"));
            }
            conn.close();

            /* Buscar id do Estado, para o INSERT do Endereço */
            sql = "SELECT ID_ESTADO FROM ESTADO WHERE SIGLA_ESTADO = '" + e.getEstado() + "'";

            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                estado = rs.getInt("ID_ESTADO");
            }
            conn.close();

            sql = "INSERT INTO Endereco(ID_CLIENTE, ID_ESTADO, CIDADE_ESTADO, LOGRADOURO_ENDERECO, NUMERO_ENDERECO, BAIRRO_ENDERECO, COMPLEMENTO_ENDERECO, CEP_ENDERECO, OBS_ENDERECO) VALUES("
                    + c.getId() + ", " + estado + ", '" + e.getCidade() + "', '" + e.getEndereco() + "', '" + e.getNumero() + "', '" + e.getBairro() + "', '" + e.getComplemento() + "', '" + e.getCep() + "', '"
                    + e.getObs() + "')";
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.close();
            System.out.println("Passou aqui: " + c.getEndereco().getObs());
            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("" + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("" + ex.getMessage());
        }
        return false;
    }

    public static List<Cliente> pesquisarCliente(String nome, String cpf) {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;

        /* Pesquisa Cliente */
        String sql = "SELECT * FROM CLIENTE "
                + "WHERE NOME_CLIENTE LIKE '%" + nome + "%' "
                + "AND CPF_CLIENTE LIKE '%" + cpf + "%' "
                + "ORDER BY NOME_CLIENTE";
        try {
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            ArrayList<Cliente> clientes = new ArrayList<>();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("ID_CLIENTE"));
                c.setNome(rs.getString("NOME_CLIENTE"));
                c.setCpf(rs.getString("CPF_CLIENTE"));
                c.setCnh(rs.getString("CNH_CLIENTE"));
                clientes.add(c);
            }
            conn.close();
            return clientes;
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Erro: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return null;
    }

    //Pega o id do Cliente para verifica e ir para a tela de alterar
    public static Cliente verCliente(int id) {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;

        /* Pesquisa Cliente com o id para ir na tela de alteração*/
        String sql = "SELECT * FROM CLIENTE CL "
                + "INNER JOIN ENDERECO EN ON CL.ID_CLIENTE = EN.ID_ENDERECO "
                + "INNER JOIN ESTADO ES ON EN.ID_ESTADO = ES.ID_ESTADO "
                + "WHERE CL.ID_CLIENTE = " + id + "";
        try {
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            Cliente c = new Cliente();
            Endereco e = new Endereco();
            while (rs.next()) {
                c.setId(rs.getInt("ID_CLIENTE"));
                c.setNome(rs.getString("NOME_CLIENTE"));
                c.setCpf(rs.getString("CPF_CLIENTE"));
                c.setCnh(rs.getString("CNH_CLIENTE"));
                c.setDataNascimento(rs.getString("DATA_NASC_CLIENTE"));
                c.setSexo(rs.getString("SEXO_CLIENTE").charAt(0));
                c.setCelular(rs.getString("CELULAR_CLIENTE"));
                c.setEmail(rs.getString("EMAIL_CLIENTE"));
                e.setCidade(rs.getString("CIDADE_ESTADO"));
                e.setEndereco(rs.getString("LOGRADOURO_ENDERECO"));
                e.setNumero(rs.getString("NUMERO_ENDERECO"));
                e.setBairro(rs.getString("BAIRRO_ENDERECO"));
                e.setComplemento(rs.getString("COMPLEMENTO_ENDERECO"));
                e.setCep(rs.getString("CEP_ENDERECO"));
                e.setObs(rs.getString("OBS_ENDERECO"));
                e.setEstado(rs.getString("SIGLA_ESTADO"));
            }
            c.setEndereco(e);
            conn.close();
            return c;
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("" + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("" + ex.getMessage());
        }
        return null;
    }

    //Altera o cadastro do Cliente
    public static boolean alterarCliente(Cliente c) {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        int estado = 0;
        Endereco e = c.getEndereco();

        /* Altera Cliente */
        System.out.println("Nome: " + c.getId());
        String sql = "UPDATE CLIENTE SET NOME_CLIENTE = '" + c.getNome() + "', CPF_CLIENTE = '" + c.getCpf() + "', CNH_CLIENTE = '" + c.getCnh() + "', DATA_NASC_CLIENTE = '" + c.getDataNascimento() + "', SEXO_CLIENTE = '" + c.getSexo() + "', CELULAR_CLIENTE = '" + c.getCelular() + "', EMAIL_CLIENTE = '" + c.getEmail() + "' WHERE ID_CLIENTE = " + c.getId() + "";
        try {
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.close();

            /* Buscar id do Estado, para o Alterar do Endereço */
            sql = "SELECT ID_ESTADO FROM ESTADO WHERE SIGLA_ESTADO = '" + c.getEndereco().getEstado() + "'";

            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                estado = rs.getInt("ID_ESTADO");
            }
            conn.close();

            //Altera o endereço do Cliente
            sql = "UPDATE ENDERECO SET ID_ESTADO = " + estado + ", LOGRADOURO_ENDERECO = '" + e.getEndereco() + "', NUMERO_ENDERECO = '" + e.getNumero() + "', BAIRRO_ENDERECO = '" + e.getBairro() + "', COMPLEMENTO_ENDERECO = '" + e.getComplemento() + "', CEP_ENDERECO = '" + e.getCep() + "', OBS_ENDERECO = '" + e.getObs() + "' WHERE ID_CLIENTE = " + c.getId() + "";
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

        String sql = "SELECT CPF_CLIENTE FROM CLIENTE "
                + "WHERE CPF_CLIENTE = '" + cpf + "'";
        try {
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("ID_CLIENTE");
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
    public boolean verificaCNH(String cnh) {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;

        String sql = "SELECT CNH_CLIENTE FROM CLIENTE "
                + "WHERE CNH_CLIENTE = '" + cnh + "'";
        try {
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("ID_CARRO");
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

    public static String verificaoes(Cliente c) {
        ClienteDAO dao = new ClienteDAO();
        if (dao.verificaCPF(c.getCpf())) {
            return "Já existe esse CPF cadastrado!";
        } else if (dao.verificaCNH(c.getCnh())) {
            return "Já existe esse CNH cadastrado!";
        }
        return null;
    }

}
