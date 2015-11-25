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
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bruno.clopes
 */
@WebServlet(name = "CadastroUsuarioServlet", urlPatterns = {"/CadastroUsuarioServlet"})
public class CadastroUsuarioServlet extends HttpServlet {

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
        request.setAttribute("tipo", "CadastroUsuarioServlet");

        //Para Verifica se o usuário possui acesso a essa página
        if (UsuarioDAO.usuario != null) {
            request.setAttribute("usuario", UsuarioDAO.usuario);
        }
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
        Usuario u = adiciocar(request);
        String erro = UsuarioDAO.verificaoes(u);
        if (erro == null) {
            if (UsuarioDAO.cadastraUsuario(u)) {
                request.setAttribute("mensagem", "Usuário cadastrado com sucesso!");
            } else {
                request.setAttribute("mensagem", "Erro ao cadastradar o Usuário!");
            }
        } else {
            request.setAttribute("mensagem", erro);
        }
        request.setAttribute("usu", u);
        processRequest(request, response);
    }

    public static Usuario adiciocar(HttpServletRequest request) {
        Usuario u = new Usuario();
        u.setNome(request.getParameter("nome"));
        u.setSexo(request.getParameter("sexo").charAt(0));
        u.setDataNascimento(request.getParameter("nascimento"));
        u.setFuncao(request.getParameter("funcao"));
        u.setTipoUsuario(request.getParameter("tipoUsuario").charAt(0));
        u.setLogin(request.getParameter("login"));
        u.setSenha(request.getParameter("senha"));
        u.setCpf(request.getParameter("cpf"));
        u.setFilial(request.getParameter("filial"));
        return u;
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
