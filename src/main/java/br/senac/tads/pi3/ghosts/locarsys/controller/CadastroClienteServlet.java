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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bruno.lopes
 */
@WebServlet(name = "CadastroClienteServlet", urlPatterns = {"/CadastroClienteServlet"})
public class CadastroClienteServlet extends HttpServlet {

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
        RequestDispatcher disp = request.getRequestDispatcher("/Cliente/cadastrarCliente.jspx");
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
        RequestDispatcher disp;
        Cliente c = new Cliente();
        c.setNome(request.getParameter("nome"));
        c.setCpf(request.getParameter("cpf"));
        c.setDataNascimento(request.getParameter("nascimento"));
        c.setCnh(request.getParameter("cnh"));
        c.setSexo(request.getParameter("sexo").charAt(0));
        c.setCelular(request.getParameter("celular"));
        c.setEmail(request.getParameter("email"));
        c.setCep(request.getParameter("cep"));
        c.setEndereco(request.getParameter("endereco"));
        c.setNumero(request.getParameter("numero"));
        c.setBairro(request.getParameter("bairro"));
        c.setEstado(request.getParameter("estado"));
        c.setCidade(request.getParameter("cidade"));
        c.setComplemento(request.getParameter("complemento"));
        c.setObs(request.getParameter("observacoes"));
        ClienteDAO dao = new ClienteDAO();
        if(dao.cadastroCliente(c))
        {
            disp = request.getRequestDispatcher("Principal");
            request.setAttribute("mensagem","Cliente cadastrado com sucesso!");
        }
        else
            disp = request.getRequestDispatcher("/Cliente/cadastrarCliente.jspx");
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
