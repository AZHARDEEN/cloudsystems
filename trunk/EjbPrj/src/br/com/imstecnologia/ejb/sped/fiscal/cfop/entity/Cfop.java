package br.com.imstecnologia.ejb.sped.fiscal.cfop.entity;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@NamedQueries( { @NamedQuery( name = Cfop.getAll, query = "select o from Cfop o" ),
                 @NamedQuery( name = Cfop.getAllValid, query = "select o from Cfop o where o.to is null" ),
                 @NamedQuery( name = Cfop.nextId, query = "select max(o.id) from Cfop o" ) } )
@Table( name = "sped.cfop" )
public class Cfop implements Serializable
{
    public static final String getAll = "Cfop.findAll";
    public static final String getAllValid = "Cfop.findAllValid";
    public static final String nextId = "Cfop.nextId";

    @Column( name = "cfop_code_ch", nullable = false )
    private String code;

    @Column( name = "cfop_description_ch", nullable = false )
    private String description;

    @Column( name = "cfop_from_dt", nullable = false )
    @Temporal( TemporalType.DATE )
    private Date from;

    @Id
    @Column( name = "cfop_id_in", nullable = false )
    private Integer id;

    @Column( name = "cfop_to_dt" )
    @Temporal( TemporalType.DATE )
    private Date to;

    public Cfop()
    {
    }

    public Cfop( Integer id )
    {
        setId( id );
    }

    public String getCode()
    {
        return code;
    }

    public void setCode( String cfop_code_ch )
    {
        this.code = cfop_code_ch;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String cfop_description_ch )
    {
        this.description = cfop_description_ch;
    }

    public Date getFrom()
    {
        return from;
    }

    public void setFrom( Date cfop_from_dt )
    {
        this.from = cfop_from_dt;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer cfop_id_in )
    {
        this.id = cfop_id_in;
    }

    public Date getTo()
    {
        return to;
    }

    public void setTo( Date cfop_to_dt )
    {
        this.to = cfop_to_dt;
    }
}
