/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.ghosts.locarsys.controller;

import br.senac.tads.pi3.ghosts.locarsys.dao.ClienteDAO;
import br.senac.tads.pi3.ghosts.locarsys.dao.EstadoDAO;
import br.senac.tads.pi3.ghosts.locarsys.model.Cliente;
import br.senac.tads.pi3.ghosts.locarsys.model.Endereco;
import br.senac.tads.pi3.ghosts.locarsys.model.Estado;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.net.HttpURLConnection;
import org.json.JSONObject;

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

        ArrayList<Estado> estados = new ArrayList<>();
        estados = EstadoDAO.listarEstados();
        request.setAttribute("estados", estados);
        
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
    //TODO implementar depois
    //private final String USER_AGENT = "Mozilla/5.0";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //TODO implementar depois
        /*
        String url = "http://cep.republicavirtual.com.br/web_cep.php?cep=" + request.getParameter("cep") + "&formato=json";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer res = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            res.append(inputLine);
        }
        in.close();

        JSONObject j = new JSONObject(res.toString());
        
        Cliente c = adicionar(request);
        
        Endereco e = new Endereco();
        e.setCidade(j.getString("cidade"));
        e.setBairro(j.getString("bairro"));
        e.setEndereco(j.getString("tipo_logradouro") + " " + j.getString("logradouro"));
        System.out.println("Cidade: " + e.getCidade());
        System.out.println("Bairro: " + e.getBairro());
        System.out.println("Endereço: " + e.getEndereco());
        
        request.setAttribute("cliente", e);
        */

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
        RequestDispatcher disp;
        Cliente c = adicionar(request);
        ClienteDAO dao = new ClienteDAO();
        if (dao.cadastroCliente(c)) {
            request.setAttribute("cliente", c);
            request.setAttribute("mensagem", "Cliente cadastrado com sucesso!");
        } else {
            request.setAttribute("mensagem","Erro ao cadastradar o Cliente!");
        }
        processRequest(request, response);
    }
    
    private static Cliente adicionar(HttpServletRequest request)
    {
        Cliente c = new Cliente();
        c.setNome(request.getParameter("nome"));
        c.setCpf(request.getParameter("cpf"));
        c.setDataNascimento(request.getParameter("nascimento"));
        c.setCnh(request.getParameter("cnh"));
        c.setSexo(request.getParameter("sexo").charAt(0));
        c.setCelular(request.getParameter("celular"));
        c.setEmail(request.getParameter("email"));
        Endereco e = new Endereco();
        e.setCep(request.getParameter("cep"));
        e.setEndereco(request.getParameter("endereco"));
        e.setNumero(request.getParameter("numero"));
        e.setBairro(request.getParameter("bairro"));
        e.setEstado(request.getParameter("estado"));
        e.setCidade(request.getParameter("cidade"));
        e.setComplemento(request.getParameter("complemento"));
        e.setObs(request.getParameter("observacoes"));
        c.setEndereco(e);
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
