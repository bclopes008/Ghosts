/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.ghosts.locarsys.controller;

import br.senac.tads.pi3.ghosts.locarsys.dao.ProdutoDAO;
import br.senac.tads.pi3.ghosts.locarsys.dao.UsuarioDAO;
import br.senac.tads.pi3.ghosts.locarsys.model.Filial;
import br.senac.tads.pi3.ghosts.locarsys.model.Usuario;
import java.io.IOException;
import java.util.ArrayList;
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
@WebServlet(name = "EditarUsuarioServlet", urlPatterns = {"/EditarUsuarioServlet"})
public class EditarUsuarioServlet extends HttpServlet {

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
        ArrayList<Filial> filiais = new ArrayList<>();
        filiais = ProdutoDAO.listarFiliais();
        request.setAttribute("filiais", filiais);
        //Envia o tipo para saber se é para cadastrar ou alterar
        request.setAttribute("tipo", "EditarUsuarioServlet");
        //Para Verifica se o usuário possui acesso a essa página
        if(UsuarioDAO.usuario != null)
            request.setAttribute("usuario", UsuarioDAO.usuario);
        RequestDispatcher disp = request.getRequestDispatcher("/Usuario/cadastrarUsuario.jspx");
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
        int id = Integer.parseInt(request.getParameter("id"));
        Usuario usuario = UsuarioDAO.verUsuario(id);
        request.setAttribute("usuario", usuario);
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
        Usuario u = CadastroUsuarioServlet.adiciocar(request);
        u.setId(Integer.parseInt(request.getParameter("codUsuario")));
        if(UsuarioDAO.alteraUsuario(u))
            request.setAttribute("mensagem", "Usuário alterado com sucesso!");
        else
            request.setAttribute("mensagem","Erro ao alterar o Usuário!");
        request.setAttribute("usuario", u);
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
