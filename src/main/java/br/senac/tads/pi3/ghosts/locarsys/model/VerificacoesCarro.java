/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.ghosts.locarsys.model;

/**
 *
 * @author bruno.clopes
 */
public interface VerificacoesCarro {
    
    boolean verificaChassi(String chassi);
    boolean verificaPlaca(String placa);
    boolean verificaRenavam(String renavam);
    
}
