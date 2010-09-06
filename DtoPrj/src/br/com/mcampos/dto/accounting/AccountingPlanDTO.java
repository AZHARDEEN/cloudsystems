package br.com.mcampos.dto.accounting;

import java.io.Serializable;


public class AccountingPlanDTO implements Serializable
{
    private String number;
    private Double balance;
    private String shortNumber; /*Número Reduzido*/
    private String description;

    private AccountingMaskDTO mask;

    private AccountingNatureDTO nature;

    public AccountingPlanDTO()
    {
        super();
    }


    public AccountingPlanDTO( AccountingMaskDTO mask, String number )
    {
        setMask( mask );
        setNumber( number );
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

    public void setMask( AccountingMaskDTO mask )
    {
        this.mask = mask;
    }

    public AccountingMaskDTO getMask()
    {
        return mask;
    }

    public void setNature( AccountingNatureDTO nature )
    {
        this.nature = nature;
    }

    public AccountingNatureDTO getNature()
    {
        return nature;
    }

    @Override
    public String toString()
    {
        return getNumber();
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof AccountingPlanDTO ) {
            AccountingPlanDTO other = ( AccountingPlanDTO )obj;
            return getMask().equals( other.getMask() ) && getNumber().equals( other.getNumber() );
        }
        return false;
    }
}
