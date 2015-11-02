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
public class ClasseProduto {
    private int idClasse;
    private char tipoClasse;
    private float tarifaClasse;

    public ClasseProduto(int idClasse, char tipoClasse, float tarifaClasse) {
        this.idClasse = idClasse;
        this.tipoClasse = tipoClasse;
        this.tarifaClasse = tarifaClasse;
    }

    public ClasseProduto() {
    }
    
    public int getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(int idClasse) {
        this.idClasse = idClasse;
    }

    public char getTipoClasse() {
        return tipoClasse;
    }

    public void setTipoClasse(char tipoClasse) {
        this.tipoClasse = tipoClasse;
    }

    public float getTarifaClasse() {
        return tarifaClasse;
    }

    public void setTarifaClasse(float tarifaClasse) {
        this.tarifaClasse = tarifaClasse;
    }
    
    
}
