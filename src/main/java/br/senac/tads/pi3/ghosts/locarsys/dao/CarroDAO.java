package br.senac.tads.pi3.ghosts.locarsys.dao;

import br.senac.tads.pi3.ghosts.locarsys.controller.Conexoes;
import br.senac.tads.pi3.ghosts.locarsys.model.Carro;
import br.senac.tads.pi3.ghosts.locarsys.model.VerificacoesCarro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class CarroDAO implements VerificacoesCarro {

    public static boolean cadastraCarro(Carro c) {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        int fabricante = 0, combustivel = 0, classe = 0, filial = 0, estado = 0;

        /* Busca o id da filial no banco de dados */
        String sql = "SELECT ID_FILIAL FROM FILIAL WHERE NOME_FILIAL = '" + c.getFilial() + "'";
        System.out.println("Nome Filial: " + c.getFilial());

        try {
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                filial = rs.getInt("ID_FILIAL");
            }
            /* Busca o id do combustivel no banco de dados */
            sql = "SELECT ID_COMBUSTIVEL FROM COMBUSTIVEL WHERE TIPO_COMBUSTIVEL = '" + c.getCombustivel() + "'";
            System.out.println("ID Filial: " + filial);
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                combustivel = rs.getInt("ID_COMBUSTIVEL");
            }
            conn.close();
            /* Buscar id do Fabricante, para o INSERT do Carro */
            sql = "SELECT ID_FABRICANTE FROM FABRICANTE WHERE NOME_FABRICANTE = '" + c.getMarca() + "'";

            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                fabricante = rs.getInt("ID_FABRICANTE");
            }
            conn.close();
            /* Buscar id da Classe, para o INSERT do Carro */
            sql = "SELECT ID_CLASSE FROM CLASSE WHERE TIPO_CLASSE = '" + c.getGrupo() + "'";

            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                classe = rs.getInt("ID_CLASSE");
            }
            conn.close();

            /* Buscar id do Estado, para o INSERT do Carro */
            sql = "SELECT ID_ESTADO FROM ESTADO WHERE SIGLA_ESTADO = '" + c.getEstado() + "'";

            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                estado = rs.getInt("ID_ESTADO");
            }
            conn.close();

            /*Insere os valores no banco de dados*/
            sql = "INSERT INTO CARRO (id_filial, id_Classe, id_Fabricante, id_Combustivel, id_Estado, Ano_Fabricacao_Carro, Chassi_Carro, Cor_Carro, "
                    + "Modelo_Carro, Placa_Carro, Cidade_Carro, Ano_Carro, Renavam_Carro, Kilometragem_Carro, Disponibilidade_Carro)"
                    + " VALUES ( " + filial + ", " + classe + ", " + fabricante + ", " + combustivel + ", " + estado + ", '" + c.getAnoFabricacao() + "', '"
                    + c.getChassi() + "', '" + c.getCor() + "', '" + c.getModelo() + "', '" + c.getPlaca() + "', '" + c.getCidade() + "', '" + c.getAno()
                    + "', '" + c.getRenavam() + "', " + c.getKilometragem() + ", '1' )";
            /*sql = "INSERT INTO CARRO (id_Classe, id_Fabricante, id_Combustivel, Ano_Fabricacao_Carro, Chassi_Carro, Cor_Carro, "
             + "Modelo_Carro, Placa_Carro, Estado_Carro, Cidade_Carro ,Disponibilidade_Carro, Ano_Carro, Renavam_Carro, "
             + "Kilometragem_Carro) VALUES (? , ? , ? , '?' , '?' , '?' , '?' , '?' , '?' , '?' , '?' , '?' , ? , '1')";
            
             conn = Conexoes.obterConexao();
             stmt = conn.prepareStatement(sql);
             stmt.setInt(1, classe);
             stmt.setInt(2, fabricante);
             stmt.setInt(3, combustivel);
             stmt.setObject(4, c.getAnoFabricacao());
             stmt.setObject(5, c.getChassi());
             stmt.setString(6, c.getCor());
             stmt.setString(7, c.getModelo());
             stmt.setString(8, c.getPlaca());
             stmt.setString(9, c.getEstado());
             stmt.setString(10, c.getCidade());
             stmt.setInt(11, c.getAno());
             stmt.setString(12, c.getRenavam());
             stmt.setFloat(13, c.getKilometragem());
             stmt.executeUpdate(sql);*/
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.close();

            sql = "SELECT ID_CARRO FROM CARRO WHERE PLACA_CARRO = '" + c.getPlaca() + "'";

            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                c.setId(rs.getInt("ID_CARRO"));
            }
            conn.close();

            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("" + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("" + ex.getMessage());
        }
        return false;
    }

    public static Carro consultarCarroDisponiveis() {
        Carro c = new Carro();

        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;

        /* Busca o id da filial no banco de dados */
        String sql = "SELECT * FROM CARRO WHERE DISPONIBILIDADE_CARRO = '1';";

        try {
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {

            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CarroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

    public static List<Carro> pesquisarCarro(String modelo, String grupo) {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;

        /* Pesquisa Carro */
        String sql = "SELECT * FROM CARRO CA "
                + "INNER JOIN CLASSE CL ON CA.ID_CLASSE = CL.ID_CLASSE "
                + "WHERE CA.MODELO_CARRO LIKE '%" + modelo + "%' AND CL.TIPO_CLASSE LIKE '%" + grupo + "%' "
                + "ORDER BY MODELO_CARRO";
        try {
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            ArrayList<Carro> carros = new ArrayList<>();
            while (rs.next()) {
                Carro c = new Carro();
                c.setId(rs.getInt("ID_CARRO"));
                c.setModelo(rs.getString("MODELO_CARRO"));
                c.setGrupo(rs.getString("TIPO_CLASSE").charAt(0));
                c.setPlaca(rs.getString("PLACA_CARRO"));
                carros.add(c);
            }
            conn.close();
            return carros;
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Erro: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return null;
    }

    public static Carro verCarro(int id) {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;

        /* Pesquisa Usuario com o id para ir na tela de alteração*/
        String sql = "SELECT * FROM CARRO CA "
                + "INNER JOIN FILIAL FI ON CA.ID_FILIAL = FI.ID_FILIAL "
                + "INNER JOIN CLASSE CL ON CA.ID_CLASSE = CL.ID_CLASSE "
                + "INNER JOIN FABRICANTE FA ON CA.ID_FABRICANTE = FA.ID_FABRICANTE "
                + "INNER JOIN COMBUSTIVEL CO ON CA.ID_COMBUSTIVEL = CO.ID_COMBUSTIVEL "
                + "INNER JOIN ESTADO ES ON CA.ID_ESTADO = ES.ID_ESTADO "
                + "WHERE CA.ID_CARRO = " + id + "";
        try {
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            Carro c = new Carro();
            while (rs.next()) {
                c.setId(rs.getInt("ID_CARRO"));
                c.setAnoFabricacao(rs.getInt("ANO_FABRICACAO_CARRO"));
                c.setChassi(rs.getString("CHASSI_CARRO"));
                c.setCor(rs.getString("COR_CARRO"));
                c.setModelo(rs.getString("MODELO_CARRO"));
                c.setPlaca(rs.getString("PLACA_CARRO"));
                c.setCidade(rs.getString("CIDADE_CARRO"));
                c.setAno(rs.getInt("ANO_CARRO"));
                c.setRenavam(rs.getString("RENAVAM_CARRO"));
                c.setKilometragem(rs.getFloat("KILOMETRAGEM_CARRO"));
                c.setFilial(rs.getString("NOME_FILIAL"));
                c.setGrupo(rs.getString("TIPO_CLASSE").charAt(0));
                c.setMarca(rs.getString("NOME_FABRICANTE"));
                c.setCombustivel(rs.getString("TIPO_COMBUSTIVEL"));
                c.setEstado(rs.getString("SIGLA_ESTADO"));
            }
            conn.close();
            return c;
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Erro: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Erro: " + ex.getMessage());
        }
        return null;
    }

    public static boolean alteraCarro(Carro c) {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        int filial = 0, classe = 0, fabricante = 0, combustivel = 0, estado = 0;

        String sql = "SELECT ID_FILIAL FROM FILIAL WHERE NOME_FILIAL = '" + c.getFilial() + "'";
        try {
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                filial = rs.getInt("ID_FILIAL");
            }
            conn.close();

            sql = "SELECT ID_CLASSE FROM CLASSE WHERE TIPO_CLASSE = '" + c.getGrupo() + "'";
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                classe = rs.getInt("ID_CLASSE");
            }
            conn.close();

            sql = "SELECT ID_FABRICANTE FROM FABRICANTE WHERE NOME_FABRICANTE = '" + c.getMarca() + "'";
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                fabricante = rs.getInt("ID_FABRICANTE");
            }
            conn.close();

            sql = "SELECT ID_COMBUSTIVEL FROM COMBUSTIVEL WHERE TIPO_COMBUSTIVEL = '" + c.getCombustivel() + "'";
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                combustivel = rs.getInt("ID_COMBUSTIVEL");
            }
            conn.close();

            sql = "SELECT ID_ESTADO FROM ESTADO WHERE SIGLA_ESTADO = '" + c.getEstado() + "'";
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                estado = rs.getInt("ID_ESTADO");
            }
            conn.close();

            sql = "UPDATE CARRO SET ID_FILIAL = " + filial + ", ID_CLASSE = " + classe + ", ID_FABRICANTE = " + fabricante + ", "
                    + "ID_COMBUSTIVEL = " + combustivel + ", ID_ESTADO = " + estado + ", ANO_FABRICACAO_CARRO = '" + c.getAnoFabricacao() + "', CHASSI_CARRO = '" + c.getChassi() + "', "
                    + "COR_CARRO = '" + c.getCor() + "', MODELO_CARRO = '" + c.getModelo() + "', PLACA_CARRO = '" + c.getPlaca() + "', CIDADE_CARRO = '" + c.getCidade() + "', "
                    + "ANO_CARRO = '" + c.getAno() + "', RENAVAM_CARRO = '" + c.getRenavam() + "', KILOMETRAGEM_CARRO = " + c.getKilometragem() + " "
                    + "WHERE ID_CARRO = " + c.getId() + "";
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
    public boolean verificaChassi(String chassi) {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;

        String sql = "SELECT CHASSI_CARRO FROM CARRO "
                + "WHERE CHASSI_CARRO = '" + chassi + "'";
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

    @Override
    public boolean verificaPlaca(String placa) {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;

        String sql = "SELECT PLACA_CARRO FROM CARRO "
                + "WHERE PLACA_CARRO = '" + placa + "'";
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

    @Override
    public boolean verificaRenavam(String renavam) {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;

        String sql = "SELECT RENAVAM_CARRO FROM CARRO "
                + "WHERE RENAVAM_CARRO = '" + renavam + "'";
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
    
    public static String verificaoes(Carro c) {
        CarroDAO dao = new CarroDAO();
        if (dao.verificaChassi(c.getChassi())) {
            return "Já existe esse Chassi cadastrado!";
        } else if (dao.verificaPlaca(c.getPlaca())) {
            return "Já existe essa Placa cadastrada!";
        } else if (dao.verificaRenavam(c.getRenavam())) {
            return "Já existe esse Renavam cadastrado!";
        }
        return null;
    }

}
