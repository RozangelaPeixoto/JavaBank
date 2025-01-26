package br.com.compass.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SAVINGS")
public class SalaryAccount extends Account{

    public SalaryAccount() { super(); }
    public SalaryAccount(Integer id, User holder) {
        super(id, holder);
    }

}
