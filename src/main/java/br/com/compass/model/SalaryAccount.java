package br.com.compass.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("3")
public class SalaryAccount extends Account{

    public SalaryAccount() { super(); }
    public SalaryAccount(Integer id, User holder) {
        super(id, holder);
    }

}
