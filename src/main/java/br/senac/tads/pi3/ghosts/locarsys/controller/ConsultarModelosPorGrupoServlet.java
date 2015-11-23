/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.ghosts.locarsys.controller;

import br.senac.tads.pi3.ghosts.locarsys.dao.ClienteDAO;
import br.senac.tads.pi3.ghosts.locarsys.dao.ProdutoDAO;
import br.senac.tads.pi3.ghosts.locarsys.dao.UsuarioDAO;
import br.senac.tads.pi3.ghosts.locarsys.model.Aluguel;
import br.senac.tads.pi3.ghosts.locarsys.model.Carro;
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
import javax.websocket.ClientEndpoint;

/**
 *
 * @author Bruno
 */
@WebServlet(name = "ConsultarModelosPorGrupoServlet", urlPatterns = {"/ConsultarModelosPorGrupoServlet"})
public class ConsultarModelosPorGrupoServlet extends HttpServlet {

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
        if(UsuarioDAO.usuario != null)
            request.setAttribute("usuario", UsuarioDAO.usuario);
        RequestDispatcher disp = request.getRequestDispatcher("CadastroAluguelServlet");
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
        
        List<Cliente> clientes = ClienteDAO.pesquisarCliente("", request.getParameter("cpf"));
        
        char grp = request.getParameter("grupo").charAt(0);
        
        ArrayList<Carro> carros = ProdutoDAO.listarCarrosDisponiveis(grp);
        request.setAttribute("carros", carros);
        request.setAttribute("grupo", grp);
        
        Aluguel aluguel = new Aluguel();
        
        Cliente c1 = clientes.get(0);
//        c1.setId(Integer.parseInt(request.getParameter("idCliente")));
        aluguel.setCliente(c1);

        aluguel.setDataFinal(request.getParameter("final"));
        aluguel.setDataInicial(request.getParameter("inicial"));
//        aluguel.setValorTotal(Float.parseFloat(request.getParameter("valorTotal")));
        
        request.setAttribute("aluguel", aluguel);
        
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
