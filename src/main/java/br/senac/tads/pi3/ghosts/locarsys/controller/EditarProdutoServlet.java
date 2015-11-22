/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.ghosts.locarsys.controller;

import br.senac.tads.pi3.ghosts.locarsys.dao.CarroDAO;
import br.senac.tads.pi3.ghosts.locarsys.dao.EstadoDAO;
import br.senac.tads.pi3.ghosts.locarsys.dao.FabricanteDAO;
import br.senac.tads.pi3.ghosts.locarsys.dao.ProdutoDAO;
import br.senac.tads.pi3.ghosts.locarsys.dao.UsuarioDAO;
import br.senac.tads.pi3.ghosts.locarsys.model.Carro;
import br.senac.tads.pi3.ghosts.locarsys.model.ClasseProduto;
import br.senac.tads.pi3.ghosts.locarsys.model.Combustivel;
import br.senac.tads.pi3.ghosts.locarsys.model.Estado;
import br.senac.tads.pi3.ghosts.locarsys.model.Fabricante;
import br.senac.tads.pi3.ghosts.locarsys.model.Filial;
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
 * @author Bruno
 */
@WebServlet(name = "EditarProdutoServlet", urlPatterns = {"/EditarProdutoServlet"})
public class EditarProdutoServlet extends HttpServlet {

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
        ArrayList<Fabricante> fabricantes = new ArrayList<>();
        fabricantes = FabricanteDAO.listarFabricantes();
        request.setAttribute("fabricantes", fabricantes);

        ArrayList<Combustivel> combustiveis = new ArrayList<>();
        combustiveis = ProdutoDAO.listarCombustiveis();
        request.setAttribute("combustiveis", combustiveis);

        ArrayList<ClasseProduto> classes = new ArrayList<>();
        classes = ProdutoDAO.listarClasses();
        request.setAttribute("classes", classes);

        ArrayList<Filial> filiais = new ArrayList<>();
        filiais = ProdutoDAO.listarFiliais();
        request.setAttribute("filiais", filiais);

        ArrayList<Estado> estados = new ArrayList<>();
        estados = EstadoDAO.listarEstados();
        request.setAttribute("estados", estados);

        //Envia o tipo para saber se é para cadastrar ou alterar
        request.setAttribute("tipo", "EditarProdutoServlet");
        //Para Verifica se o usuário possui acesso a essa página
        if(UsuarioDAO.usuario != null)
            request.setAttribute("usuario", UsuarioDAO.usuario);
        RequestDispatcher disp = request.getRequestDispatcher("/Produto/cadastrarProduto.jspx");
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
        System.out.println("id = " + id);
        Carro carro = CarroDAO.verCarro(id);
        request.setAttribute("carro", carro);
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
        Carro c = CadastroProdutoServlet.adiciocar(request);
        c.setId(Integer.parseInt(request.getParameter("codCarro")));
        if(CarroDAO.alteraCarro(c))
            request.setAttribute("mensagem", "Produto alterado com sucesso!");
        else
            request.setAttribute("mensagem","Erro ao alterar o Produto!");
        request.setAttribute("carro", c);
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
