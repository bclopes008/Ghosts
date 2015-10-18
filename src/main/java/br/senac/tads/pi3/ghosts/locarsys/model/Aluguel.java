package br.senac.tads.pi3.ghosts.locarsys.model;

import java.util.Date;

/**
 *
 * @author Bruno
 */
public class Aluguel {
    
    private Date dataInicial;
    private Date dataFinal;
    private float valorTotal;

    public Aluguel(Date dataInicial, Date dataFinal, float valorTotal) {
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.valorTotal = valorTotal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public float getValorTotal() {
        return valorTotal;
    }
    
}
