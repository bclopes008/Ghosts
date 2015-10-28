package br.senac.tads.pi3.ghosts.locarsys.dao;

import br.senac.tads.pi3.ghosts.locarsys.controller.Conexoes;
import br.senac.tads.pi3.ghosts.locarsys.model.Carro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class CarroDAO {
    
    public boolean cadastraCarro(Carro c)
    {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        String fabricante = null, combustivel = null, classe = null;
        
        /* Busca o id do combustivel no banco de dados */
        String sql = "SELECT TOP 1 ID_COMBUSTIVEL FROM COMBUSTIVEL WHERE TIPO_COMBUSTIVEL = ? ";
                
        try {
            conn = Conexoes.obterConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setObject(1, c.getCombustivel());
            stmt.executeQuery();
            
            while (rs.next()) {
                combustivel = rs.getString("ID_COMBUSTIVEL");
            }
            
            /* Buscar id do Fabricante, para o INSERT do Carro */
            sql = "SELECT TOP 1 ID_FABRICANTE FROM FABRICANTE WHERE NOME_FABRICANTE = ? ";
        
            stmt = conn.prepareStatement(sql);
            stmt.setObject(1, c.getMarca());
            stmt.executeQuery();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                fabricante = rs.getString("ID_FABRICANTE");
            }
            
            /* Buscar id da Classe, para o INSERT do Carro */
            sql = "SELECT TOP 1 ID_CLASSE FROM CLASSE WHERE TIPO_CLASSE = ?";
        
            stmt = conn.prepareStatement(sql);
            stmt.setObject(1, c.getGrupo());
            stmt.executeQuery();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
            classe = rs.getString("ID_CLASSE");
            }
            
            sql = "INSERT INTO Carro VALUES (NEWID(), ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , 1)";
            
            stmt = conn.prepareStatement(sql);
            stmt.setObject(1, classe);
            stmt.setObject(2, fabricante);
            stmt.setObject(3, combustivel);
            stmt.setObject(4, c.getAnoFabricacao());
            stmt.setObject(5, c.getChassi());
            stmt.setObject(6, c.getCor());
            stmt.setObject(7, c.getModelo());
            stmt.setObject(8, c.getPlaca());
            stmt.setObject(9, c.getEstado());
            stmt.setObject(10, c.getAno());
            stmt.setObject(11, c.getRenavam());
            stmt.setObject(12, c.getKilometragem());
            stmt.executeQuery();
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro SQL" + ex.getMessage(),
                    "ERRO SQL", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CarroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
