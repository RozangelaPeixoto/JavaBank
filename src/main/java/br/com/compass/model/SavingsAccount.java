package br.com.compass.model;

import javax.persistence.Entity;

@Entity
public class SavingsAccount extends Account{

    public SavingsAccount(Integer id, Double balance, User holder) {
        super(id, balance, holder);
    }

}
