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
import br.senac.tads.pi3.ghosts.locarsys.dao.*;
import br.senac.tads.pi3.ghosts.locarsys.model.Carro;
import br.senac.tads.pi3.ghosts.locarsys.model.ClasseProduto;
import br.senac.tads.pi3.ghosts.locarsys.model.Cliente;
import java.util.ArrayList;

/**
 *
 * @author Prime-PC
 */
@WebServlet(name = "CadastroAluguelServlet", urlPatterns = {"/CadastroAluguelServlet"})
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

        ArrayList<ClasseProduto> classes = ProdutoDAO.listarClasses();
        request.setAttribute("classes", classes);

        //Para Verifica se o usuário possui acesso a essa página
        if (UsuarioDAO.usuario != null) {
            request.setAttribute("usuario", UsuarioDAO.usuario);
        }

        RequestDispatcher disp = request.getRequestDispatcher("/Aluguel/cadastroAlugueis.jspx");
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
        Aluguel aluguel = new Aluguel();

        aluguel.setDataFinal(request.getParameter("final"));
        aluguel.setDataInicial(request.getParameter("inicial"));

        Cliente c1 = new Cliente();
        c1.setCpf(request.getParameter("cpf"));
        c1.setCnh(request.getParameter("cnh"));
        c1.setNome(request.getParameter("nomeCliente"));
        c1.setId(Integer.parseInt(request.getParameter("idCliente")));

        aluguel.setCliente(c1);

        Carro ca = new Carro();
        ca.setGrupo(request.getParameter("grupo").charAt(0));
        if (request.getParameter("carro") != null) {
            ca.setId(Integer.parseInt(request.getParameter("carro")));
            aluguel.setCarro(ca);
            String erro = AluguelDAO.verificaoes(aluguel);
            if (erro == null) {
                AluguelDAO.calcularValorTotal(aluguel);

                try {
                    if (AluguelDAO.cadastrarAluguel(aluguel)) {
                        request.setAttribute("mensagem", "Aluguel cadastrado sucesso");
                        aluguel.setId(AluguelDAO.proximoID());
                    } else {
                        request.setAttribute("mensagem", "Erro ao cadastrar o aluguel");
                    }
                } catch (ClassNotFoundException ex) {
                    request.setAttribute("mensagem", "Erro ao cadastrar o aluguel");
                    System.err.println(ex.getMessage());
                }
            } else {
                request.setAttribute("mensagem", erro);
            }
        } else {
            //Caso nenhum carro seja selecionado, apresenta a mensagem:
            request.setAttribute("mensagem", "Escolha um carro!");
        }

        request.setAttribute("aluguel", aluguel);
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
