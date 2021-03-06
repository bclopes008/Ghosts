/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.ghosts.locarsys.controller;

import br.senac.tads.pi3.ghosts.locarsys.dao.*;
import br.senac.tads.pi3.ghosts.locarsys.model.*;
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
@WebServlet(name = "CadastroProdutoServlet", urlPatterns = {"/CadastroProdutoServlet"})
public class CadastroProdutoServlet extends HttpServlet {

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
        request.setAttribute("tipo", "CadastroProdutoServlet");
        //Para Verifica se o usuário possui acesso a essa página
        if (UsuarioDAO.usuario != null) {
            request.setAttribute("usuario", UsuarioDAO.usuario);
        }
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
        Carro c = adiciocar(request);
        String erro = CarroDAO.verificaoes(c);
        if (erro == null) {
            if (CarroDAO.cadastraCarro(c)) {
                request.setAttribute("mensagem", "Produto cadastrado com sucesso!");
            } else {
                request.setAttribute("mensagem", "Erro ao cadastradar o Produto!");
            }
        } else {
            request.setAttribute("mensagem", erro);
        }
        request.setAttribute("carro", c);
        processRequest(request, response);
    }

    public static Carro adiciocar(HttpServletRequest request) {
        Carro c = new Carro();
        c.setAno(Integer.parseInt(request.getParameter("ano")));
        c.setAnoFabricacao(Integer.parseInt(request.getParameter("anoFabricacao")));
        c.setChassi(request.getParameter("chassi"));
        c.setCidade(request.getParameter("cidade"));
        c.setCombustivel(request.getParameter("combustivel"));
        c.setCor(request.getParameter("cor"));
        c.setEstado(request.getParameter("estado"));
        c.setGrupo(request.getParameter("grupo").charAt(0));
        c.setKilometragem(Float.parseFloat(request.getParameter("km")));
        c.setMarca(request.getParameter("fabricante"));
        c.setModelo(request.getParameter("modelo"));
        c.setPlaca(request.getParameter("placa").toUpperCase());
        c.setRenavam(request.getParameter("renavam"));
        c.setFilial(request.getParameter("filial"));
        return c;
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
