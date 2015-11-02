/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.ghosts.locarsys.dao;

import br.senac.tads.pi3.ghosts.locarsys.model.Fabricante;
import java.util.ArrayList;
import br.senac.tads.pi3.ghosts.locarsys.controller.Conexoes;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bruno
 */
public class FabricanteDAO {

    public ArrayList<Fabricante> listarFabricantes() {
        ArrayList<Fabricante> fabricantes = new ArrayList<>();

        String sql = "SELECT * FROM Fabricante";
        
        try {
            Connection conn = Conexoes.obterConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = null;
            
            rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                fabricantes.add(new Fabricante (rs.getInt("ID_FABRICANTE"), rs.getString("NOME_FABRICANTE")));
            }
            
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return fabricantes;
    }

}
