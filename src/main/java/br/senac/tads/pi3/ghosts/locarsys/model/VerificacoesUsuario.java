/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.ghosts.locarsys.model;

/**
 *
 * @author Bruno
 */
public interface VerificacoesUsuario {
    
    boolean verificaCPF(String cpf);
    boolean verificaLogin(String login);
    
}
