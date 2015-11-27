/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.ghosts.locarsys.controller;

import br.senac.tads.pi3.ghosts.locarsys.dao.Relatorios;
import br.senac.tads.pi3.ghosts.locarsys.dao.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vinicius.vsantos
 */
@WebServlet(name = "ExibirRelatóriosServlet", urlPatterns = {"/ExibirRelat_riosServlet"})
public class ExibirRelatoriosServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Para Verifica se o usuário possui acesso a essa página
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Relatorios.relatoriosDisponibilidade();
            Relatorios.relatoriosVendas();
        } catch (SQLException ex) {
            Logger.getLogger(ExibirRelatoriosServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ExibirRelatoriosServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        RequestDispatcher disp = null;
        
        if(request.getParameter("relatorio").equals("2")){
            disp = request.getRequestDispatcher("Relatorios/relatorioDisponibilidade.jspx");
        }else{
            disp = request.getRequestDispatcher("Relatorios/relatorioVendas.jspx");
        }
        
        if (UsuarioDAO.usuario != null) {
            request.setAttribute("usuario", UsuarioDAO.usuario);
        }
        disp.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
