package br.com.guerethes.offdroid.persist.model;

import java.util.Date;

import br.com.guerethes.offdroid.persist.interfaces.PersistDB;

/**
 * Created by jean on 27/12/16.
 */
public class Sincronizacao implements PersistDB {

    private Object id;
    private Date data;
    private PersistDB value;
    private int operacao;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public PersistDB getValue() {
        return value;
    }

    public void setValue(PersistDB value) {
        this.value = value;
    }

    public int getOperacao() {
        return operacao;
    }

    public void setOperacao(int operacao) {
        this.operacao = operacao;
    }

    @Override
    public Object getId() {
        return id;
    }

    @Override
    public void setId(Object id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Data: " + data + " Value: " + value;
    }
}