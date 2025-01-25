package br.com.compass.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("2")
public class CheckingAccount extends Account{

    public CheckingAccount() { super(); }
    public CheckingAccount(Integer id, User holder) {
        super(id, holder);
    }

}
