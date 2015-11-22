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

    private int id;
    private Carro carro;
    private String dataInicial;
    private String dataFinal;
    private float valorTotal;
    private Cliente cliente;
    private Funcionario funcionario;

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setDataInicial(String dataInicial) {
        this.dataInicial = dataInicial;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(String cpf, String cnh, String nome) {
        this.cliente.setCpf(cpf);
        this.cliente.setCnh(cnh);
        this.cliente.setNome(nome);
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Aluguel() {
    }

    public void setCarro(char grupo, String modelo) {
        this.carro.setGrupo(grupo);
        this.carro.setModelo(modelo);
    }



    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getDataInicial() {
        return dataInicial;
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getValorTotal() {
        return valorTotal;
    }

}
