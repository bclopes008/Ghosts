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
        ArrayList<Fabricante> fabricantes = new ArrayList<>();
        
        FabricanteDAO FbDAO = new FabricanteDAO();
        fabricantes = FbDAO.listarFabricantes();
        request.setAttribute("fabricantes", fabricantes);
        
        ArrayList<Combustivel> combustiveis = new ArrayList<>();
        
        combustiveis = ProdutoDAO.listarCombustiveis();
        request.setAttribute("combustiveis", combustiveis);
        
        
        ArrayList<ClasseProduto> classes = new ArrayList<>();
        
        classes = ProdutoDAO.listarClasses();
        request.setAttribute("classes", classes);
        
        
        RequestDispatcher disp = request.getRequestDispatcher("cadastrarProduto.jspx");
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
        Carro c = new Carro();
        c.setAno(Integer.parseInt(request.getParameter("ano")));
        c.setAnoFabricacao(Integer.parseInt(request.getParameter("anoFabricacao")));
        c.setChassi(request.getParameter("chassi"));
        c.setCidade(request.getParameter("cidade"));
        c.setCombustivel(request.getParameter("combustivel"));
        c.setCor(request.getParameter("cor"));
        c.setEstado(request.getParameter("estado"));
        c.setGrupo(request.getParameter("grupo").charAt(0));
        //c.setKilometragem(Float.parseFloat(request.getParameter("kilometragem")));
        c.setMarca(request.getParameter("fabricante"));
        c.setModelo(request.getParameter("modelo"));
        c.setPlaca(request.getParameter("placa"));
        c.setRenavam(request.getParameter("renavam"));
        CarroDAO dao = new CarroDAO();
        if(dao.cadastraCarro(c))
            disp = request.getRequestDispatcher("telaPrincipal.jspx");
        else
            disp = request.getRequestDispatcher("cadastroProduto.jspx");
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
