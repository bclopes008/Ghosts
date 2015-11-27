/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.ghosts.locarsys.dao;

import br.senac.tads.pi3.ghosts.locarsys.controller.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.jdbc.JDBCCategoryDataset;

/**
 *
 * @author vinicius.vsantos
 */
public class Relatorios {

    public static void relatoriosDisponibilidade() throws SQLException, ClassNotFoundException {
        String query = "SELECT FL.NOME_FILIAL, COUNT(FL.NOME_FILIAL) AS QUANTIDADE FROM CARRO CA "
                + "INNER JOIN FILIAL FL ON FL.ID_FILIAL = CA.ID_FILIAL "
                + "WHERE CA.DISPONIBILIDADE_CARRO = '1' "
                + "GROUP BY FL.NOME_FILIAL";
        Connection conn = Conexoes.obterConexao();
        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        DefaultCategoryDataset ds = new DefaultCategoryDataset();

        while (rs.next()) {
            ds.addValue(rs.getInt("QUANTIDADE"), "Quantidade", rs.getString("NOME_FILIAL"));
        }

        /*File fg = new File("C:\\Users\\bruno.lopes.KRONMED\\Documents\\NetBeansProjects\\LoCarSys\\src\\main\\webapp\\ImagensLoCarSys\\disponibilidade.png");
         fg.delete();*/
        JFreeChart grafico = ChartFactory.createBarChart3D("Relatório de Disponibilidade", "Filiais",
                "Separados por filiais", ds, PlotOrientation.VERTICAL, true, true, false);

        //try (OutputStream arquivo = new FileOutputStream("C:\\Users\\bruno.clopes\\Documents\\NetBeansProjects\\LoCarSys\\target\\LoCarSys-1.0-SNAPSHOT\\ImagensLoCarSys\\disponibilidade.png")) {
        try (OutputStream arquivo = new FileOutputStream("ImagensLoCarSys\\disponibilidade.png")) {
            ChartUtilities.writeChartAsPNG(arquivo, grafico, 800, 600);
        } catch (FileNotFoundException ex) {
            System.out.println("" + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("" + ex.getMessage());
        }
        
        try (OutputStream arquivo = new FileOutputStream("C:\\Users\\bruno.clopes\\Documents\\NetBeansProjects\\LoCarSys\\target\\LoCarSys-1.0-SNAPSHOT\\ImagensLoCarSys\\disponibilidade.png")) {
            ChartUtilities.writeChartAsPNG(arquivo, grafico, 800, 600);
        } catch (FileNotFoundException ex) {
            System.out.println("" + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("" + ex.getMessage());
        }
    }

    public static void relatoriosVendas() throws SQLException, ClassNotFoundException {
        String query = "SELECT FL.NOME_FILIAL, COUNT(FL.NOME_FILIAL) AS QUANTIDADE FROM FILIAL FL "
                + "INNER JOIN FUNCIONARIO FUNC ON FUNC.ID_FILIAL = FL.ID_FILIAL "
                + "INNER JOIN ALUGUEL AL ON AL.ID_FUNCIONARIO = FUNC.ID_FUNCIONARIO "
                + "GROUP BY FL.NOME_FILIAL";
        Connection conn = Conexoes.obterConexao();
        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        DefaultCategoryDataset ds = new DefaultCategoryDataset();

        while (rs.next()) {
            ds.addValue(rs.getInt("QUANTIDADE"), "Quantidade", rs.getString("NOME_FILIAL"));
        }

        JFreeChart grafico = ChartFactory.createBarChart3D("Relatório de Aluguéis", "Filiais",
                "Separadas por filiais", ds, PlotOrientation.VERTICAL, true, true, false);

        
        try (OutputStream arquivo = new FileOutputStream("ImagensLoCarSys\\vendas.png")) {
            ChartUtilities.writeChartAsPNG(arquivo, grafico, 800, 600);
        } catch (FileNotFoundException ex) {
            System.out.println("" + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("" + ex.getMessage());
        }
        
        try (OutputStream arquivo = new FileOutputStream("C:\\Users\\bruno.clopes\\Documents\\NetBeansProjects\\LoCarSys\\target\\LoCarSys-1.0-SNAPSHOT\\ImagensLoCarSys\\vendas.png")) {
            ChartUtilities.writeChartAsPNG(arquivo, grafico, 800, 600);
        } catch (FileNotFoundException ex) {
            System.out.println("" + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("" + ex.getMessage());
        }
    }

    public static void relatoriosFiliais() {
        String sql = "";

        try {
            Connection conn = Conexoes.obterConexao();

            JDBCCategoryDataset ds = new JDBCCategoryDataset(conn, sql);

            ds.executeQuery(sql);

            JFreeChart grafico = ChartFactory.createLineChart("Gráfico de Disponibilidade",
                    "Filial", "Quantidade", ds, PlotOrientation.HORIZONTAL, true, true, true);

        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
