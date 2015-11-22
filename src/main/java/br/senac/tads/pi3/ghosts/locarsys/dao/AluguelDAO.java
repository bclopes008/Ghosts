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
import br.senac.tads.pi3.ghosts.locarsys.model.Funcionario;
import br.senac.tads.pi3.ghosts.locarsys.model.Produto;
import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Prime-PC
 */
public class AluguelDAO {

    public ArrayList<Aluguel> consultaAluguel() throws ClassNotFoundException {
        ArrayList<Aluguel> alugueis = new ArrayList<>();
        Aluguel aluguelObj = new Aluguel();

        try {
            Connection conn = Conexoes.obterConexao();
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM ALUGUEL AL "
                    + "INNER JOIN CARRO CA ON CA.ID_CARRO = AL.ID_CARRO "
                    + "INNER JOIN FILIAL FL ON FL.ID_FILIAL = CA.ID_FILIAL "
                    + "INNER JOIN FABRICANTE FA ON FA.ID_FABRICANTE = CA.ID_FABRICANTE "
                    + "INNER JOIN COMBUSTIVEL CO ON CO.ID_COMBUSTIVEL = CA.ID_COMBUSTIVEL "
                    + "INNER JOIN CLASSE CLA ON CLA.ID_CLASSE = CA.ID_CLASSE";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                //Atributos do Carro
//                aluguelObj.setCarro(new Carro(
//                        rs.getString("CHASSI_CARRO"),
//                        rs.getString("PLACA_CARRO"),
//                        rs.getString("ESTADO_CARRO"),
//                        rs.getString("CIDADE_CARRO"),
//                        rs.getInt("ANO_CARRO"),
//                        rs.getString("RENAVAM_CARRO"),
//                        rs.getFloat("KILOMETRAGEM_CARRO"),
//                        rs.getString("MODELO_CARRO"),
//                        rs.getString("NOME_FABRICANTE"),
//                        rs.getString("TIPO_COMBUSTIVEL"),
//                        rs.getInt("ANO_FABRICACAO_CARRO"),
//                        rs.getString("COR_CARRO"),
//                        rs.getString("TIPO_CLASSE").charAt(0),
//                        rs.getString("NOME_FILIAL")));

                //Atributos do Cliente
//                aluguelObj.setCliente(new Cliente(
//                        rs.getString("CNH_CLIENTE"),
//                        rs.getString("CELULAR_CLIENTE"),
//                        rs.getString("EMAIL_CLIENTE"),
//                        rs.getString("LOGRADOURO_ENDERECO"),
//                        rs.getString("NUMERO_ENDERECO"),
//                        rs.getString("COMPLEMENTO_ENDERECO"),
//                        rs.getString("CEP_ENDERECO"),
//                        rs.getString("BAIRRO_ENDERECO"),
//                        rs.getString("OBS_ENDERECO"),
//                        rs.getString("NOME_CLIENTE"),
//                        rs.getString("SEXO_CLIENTE").charAt(0),
//                        rs.getDate("DATA_NASC_CLIENTE"),
//                        rs.getString("CPF_CLIENTE")));
                //Atributos do Funcionário
//                aluguelObj.setFuncionario(new Funcionario(
//                        rs.getString("FUNCAO_FUNCIONARIO"),
//                        rs.getString("NOME_FILIAL"),
//                        rs.getString("NOME_FUNCIONARIO"),
//                        rs.getString("SEXO_FUNCIONARIO").charAt(0),
//                        rs.getDate("DATA_NASC_FUNCIONARIO"),
//                        rs.getString("CPF_FUNCIONARIO")));
                aluguelObj.setDataInicial(rs.getString("DATA_LOCACAO_ALUGUEL"));
                aluguelObj.setDataFinal(rs.getString("DATA_DEVOLUCAO_ALUGUEL"));
                aluguelObj.setValorTotal(rs.getFloat("PRECO_TOTAL"));

                alugueis.add(aluguelObj);
            }
            conn.close();
            return alugueis;
        } catch (SQLException ex) {
            System.err.println("" + ex.getMessage());
        }
        return null;
    }

    public static void calcularValorTotal(Aluguel aluguel) {
        Calendar dataAtual = new GregorianCalendar();
        Calendar dataFinal = new GregorianCalendar();
        Calendar dataInicial = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            dataInicial.setTime(sdf.parse(aluguel.getDataInicial()));
            dataFinal.setTime(sdf.parse(aluguel.getDataFinal()));
        } catch (ParseException ex) {
            System.err.println(ex.getMessage());
        }

        dataAtual.get(Calendar.DATE);

        float precoClasse = 0;

        try {
            Connection conn = Conexoes.obterConexao();
            Statement stmt = conn.createStatement();
            String sql = "SELECT TARIFA_CLASSE FROM CARRO CA INNER JOIN CLASSE CL "
                    + "ON CL.ID_CLASSE = CA.ID_CLASSE WHERE ID_CARRO = " + aluguel.getCarro().getChassi() + " FETCH FIRST 1 ROW ONLY";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                precoClasse = rs.getFloat("TARIFA_CLASSE");
            }

        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }

        if (dataFinal.get(Calendar.DATE) <= dataAtual.get(Calendar.DATE)) {
            aluguel.setValorTotal((dataFinal.get(Calendar.DATE) - dataInicial.get(Calendar.DATE)) * precoClasse);
        } else {
            aluguel.setValorTotal((dataFinal.get(Calendar.DATE) - dataInicial.get(Calendar.DATE)) * precoClasse * 1.15f);
        }
    }

    public static boolean cadastrarAluguel(Aluguel aluguel) throws ClassNotFoundException {
        float valorClasse = 0;

        try {
            Connection conn = Conexoes.obterConexao();
            PreparedStatement stmt = null;

            //INSERT do Aluguel
            String sql = "INSERT INTO ALUGUEL "
                    + "(ID_FUNCIONARIO, ID_CARRO, ID_CLIENTE, DATA_LOCACAO_ALUGUEL, DATA_DEVOLUCAO_ALUGUEL, PRECO_TOTAL) "
                    + "VALUES (1,(SELECT ID_CARRO FROM CARRO CA INNER JOIN CLASSE CL ON CA.ID_CLASSE = CL.ID_CLASSE "
                    + "WHERE MODELO_CARRO = ? AND CL.TIPO_CLASSE = ? FETCH FIRST 1 ROW ONLY), "
                    + "(SELECT ID_CLIENTE FROM CLIENTE WHERE CNH_CLIENTE = ? FETCH FIRST 1 ROW ONLY), ?, ?, ?)";
            stmt = conn.prepareStatement(sql);

            AluguelDAO.calcularValorTotal(aluguel);

            System.out.println("Valor total: " + aluguel.getValorTotal());
            String grupo = "" + aluguel.getCarro().getGrupo();
            //Falta implementar o funcionário atualmente está como '1'
            stmt.setString(1, aluguel.getCarro().getModelo());
            stmt.setString(2, grupo);
            stmt.setString(3, aluguel.getCliente().getCnh());
            stmt.setString(4, aluguel.getDataInicial());
            stmt.setString(5, aluguel.getDataFinal());
            stmt.setFloat(6, aluguel.getValorTotal());
            stmt.executeUpdate();

            //UPDATE Disponibilidade
            stmt.clearBatch();
            sql = "UPDATE CARRO SET DISPONIBILIDADE_CARRO = '0' "
                    + "WHERE ID_CARRO = (SELECT ID_CARRO FROM Carro WHERE DISPONIBILIDADE_CARRO = '1' "
                    + "AND MODELO_CARRO = ? AND CL.TIPO_CLASSE = ? FETCH FIRST 1 ROW ONLY)";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, aluguel.getCarro().getModelo());
            stmt.setString(2, grupo);
            System.out.println("Grupo: " + aluguel.getCarro().getGrupo());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
            return true;
        } catch (SQLException ex) {
            System.err.println("" + ex.getMessage());
            return false;
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
