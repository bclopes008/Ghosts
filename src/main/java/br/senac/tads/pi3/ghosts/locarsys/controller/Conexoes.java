/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.ghosts.locarsys.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vinicius.vsantos
 */
public class Conexoes {
    public static Connection obterConexao() throws ClassNotFoundException {
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1527/locarsys;IntegratedSecurity=true");
        } catch (SQLException ex) {
            System.err.println("Erro: "+ex.getMessage());
        }
        return conn;
    }
}
