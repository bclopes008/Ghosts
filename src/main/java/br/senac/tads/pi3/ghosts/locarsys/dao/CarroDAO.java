package br.senac.tads.pi3.ghosts.locarsys.dao;

import br.senac.tads.pi3.ghosts.locarsys.controller.Conexoes;
import br.senac.tads.pi3.ghosts.locarsys.model.Carro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class CarroDAO {

    public boolean cadastraCarro(Carro c) {
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
}
