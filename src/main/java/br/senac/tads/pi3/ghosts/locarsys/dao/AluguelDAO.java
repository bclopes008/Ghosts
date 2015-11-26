/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.ghosts.locarsys.dao;

import br.senac.tads.pi3.ghosts.locarsys.model.Aluguel;
import java.util.ArrayList;
import br.senac.tads.pi3.ghosts.locarsys.controller.Conexoes;
import br.senac.tads.pi3.ghosts.locarsys.model.Carro;
import br.senac.tads.pi3.ghosts.locarsys.model.Cliente;
import java.sql.*;
import java.util.List;

/**
 *
 * @author Prime-PC
 */
public class AluguelDAO {

    public static void calcularValorTotal(Aluguel aluguel) {
        int diaInicial = Integer.parseInt(aluguel.getDataInicial().substring(8, 10));
        int mesInicial = Integer.parseInt(aluguel.getDataInicial().substring(5, 7));
        int diaFinal = Integer.parseInt(aluguel.getDataFinal().substring(8, 10));
        int mesFinal = Integer.parseInt(aluguel.getDataFinal().substring(5, 7));

        float precoClasse = 0;

        try {
            Connection conn = Conexoes.obterConexao();
            Statement stmt = conn.createStatement();
            String sql = "SELECT TARIFA_CLASSE FROM CLASSE "
                    + "WHERE TIPO_CLASSE = '" + aluguel.getCarro().getGrupo() + "'";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                precoClasse = rs.getFloat("TARIFA_CLASSE");
            }

        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }

        aluguel.setValorTotal((diaFinal - diaInicial) * precoClasse);

    }

    public static boolean cadastrarAluguel(Aluguel aluguel) throws ClassNotFoundException {
        float valorClasse = 0;

        try {
            Connection conn = Conexoes.obterConexao();
            PreparedStatement stmt = null;

            //INSERT do Aluguel
            String sql = "INSERT INTO ALUGUEL "
                    + "(ID_FUNCIONARIO, ID_CARRO, ID_CLIENTE, DATA_LOCACAO_ALUGUEL, DATA_DEVOLUCAO_ALUGUEL, PRECO_TOTAL) "
                    + "VALUES (? ,?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);

            AluguelDAO.calcularValorTotal(aluguel);

            String grupo = "" + aluguel.getCarro().getGrupo();

            stmt.setInt(1, UsuarioDAO.usuario.getId());
            stmt.setInt(2, aluguel.getCarro().getId());
            stmt.setInt(3, aluguel.getCliente().getId());
            stmt.setString(4, aluguel.getDataInicial());
            stmt.setString(5, aluguel.getDataFinal());
            stmt.setFloat(6, aluguel.getValorTotal());
            stmt.executeUpdate();

            //UPDATE Disponibilidade
            stmt.clearBatch();
            stmt.close();

            sql = "UPDATE CARRO SET DISPONIBILIDADE_CARRO = '0' "
                    + "WHERE ID_CARRO = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, aluguel.getCarro().getId());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
            System.out.println("depois INSERT ID: " + aluguel.getCarro().getId());
            return true;
        } catch (SQLException ex) {
            System.err.println("" + ex.getMessage());
            return false;
        }
    }

    public static void devolucaoAluguel(Aluguel aluguel) {
        String sql = "";

        try {
            Connection conn = null;
            PreparedStatement stmt = null;
            //Realiza update na data de devolução.
            sql = "UPDATE ALUGUEL SET DATA_DEVOLUCAO_ALUGUEL = CURRENT_DATE"
                    + "WHERE ID_ALUGUEL = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, aluguel.getId());

            stmt.execute();
            stmt.clearBatch();
            stmt.close();
            //Faz com que o carro fique novamente disponivel para locação.
            sql = "UPDATE CARRO SET DISPONIBILIDADE_CARRO = '0' "
                    + "WHERE ID_CARRO = ?";
            conn = Conexoes.obterConexao();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, aluguel.getCarro().getId());

            stmt.execute();
            conn.close();
        } catch (SQLException ex) {
            System.err.println("" + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println("" + ex.getMessage());
        }
    }

    public void deletarAluguel(Aluguel aluguel, String campoBD) throws ClassNotFoundException {
        try {
            Connection conn = Conexoes.obterConexao();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM ALUGUEL WHERE ? = ?");

            stmt.setString(1, "Preco_Total");
            stmt.setString(2, "" + aluguel.getValorTotal());

            stmt.execute();
            conn.close();
        } catch (SQLException ex) {
            System.err.println("" + ex.getMessage());
        }
    }

    public static List<Aluguel> perquisarAluguel(String modelo, String cpf, String nome) {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;

        /*Pesquisa Aluguel*/
        String sql = "SELECT * FROM ALUGUEL AL "
                + "INNER JOIN FUNCIONARIO FU ON AL.ID_FUNCIONARIO = FU.ID_FUNCIONARIO "
                + "INNER JOIN CARRO CA ON AL.ID_CARRO = CA.ID_CARRO "
                + "INNER JOIN CLIENTE CL ON AL.ID_CLIENTE = CL.ID_CLIENTE "
                + "INNER JOIN FILIAL FI ON FU.ID_FILIAL = FI.ID_FILIAL "
                + "WHERE CA.MODELO_CARRO LIKE '%" + modelo + "%' AND CL.CPF_CLIENTE LIKE '%" + cpf + "%' "
                + "AND CL.NOME_CLIENTE LIKE '%" + nome + "%' AND FI.NOME_FILIAL = '" + UsuarioDAO.usuario.getFilial() + "'";
        try {
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            ArrayList<Aluguel> alugueis = new ArrayList<>();
            while (rs.next()) {
                Aluguel a = new Aluguel();
                Cliente c = new Cliente();
                Carro ca = new Carro();
                a.setId(rs.getInt("ID_ALUGUEL"));
                a.setDataInicial(rs.getString("DATA_LOCACAO_ALUGUEL"));
                a.setDataFinal(rs.getString("DATA_DEVOLUCAO_ALUGUEL"));
                c.setCpf(rs.getString("CPF_CLIENTE"));
                c.setCnh(rs.getString("CNH_CLIENTE"));
                c.setNome(rs.getString("NOME_CLIENTE"));
                ca.setModelo(rs.getString("MODELO_CARRO"));
                ca.setPlaca(rs.getString("PLACA_CARRO"));
                a.setCliente(c);
                a.setCarro(ca);
                alugueis.add(a);
            }
            conn.close();
            return alugueis;
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Erro: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return null;
    }

}
