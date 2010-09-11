package br.com.mcampos.dto.accounting;


import java.io.Serializable;

public class AccountingEventPlanDTO implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2148729417102308302L;
	private AccountingNatureDTO nature;
    private AccountingRateTypeDTO rateType;
    private AccountingPlanDTO plan;
    private Double rate;

    private AccountingEventDTO parent;

    public void setNature( AccountingNatureDTO nature )
    {
        this.nature = nature;
    }

    public AccountingNatureDTO getNature()
    {
        return nature;
    }

    public void setRateType( AccountingRateTypeDTO rateType )
    {
        this.rateType = rateType;
    }

    public AccountingRateTypeDTO getRateType()
    {
        return rateType;
    }

    public void setPlan( AccountingPlanDTO plan )
    {
        this.plan = plan;
    }

    public AccountingPlanDTO getPlan()
    {
        return plan;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof AccountingEventPlanDTO ) {
            AccountingEventPlanDTO other = ( AccountingEventPlanDTO )obj;
            return getPlan().equals( other.getPlan() );
        }
        return false;
    }

    public void setRate( Double rate )
    {
        this.rate = rate;
    }

    public Double getRate()
    {
        return rate;
    }

    public void setParent( AccountingEventDTO parent )
    {
        this.parent = parent;
    }

    public AccountingEventDTO getParent()
    {
        return parent;
    }
}
