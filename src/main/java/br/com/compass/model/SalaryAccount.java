package br.com.compass.model;

import javax.persistence.Entity;

@Entity
public class SalaryAccount extends Account{

    public SalaryAccount(Integer id, Double balance, User holder) { super(id, balance, holder); }

}
