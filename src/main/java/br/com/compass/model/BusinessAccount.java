package br.com.compass.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("BUSINESS")
public class BusinessAccount extends Account{

    public BusinessAccount() { super(); }

}
