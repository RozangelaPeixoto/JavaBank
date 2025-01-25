package br.com.compass.model;

import javax.persistence.Entity;

@Entity
public class CheckingAccount extends Account{

    public CheckingAccount(Integer id, Double balance, User holder) {
        super(id, balance, holder);
    }

}
