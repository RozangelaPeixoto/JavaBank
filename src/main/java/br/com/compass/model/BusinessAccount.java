package br.com.compass.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("1")
public class BusinessAccount extends Account{

    public BusinessAccount() { super(); }
    public BusinessAccount(Integer id, User holder) {
        super(id, holder);
    }

}
