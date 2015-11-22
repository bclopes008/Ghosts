/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.ghosts.locarsys.dao;

import br.senac.tads.pi3.ghosts.locarsys.controller.Conexoes;
import br.senac.tads.pi3.ghosts.locarsys.model.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Bruno
 */
public class ProdutoDAO {

    public static ArrayList<Carro> listarCarrosDisponiveis(char grupo) {
        ArrayList<Carro> carros = new ArrayList<>();

        String sql = "SELECT * FROM CARRO CA "
                + "INNER JOIN CLASSE CL ON CA.ID_CLASSE = CL.ID_CLASSE "
                + "INNER JOIN FILIAL FI ON CA.ID_FILIAL = FI.ID_FILIAL "
                + "WHERE DISPONIBILIDADE_CARRO = '1' AND CL.TIPO_CLASSE = '" + grupo + "' "
                + "AND FI.NOME_FILIAL = '" + UsuarioDAO.usuario.getFilial() + "'";

        try {
            Connection conn = Conexoes.obterConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = null;

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Carro cars = new Carro();
                cars.setId(rs.getInt("ID_CARRO"));
                cars.setModelo(rs.getString("MODELO_CARRO"));
                cars.setPlaca(rs.getString("PLACA_CARRO"));
                cars.setGrupo(rs.getString("TIPO_CLASSE").charAt(0));
                carros.add(cars);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return carros;
    }

    public static ArrayList<Combustivel> listarCombustiveis() {
        ArrayList<Combustivel> combustiveis = new ArrayList<>();

        String sql = "SELECT * FROM Combustivel";

        try {
            Connection conn = Conexoes.obterConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = null;

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                combustiveis.add(new Combustivel(rs.getInt("ID_COMBUSTIVEL"),
                        rs.getString("TIPO_COMBUSTIVEL")));
            }

        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return combustiveis;
    }

    public static ArrayList<ClasseProduto> listarClasses() {
        ArrayList<ClasseProduto> classes = new ArrayList<>();

        String sql = "SELECT * FROM Classe";

        try {
            Connection conn = Conexoes.obterConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = null;

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                classes.add(new ClasseProduto(rs.getInt("ID_CLASSE"),
                        rs.getString("TIPO_CLASSE").charAt(0),
                        rs.getFloat("TARIFA_CLASSE")));
            }

        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return classes;
    }

    public static ArrayList<Filial> listarFiliais() {
        ArrayList<Filial> filiais = new ArrayList<>();

        String sql = "SELECT * FROM FILIAL";

        try {
            Connection conn = Conexoes.obterConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = null;

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                filiais.add(new Filial(rs.getInt("id_Filial"),
                        rs.getString("Nome_Filial"),
                        rs.getInt("Id_Estado"),
                        rs.getString("Cidade_Filial")));
            }

        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return filiais;
    }
}
