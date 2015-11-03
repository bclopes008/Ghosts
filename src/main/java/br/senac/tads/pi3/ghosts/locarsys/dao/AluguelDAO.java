/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.ghosts.locarsys.dao;

import br.senac.tads.pi3.ghosts.locarsys.model.Aluguel;
import java.util.ArrayList;
import br.senac.tads.pi3.ghosts.locarsys.controller.Conexoes;
import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Prime-PC
 */
public class AluguelDAO {

    public ArrayList<Aluguel> consultaAluguel() throws ClassNotFoundException {
        ArrayList<Aluguel> aluguel = new ArrayList<>();
        Aluguel aluguelObj = new Aluguel();

        try {
            DateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");
            Connection conn = Conexoes.obterConexao();
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM ALUGUEL";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                aluguel.add(new Aluguel(formatadorData.parse(rs.getString("")), formatadorData.parse(rs.getString("")), Float.valueOf(rs.getString(""))));
            }
            conn.close();
            return aluguel;
        } catch (SQLException | DateTimeParseException | ParseException ex) {
            System.err.println("" + ex.getMessage());
        }
        return null;
    }

    public void cadastrarAluguel(Aluguel aluguel, String idUsuario, String idCarro, String idCliente) throws ClassNotFoundException {
        String sql = "INSERT INTO ALUGUEL VALUES(?, ?, ?, ?, ?, ?)";
        float valorClasse = 0;
        try {
            Connection conn = Conexoes.obterConexao();
            PreparedStatement stmt = null;
            //SELECT Para pegar o valor e nome do carro
            sql = "SELECT * FROM CARRO CA INNER JOIN CLASSE CL \n"
                    + "ON CL.ID_CLASSE = CA.ID_CLASSE WHERE ID_CARRO = ? FETCH FIRST 1 ROW ONLY;";
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, idCarro);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                valorClasse = rs.getFloat("TARIFA_CLASSE");
            }

            //INSERT do Aluguel
            stmt.clearBatch();
            sql = "INSERT INTO ALUGUEL VALUES(?, ?, ?, ?, ?, ?);";
            stmt = conn.prepareStatement(sql);

            aluguel.calcularValorTotal(valorClasse);

            stmt.setString(1, idUsuario);
            stmt.setString(2, idCarro);
            stmt.setString(3, idCliente);
            stmt.setDate(4, (Date) aluguel.getDataInicial());
            stmt.setDate(5, (Date) aluguel.getDataFinal());
            stmt.setFloat(6, aluguel.getValorTotal());
            System.out.println("Data final: " + (Date) aluguel.getDataFinal());
            stmt.executeUpdate();

            //UPDATE Disponibilidade
            stmt.clearBatch();

            stmt.close();
            conn.close();
        } catch (SQLException ex) {
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
}
