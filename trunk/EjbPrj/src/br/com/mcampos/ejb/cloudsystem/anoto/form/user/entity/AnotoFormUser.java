package br.com.mcampos.ejb.cloudsystem.anoto.form.user.entity;


import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@NamedQueries( { @NamedQuery( name = "AnotoFormUser.findAll", query = "select o from AnotoFormUser o" ),
                 @NamedQuery( name = AnotoFormUser.nextSequence, query = "select o from AnotoFormUser o where o.form = ?1 " ) } )
@Table( name = "anoto_form_user" )
@IdClass( AnotoFormUserPK.class )
public class AnotoFormUser implements Serializable
{
    public static final String nextSequence = "AnotoFormUser.nextSequence";

    @Column( name = "afu_from_dt", nullable = false )
    @Temporal( value = TemporalType.TIMESTAMP )
    private Date fromDate;

    @Id
    @Column( name = "afu_seq_in", nullable = false )
    private Integer sequence;

    @Column( name = "afu_to_dt", nullable = true )
    @Temporal( value = TemporalType.TIMESTAMP )
    private Date toDate;

    @Id
    @Column( name = "frm_id_in", nullable = false, insertable = false, updatable = false )
    private Integer formId;

    @Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer userId;

    @ManyToOne( optional = false, fetch = FetchType.EAGER )
    @JoinColumn( name = "frm_id_in", columnDefinition = "Integer" )
    private AnotoForm form;

    @ManyToOne( optional = false, fetch = FetchType.LAZY )
    @JoinColumn( name = "usr_id_in", columnDefinition = "Integer" )
    private Company company;

    public AnotoFormUser()
    {
    }


    public Date getFromDate()
    {
        return fromDate;
    }

    public void setFromDate( Date afu_from_dt )
    {
        this.fromDate = afu_from_dt;
    }

    public Integer getSequence()
    {
        return sequence;
    }

    public void setSequence( Integer afu_seq_in )
    {
        this.sequence = afu_seq_in;
    }

    public Date getToDate()
    {
        return toDate;
    }

    public void setToDate( Date afu_to_dt )
    {
        this.toDate = afu_to_dt;
    }

    public Integer getFormId()
    {
        return formId;
    }

    public void setFormId( Integer frm_id_in )
    {
        this.formId = frm_id_in;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId( Integer usr_id_in )
    {
        this.userId = usr_id_in;
    }

    public void setForm( AnotoForm form )
    {
        this.form = form;
        setFormId( form != null ? form.getId() : null );
    }

    public AnotoForm getForm()
    {
        return form;
    }

    public void setCompany( Company user )
    {
        this.company = user;
        setUserId( user != null ? user.getId() : null );
    }

    public Company getCompany()
    {
        return company;
    }
}
