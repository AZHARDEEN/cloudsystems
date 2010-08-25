package br.com.mcampos.dto.accounting;

import java.io.Serializable;


public class AccountingPlanDTO implements Serializable
{
    private String number;
    private Double balance;
    private String shortNumber; /*Número Reduzido*/
    private String description;

    public AccountingPlanDTO()
    {
        super();
    }

    public void setNumber( String number )
    {
        this.number = number;
    }

    public String getNumber()
    {
        return number;
    }

    public void setBalance( Double balance )
    {
        this.balance = balance;
    }

    public Double getBalance()
    {
        return balance;
    }

    public void setShortNumber( String shortNumber )
    {
        this.shortNumber = shortNumber;
    }

    public String getShortNumber()
    {
        return shortNumber;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
}
