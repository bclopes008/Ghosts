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
        int fabricante = 0, combustivel = 0, classe = 0;
        //String fabricante = null, combustivel = null, classe = null;

        /* Busca o id do combustivel no banco de dados */
        String sql = "SELECT ID_COMBUSTIVEL FROM COMBUSTIVEL WHERE TIPO_COMBUSTIVEL = '" + c.getCombustivel() + "'";

        try {
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

            sql = "INSERT INTO CARRO (id_Classe, id_Fabricante, id_Combustivel, Ano_Fabricacao_Carro, Chassi_Carro, Cor_Carro, "
                    + "Modelo_Carro, Placa_Carro, Estado_Carro, Cidade_Carro, Ano_Carro, Renavam_Carro, Kilometragem_Carro, Disponibilidade_Carro)"
                    + " VALUES ( " + classe + ", " + fabricante + ", " + combustivel + ", '" + c.getAnoFabricacao() + "', '"
                    + c.getChassi() + "', '" + c.getCor() + "', '" + c.getModelo() + "', '" + c.getPlaca() + "', '" + c.getEstado() + "', '"
                    + c.getCidade() + "', '" + c.getAno() + "', '" + c.getRenavam() + "', " + c.getKilometragem() + ", '1' )";
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
            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("" + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("" + ex.getMessage());
        }
        return false;
    }
}
