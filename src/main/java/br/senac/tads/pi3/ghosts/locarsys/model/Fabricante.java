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
public class Fabricante {
    private int idFabricante;
    private String nomeFabricante;

    public int getIdFabricante() {
        return idFabricante;
    }

    public String getNomeFabricante() {
        return nomeFabricante;
    }

    public void setIdFabricante(int idFabricante) {
        this.idFabricante = idFabricante;
    }

    public void setNomeFabricante(String nomeFabricante) {
        this.nomeFabricante = nomeFabricante;
    }

    public Fabricante(int idFabricante, String nomeFabricante) {
        this.idFabricante = idFabricante;
        this.nomeFabricante = nomeFabricante;
    }

    
    public Fabricante() {
    }
    
    
}
