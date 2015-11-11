/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.ghosts.locarsys.controller;

import br.senac.tads.pi3.ghosts.locarsys.dao.AluguelDAO;
import br.senac.tads.pi3.ghosts.locarsys.model.Aluguel;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Prime-PC
 */
@WebServlet(name = "CadastroAlugueisServlet", urlPatterns = {"/CadastroAlugueisServlet"})
public class CadastroAluguelServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");

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
        
        RequestDispatcher disp = request.getRequestDispatcher("/Aluguel/cadastroAlugueis.jspx");
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
        
        Aluguel aluguel = new Aluguel();
        
        aluguel.setDataFinal(request.getParameter("final"));
        aluguel.setDataInicial(request.getParameter("inicial"));
        aluguel.setCliente(request.getParameter("cpf"), request.getParameter("cnh"), request.getParameter("nomeCliente"));
        aluguel.setCarro(request.getParameter("grupo").charAt(0), request.getParameter("modelo"));
        
        AluguelDAO.calcularValorTotal(aluguel);
        
        RequestDispatcher disp = null;
        try {
            if(AluguelDAO.cadastrarAluguel(aluguel)){
                disp = request.getRequestDispatcher("/Principal/telaPrincipal.jspx");
            }
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        
        disp = request.getRequestDispatcher("/Aluguel/cadastroAlugueis.jspx");
        disp.forward(request, response);

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
