/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.ghosts.locarsys.controller;

import br.senac.tads.pi3.ghosts.locarsys.dao.AluguelDAO;
import br.senac.tads.pi3.ghosts.locarsys.dao.ClienteDAO;
import br.senac.tads.pi3.ghosts.locarsys.dao.UsuarioDAO;
import br.senac.tads.pi3.ghosts.locarsys.model.Aluguel;
import br.senac.tads.pi3.ghosts.locarsys.model.Carro;
import br.senac.tads.pi3.ghosts.locarsys.model.Cliente;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vinicius.vsantos
 */
public class DevolucaoAluguelServlet extends HttpServlet {

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
        if (UsuarioDAO.usuario != null) {
            request.setAttribute("usuario", UsuarioDAO.usuario);
        }
        RequestDispatcher disp = request.getRequestDispatcher("/Aluguel/devolucaoAluguel.jspx");
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
        /*Aluguel aluguel = new Aluguel();
        aluguel.setId(Integer.parseInt(request.getParameter("idaluguel")));

        AluguelDAO.devolucaoAluguel(aluguel);*/

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
        Aluguel aluguel = new Aluguel();
        Carro ca = new Carro();
        ca.setModelo(request.getParameter("modelo"));
        aluguel.setCarro(ca);

        List<Cliente> clientes = ClienteDAO.pesquisarCliente(request.getParameter("nome"), request.getParameter("cpf"));
        aluguel.setCliente(clientes.get(0));

        List<Aluguel> alugueis = AluguelDAO.perquisarAluguel(aluguel.getCarro().getModelo(), aluguel.getCliente().getCpf(), aluguel.getCliente().getNome());
        request.setAttribute("aluguel", alugueis);
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