package br.com.compass.model;

import javax.persistence.Entity;

@Entity
public class SalaryAccount extends Account{

    private String company;

    public SalaryAccount(Integer id, Double balance, User holder, String company) {
        super(id, balance, holder);
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

}
