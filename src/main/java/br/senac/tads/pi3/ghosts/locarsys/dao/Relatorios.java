/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.ghosts.locarsys.dao;

import br.senac.tads.pi3.ghosts.locarsys.controller.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.jdbc.JDBCCategoryDataset;

/**
 *
 * @author vinicius.vsantos
 */
public class Relatorios {

    public static void relatoriosDisponibilidade() {
        String sql = "SELECT * FROM CARRO CA "
                + "INNER JOIN CLASSE CL ON CL.ID_CLASSE = CA.ID_CLASSE "
                + "INNER JOIN FABRICANTE FB ON FB.ID_FABRICANTE = CA.ID_FABRICANTE "
                + "INNER JOIN COMBUSTIVEL CO ON CO.ID_COMBUSTIVEL = CA.ID_COMBUSTIVEL "
                + "INNER JOIN FILIAL FL ON FL.ID_FILIAL = CA.ID_FILIAL "
                + "WHERE DISPONIBILIDADE_CARRO = '1';";

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

    public static void relatoriosVendas() {
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
