/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.ghosts.locarsys.controller;

import br.senac.tads.pi3.ghosts.locarsys.dao.ClienteDAO;
import br.senac.tads.pi3.ghosts.locarsys.model.Cliente;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Bruno
 */
@WebServlet(name = "ConsultaClientesServlet", urlPatterns = {"/ConsultaClientesServlet"})
public class ConsultaClientesServlet extends HttpServlet {
    
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
        RequestDispatcher disp = request.getRequestDispatcher("/Cliente/consultaAlterarCliente.jspx");
        disp.forward(request, response);
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
        processRequest(request, response);
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
        //Pega os valores para realizar a consulta do cliente
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        List<Cliente> listarClientes = ClienteDAO.pesquisarCliente(nome, cpf);
        request.setAttribute("clientes", listarClientes);
        request.setAttribute("nome", nome);
        request.setAttribute("cpf", cpf);
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a Strin//Pega os valores para realizar a consulta do cliente
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        List<Cliente> listarClientes = ClienteDAO.pesquisarCliente(nome);
        request.setAttribute("clientes", listarClientes);
        request.setAttribute("nome", nome);
        request.setAttribute("cpf", cpf);
        processRequest(request, response);g containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
