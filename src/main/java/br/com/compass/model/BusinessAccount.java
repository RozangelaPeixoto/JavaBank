package br.com.compass.model;

import javax.persistence.Entity;

@Entity
public class BusinessAccount extends Account{

    private String cnpj;

    public BusinessAccount(Integer id, Double balance, User holder, String cnpj) {
        super(id, balance, holder);
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }
}
