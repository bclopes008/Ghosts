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
public class Combustivel {
    private int idCombustivel;
    private String tipoCombustivel;

    public Combustivel() {
    }

    public Combustivel(int idCombustivel, String tipoCombustivel) {
        this.idCombustivel = idCombustivel;
        this.tipoCombustivel = tipoCombustivel;
    }

    public int getIdCombustivel() {
        return idCombustivel;
    }

    public void setIdCombustivel(int idCombustivel) {
        this.idCombustivel = idCombustivel;
    }

    public String getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(String tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }
    
    
}
