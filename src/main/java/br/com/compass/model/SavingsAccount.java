package br.com.compass.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("4")
public class SavingsAccount extends Account{

    public SavingsAccount() {
        super();
    }

}
