/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.ghosts.locarsys.dao;

import br.senac.tads.pi3.ghosts.locarsys.controller.Conexoes;
import br.senac.tads.pi3.ghosts.locarsys.model.Cliente;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Bruno
 */
public class ClienteDAO {

    public boolean cadastroCliente(Cliente c) {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        int idCliente = 0;
        String dataNasc = "" + c.getDataNascimento();
        System.out.println("Passou aqui: " + c.getEstado());  //TODO
        String sql = "INSERT INTO CLIENTE (NOME_CLIENTE, CPF_CLIENTE, CNH_CLIENTE, DATA_NASC_CLIENTE, SEXO_CLIENTE, CELULAR_CLIENTE, EMAIL_CLIENTE) VALUES ('" + c.getNome() + "', '"
                + c.getCpf() + "', '" + c.getCnh() + "', '" + dataNasc + "', '" + c.getSexo() + "', '" + c.getCelular() + "', '" + c.getEmail() + "')";
        try {
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.close();
            System.out.println("Passou aqui: " + c.getNome());  //TODO
            sql = "SELECT ID_CLIENTE FROM CLIENTE WHERE CPF_CLIENTE = '" + c.getCpf() + "'";
            
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                idCliente = rs.getInt("ID_CLIENTE");
            }
            conn.close();
            System.out.println("Passou aqui: " + idCliente);  //TODO
            sql = "INSERT INTO Endereco(ID_CLIENTE, LOGRADOURO_ENDERECO, NUMERO_ENDERECO, BAIRRO_ENDERECO, COMPLEMENTO_ENDERECO, CEP_ENDERECO, OBS_ENDERECO) VALUES("
                    + idCliente + ", '" + c.getEndereco() +"', '" + c.getNumero() + "', '" + c.getBairro() + "', '" + c.getComplemento() + "', '" + c.getCep() + "', '"
                    + c.getObs() + "')";
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.close();
            System.out.println("Passou aqui: " + c.getObs());
            return true;
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("" + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("" + ex.getMessage());
        }
        return false;
    }

}
