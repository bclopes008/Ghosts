package br.senac.tads.pi3.ghosts.locarsys.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Bruno
 */
public class Aluguel {
    private Produto carro;
    private Date dataInicial;
    private Date dataFinal;
    private float valorTotal;

    public Aluguel(Date dataInicial, Date dataFinal, float valorTotal, Produto carro) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        sdf.format(this.dataInicial);
        sdf.format(this.dataFinal);
        this.valorTotal = valorTotal;
        this.carro = carro;
    }

    public Aluguel() {
    }

    public Produto getCarro() {
        return carro;
    }

    public void setCarro(Produto carro) {
        this.carro = carro;
    }

    public void calcularValorTotal(float precoClasse) {
        Calendar dataAtual = new GregorianCalendar();
        Calendar dataFinal = new GregorianCalendar();
        Calendar dataInicial = new GregorianCalendar();
        
        dataInicial.setTime(this.dataInicial);
        dataFinal.setTime(this.dataFinal);
        dataAtual.get(Calendar.DATE);
        
        if(dataFinal.get(Calendar.DATE) <= dataAtual.get(Calendar.DATE)){
            this.valorTotal = (dataFinal.get(Calendar.DATE) - dataInicial.get(Calendar.DATE)) * precoClasse;
        }else{
            this.valorTotal = ((dataFinal.get(Calendar.DATE) - dataInicial.get(Calendar.DATE)) * precoClasse) * 1.15f;
        }
        
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
