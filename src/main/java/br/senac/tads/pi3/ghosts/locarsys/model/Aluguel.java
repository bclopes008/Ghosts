package br.senac.tads.pi3.ghosts.locarsys.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        sdf.format(this.dataInicial);
        sdf.format(this.dataFinal);
        this.valorTotal = valorTotal;
    }

    public Aluguel() {
    }

    public void calcularValorTotal(float precoClasse) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        long diferencaemHoras = (this.dataFinal.getTime() - this.dataInicial.getTime());
        diferencaemHoras = diferencaemHoras / 1000 / 60 / 60;

        this.valorTotal = (diferencaemHoras / 24) * precoClasse;
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