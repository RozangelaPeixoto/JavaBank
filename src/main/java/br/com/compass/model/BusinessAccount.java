package br.com.compass.model;

import javax.persistence.Entity;

@Entity
public class BusinessAccount extends Account{

    public BusinessAccount(Integer id, Double balance, User holder) { super(id, balance, holder); }

}
