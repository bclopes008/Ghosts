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

/**
 *
 * @author Prime-PC
 */
public class AluguelDAO {
    
    public ArrayList<Aluguel> consultaAluguel() {
        ArrayList<Aluguel> aluguel = new ArrayList<>();
        Aluguel aluguelObj = new Aluguel();

        try {
            DateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");
            Connection conn = Conexoes.obterConexao();
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Aluguel";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                aluguel.add(new Aluguel(formatadorData.parse(rs.getString("")), formatadorData.parse(rs.getString("")), Float.valueOf(rs.getString(""))));
            }
            conn.close();
            return aluguel;
        } catch (ClassNotFoundException | SQLException | DateTimeParseException ex) {
            System.err.println("" + ex.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(AluguelDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void cadastrarAluguel(Aluguel aluguel,String classeCarro, String idUsuario, String idCarro, String idCliente){
        try {
            Connection conn = Conexoes.obterConexao();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Aluguel VALUES(NEWID(), ?, ?, ?, ?, ?, ?, ?)");
            
            stmt.setString(1, idUsuario);
            stmt.setString(2, classeCarro);
            stmt.setString(3, idCarro);
            stmt.setString(4, idCliente);
            stmt.setString(5, aluguel.getDataInicial().toString());
            stmt.setString(6, aluguel.getDataFinal().toString());
            stmt.setString(7, ""+aluguel.getValorTotal());
            
            stmt.execute();
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("" + ex.getMessage());
        }
    }
    
    public void deletarAluguel(Aluguel aluguel, String campoBD){
        try {
            Connection conn = Conexoes.obterConexao();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Aluguel WHERE ? = ?");
            
            stmt.setString(1, "Preco_Total");
            stmt.setString(2, ""+aluguel.getValorTotal());
            
            stmt.execute();
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("" + ex.getMessage());
        }
    }
}
