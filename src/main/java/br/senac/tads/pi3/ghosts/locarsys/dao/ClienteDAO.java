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
        int idCliente = 0, estado = 0;
        
        /* INSERT do Cliente */
        String sql = "INSERT INTO CLIENTE (NOME_CLIENTE, CPF_CLIENTE, CNH_CLIENTE, DATA_NASC_CLIENTE, SEXO_CLIENTE, CELULAR_CLIENTE, EMAIL_CLIENTE) VALUES ('" + c.getNome() + "', '"
                + c.getCpf() + "', '" + c.getCnh() + "', '" + c.getDataNascimento() + "', '" + c.getSexo() + "', '" + c.getCelular() + "', '" + c.getEmail() + "')";
        try {
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.close();
            
            /* Buscar id do Cliente, para o INSERT do Endereço */
            sql = "SELECT ID_CLIENTE FROM CLIENTE WHERE CPF_CLIENTE = '" + c.getCpf() + "'";
            
            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                idCliente = rs.getInt("ID_CLIENTE");
            }
            conn.close();
            
            /* Buscar id do Estado, para o INSERT do Endereço */
            sql = "SELECT ID_ESTADO FROM ESTADO WHERE SIGLA_ESTADO = '" + c.getEstado() + "'";

            conn = Conexoes.obterConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                estado = rs.getInt("ID_ESTADO");
            }
            conn.close();
            
            sql = "INSERT INTO Endereco(ID_CLIENTE, ID_ESTADO, LOGRADOURO_ENDERECO, NUMERO_ENDERECO, BAIRRO_ENDERECO, COMPLEMENTO_ENDERECO, CEP_ENDERECO, OBS_ENDERECO) VALUES("
                    + idCliente + ", " + estado + ", '" + c.getEndereco() + "', '" + c.getNumero() + "', '" + c.getBairro() + "', '" + c.getComplemento() + "', '" + c.getCep() + "', '"
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
