package br.com.mcampos.ejb.cloudsystem.account.plan.entity;


import br.com.mcampos.ejb.cloudsystem.account.mask.entity.AccountingMask;
import br.com.mcampos.ejb.cloudsystem.account.nature.entity.AccountingNature;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = AccountingPlan.getAll,
                              query = "select o from AccountingPlan o where o.mask = ?1 order by o.number" ) } )
@Table( name = "\"accounting_plan\"" )
@IdClass( AccountingPlanPK.class )
public class AccountingPlan implements Serializable
{
    public static final String getAll = "AccountingPlan.findAll";

    @Id
    @Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer companyId;

    @Id
    @Column( name = "acm_id_in", nullable = false, insertable = false, updatable = false )
    private Integer maskId;

    @Id
    @Column( name = "acp_number_ch", nullable = false )
    private String number;

    @Column( name = "acp_description_ch", nullable = false )
    private String description;


    @Column( name = "acp_simplified_number_ch" )
    private String shortNumber;


    @ManyToOne( optional = false )
    @JoinColumns( { @JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", nullable = false ),
                    @JoinColumn( name = "acm_id_in", referencedColumnName = "acm_id_in", nullable = false ) } )
    private AccountingMask mask;


    @ManyToOne( optional = true )
    @JoinColumns( { @JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", insertable = false, updatable = false ),
                    @JoinColumn( name = "acm_id_in", referencedColumnName = "acm_id_in", insertable = false, updatable = false ),
                    @JoinColumn( name = "acp_parent_ch", referencedColumnName = "acp_number_ch", nullable = true ) } )
    private AccountingPlan parent;


    @ManyToOne( optional = false )
    @JoinColumn( name = "acn_id_in" )
    private AccountingNature nature;


    public AccountingPlan()
    {
    }

    public AccountingPlan( AccountingMask mask, String number )
    {
        setMask( mask );
        setNumber( number );
    }


    public Integer getMaskId()
    {
        return maskId;
    }

    public void setMaskId( Integer acm_id_in )
    {
        this.maskId = acm_id_in;
    }


    public String getDescription()
    {
        return description;
    }

    public void setDescription( String acp_description_ch )
    {
        this.description = acp_description_ch;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber( String acp_number_ch )
    {
        this.number = acp_number_ch;
    }

    public String getShortNumber()
    {
        return shortNumber;
    }

    public void setShortNumber( String acp_simplified_number_ch )
    {
        this.shortNumber = acp_simplified_number_ch;
    }

    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId( Integer usr_id_in )
    {
        this.companyId = usr_id_in;
    }

    public AccountingMask getMask()
    {
        return mask;
    }

    public void setMask( AccountingMask accountingMask )
    {
        this.mask = accountingMask;
        if ( accountingMask != null ) {
            this.maskId = accountingMask.getId();
            this.companyId = accountingMask.getCompanyId();
        }
    }

    public AccountingNature getNature()
    {
        return nature;
    }

    public void setNature( AccountingNature accountingNature )
    {
        this.nature = accountingNature;
    }

    public void setParent( AccountingPlan parent )
    {
        this.parent = parent;
    }

    public AccountingPlan getParent()
    {
        return parent;
    }
}
