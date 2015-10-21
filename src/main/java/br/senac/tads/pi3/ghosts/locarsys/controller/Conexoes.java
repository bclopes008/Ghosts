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
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            
            conn = DriverManager.getConnection("jdbc:sqlserver://BRUNO-PC:1433;databaseName=LOCARSYS;user=user1;password=1234");
            
        } catch (SQLException ex) {
            System.out.println("Conexão não realizada pelo motivo: " + ex.getMessage());
            System.out.println("Conexão não realizada pelo motivo: " + ex.getErrorCode());
            System.out.println("Conexão não realizada pelo motivo: " + ex.getCause());
        } catch(Exception e){
            System.err.println("Erro: "+e.getMessage());
        }
        return conn;
    }
}
