package br.com.mcampos.ejb.cloudsystem.resale.dealer.entity;


import br.com.mcampos.ejb.cloudsystem.resale.dealer.type.entity.DealerType;
import br.com.mcampos.ejb.cloudsystem.resale.entity.Resale;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;

import java.io.Serializable;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@NamedQueries( { @NamedQuery( name = Dealer.getAll, query = "select o from Dealer o where o.resale = ?1 and o.toDate is null " ),
                 @NamedQuery( name = Dealer.findDealer,
                              query = "select o from Dealer o where o.resale = ?1 and o.dealer = ?2 and o.toDate is null " ),
                 @NamedQuery( name = Dealer.hasResale,
                              query = "select o from Dealer o where o.dealer = ?1 and o.toDate is null " ),
                 @NamedQuery( name = Dealer.nextSequence, query = "select max(o.sequence) from Dealer o where o.resale = ?1 " ) } )
@Table( name = "dealer" )
@IdClass( DealerPK.class )
public class Dealer implements Serializable
{

    public static final String getAll = "Dealer.findAll";
    public static final String findDealer = "Dealer.findDealer";
    public static final String hasResale = "Dealer.hasResale";
    public static final String nextSequence = "Dealer.nextSequence";

    @Column( name = "dea_from_dt" )
    @Temporal( value = TemporalType.TIMESTAMP )
    private Date fromDate;

    @Column( name = "dea_id_in", nullable = false, insertable = false, updatable = false )
    private Integer id;

    @Id
    @Column( name = "dea_sequence_in", nullable = false )
    private Integer sequence;

    @Column( name = "dea_to_dt" )
    @Temporal( value = TemporalType.TIMESTAMP )
    private Date toDate;

    /*
     * Resale PK
     */
    @Id
    @Column( name = "rsl_sequence_in", nullable = false, insertable = false, updatable = false )
    private Integer resaleSequence;

    @Id
    @Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer userId;
    /*END OF ResalePK*/

    @ManyToOne
    @JoinColumn( name = "dtp_id_in" )
    private DealerType dealerType;

    @ManyToOne( optional = false )
    @JoinColumns( { @JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", nullable = false, updatable = true,
                                 insertable = true ),
                    @JoinColumn( name = "rsl_sequence_in", referencedColumnName = "rsl_sequence_in", nullable = false,
                                 updatable = true, insertable = true ) } )
    private Resale resale;

    @ManyToOne( optional = false )
    @JoinColumn( name = "dea_id_in", referencedColumnName = "usr_id_in", nullable = false, updatable = true, insertable = true )
    private Person dealer;

    public Dealer()
    {
    }

    public Dealer( Resale resale )
    {
        setResale( resale );
    }

    public Date getFromDate()
    {
        return fromDate;
    }

    public void setFromDate( Date dea_from_dt )
    {
        this.fromDate = dea_from_dt;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer dea_id_in )
    {
        this.id = dea_id_in;
    }

    public Integer getSequence()
    {
        return sequence;
    }

    public void setSequence( Integer dea_sequence_in )
    {
        this.sequence = dea_sequence_in;
    }

    public Date getToDate()
    {
        return toDate;
    }

    public void setToDate( Date dea_to_dt )
    {
        this.toDate = dea_to_dt;
    }


    public Integer getResaleSequence()
    {
        return resaleSequence;
    }

    public void setResaleSequence( Integer rsl_sequence_in )
    {
        this.resaleSequence = rsl_sequence_in;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId( Integer usr_id_in )
    {
        this.userId = usr_id_in;
    }

    public DealerType getDealerType()
    {
        return dealerType;
    }

    public void setDealerType( DealerType dealerType )
    {
        this.dealerType = dealerType;
    }

    public void setResale( Resale resale )
    {
        this.resale = resale;
        setUserId( resale != null ? resale.getCompany().getId() : null );
        setResaleSequence( resale != null ? resale.getSequence() : null );
    }

    public Resale getResale()
    {
        return resale;
    }

    public void setDealer( Person dealer )
    {
        this.dealer = dealer;
        setId( dealer != null ? dealer.getId() : null );
    }

    public Person getDealer()
    {
        return dealer;
    }
}
