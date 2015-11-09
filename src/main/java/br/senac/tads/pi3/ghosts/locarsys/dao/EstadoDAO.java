/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.ghosts.locarsys.dao;

import br.senac.tads.pi3.ghosts.locarsys.controller.Conexoes;
import br.senac.tads.pi3.ghosts.locarsys.model.Estado;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Bruno
 */
public class EstadoDAO {
    
    public static ArrayList<Estado> listarEstados() {
        ArrayList<Estado> estados = new ArrayList<>();

        String sql = "SELECT * FROM ESTADO";
        
        try {
            Connection conn = Conexoes.obterConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = null;
            
            rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                estados.add(new Estado(rs.getString("NOME_ESTADO"), rs.getString("SIGLA_ESTADO")));
            }
            
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return estados;
    }
    
}
