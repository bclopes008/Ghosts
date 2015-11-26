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
import br.senac.tads.pi3.ghosts.locarsys.model.VerificacoesAluguel;
import java.sql.*;
import java.util.List;

/**
 *
 * @author Prime-PC
 */
public class AluguelDAO implements VerificacoesAluguel {

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
        int anoInicial = Integer.parseInt(aluguel.getDataInicial().substring(1, 4));
        int anoFinal = Integer.parseInt(aluguel.getDataFinal().substring(1, 4));
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
        int dias = 0;
        if (anoInicial == anoFinal) {
            if (mesInicial < mesFinal) {
                if (diaInicial >= diaFinal) {
                    dias = 30 - diaInicial;
                    dias += diaFinal;
                }
            } else if (mesInicial == mesFinal) {
                dias = diaFinal - diaInicial;
            }
        } else if (anoInicial < anoFinal) {
            dias = 30 - diaInicial;
            if (mesFinal == 1) {
                dias += diaFinal;
            } else if (mesFinal > 1) {
                dias += ((mesFinal - 1) * 30) + diaFinal;
                //System.out.println("" + (((mesFinal - 1) * 30) + diaFinal));
            }
        }
        System.out.println("Dias: " + dias);
        aluguel.setValorTotal(dias * precoClasse);

    }

    public static boolean cadastrarAluguel(Aluguel aluguel) throws ClassNotFoundException {
        float valorClasse = 0;

        try {
            Connection conn = Conexoes.obterConexao();
            PreparedStatement stmt = null;

            //INSERT do Aluguel
            String sql = "INSERT INTO ALUGUEL "
                    + "(ID_FUNCIONARIO, ID_CARRO, ID_CLIENTE, DATA_LOCACAO_ALUGUEL, DATA_DEVOLUCAO_ALUGUEL, PRECO_TOTAL, DEVOLUCAO_ALUGUEL) "
                    + "VALUES (? ,?, ?, ?, ?, ?, '1')";
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
        }
        return false;
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
                ca.setId(rs.getInt("ID_CARRO"));
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
    
    public static List<Aluguel> perquisarAlugueisNaoDevolvidos(String modelo, String cpf, String nome) {
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
                + "AND CL.NOME_CLIENTE LIKE '%" + nome + "%' AND FI.NOME_FILIAL = '" + UsuarioDAO.usuario.getFilial() + "'"
                + "AND AL.DEVOLUCAO_ALUGUEL = '1'";
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
                ca.setId(rs.getInt("ID_CARRO"));
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

    public static void devolucaoAluguel(Aluguel aluguel) {
        String sql = "";

        try {
            Connection conn = Conexoes.obterConexao();
            PreparedStatement stmt = null;
            //Realiza update na data de devolução.
            sql = "UPDATE ALUGUEL SET DATA_DEVOLUCAO_ALUGUEL = CURRENT_DATE, DEVOLUCAO_ALUGUEL = '0' " +
"                    WHERE ID_ALUGUEL = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, aluguel.getId());

            stmt.execute();
            stmt.clearBatch();
            stmt.close();
            //Faz com que o carro fique novamente disponivel para locação.
            sql = "UPDATE CARRO SET DISPONIBILIDADE_CARRO = '1' "
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
    
    public static void alterarAluguel(Aluguel aluguel){
        String sql ="";
        try {
            Connection conn = Conexoes.obterConexao();
            PreparedStatement stmt = null;
            //Realiza update na data de devolução.
            sql = "UPDATE ALUGUEL SET DATA_DEVOLUCAO_ALUGUEL = CURRENT_DATE, DEVOLUCAO_ALUGUEL = '0' " +
"                    WHERE ID_ALUGUEL = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, aluguel.getId());

            stmt.execute();
            stmt.clearBatch();
            stmt.close();
            //Faz com que o carro fique novamente disponivel para locação.
            sql = "UPDATE CARRO SET DISPONIBILIDADE_CARRO = '1' "
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

    @Override
    public boolean verificaData(String dataInicio, String dataFinal) {
        int anoInicial = Integer.parseInt(dataInicio.substring(1, 4));
        int anoFinal = Integer.parseInt(dataFinal.substring(1, 4));
        int diaInicial = Integer.parseInt(dataInicio.substring(8, 10));
        int mesInicial = Integer.parseInt(dataInicio.substring(5, 7));
        int diaFinal = Integer.parseInt(dataFinal.substring(8, 10));
        int mesFinal = Integer.parseInt(dataFinal.substring(5, 7));
        if (anoInicial > anoFinal) {
            return false;
        } else if (anoInicial == anoFinal && mesInicial > mesFinal) {
            return false;
        } else if (anoInicial == anoInicial && mesInicial == mesFinal && diaInicial > diaFinal) {
            return false;
        }
        return true;
    }

    public static String verificaoes(Aluguel a) {
        AluguelDAO dao = new AluguelDAO();
        if (!dao.verificaData(a.getDataInicial(), a.getDataFinal())) {
            return "Data inválida!";
        }
        return null;
    }
}
