package br.com.mcampos.ejb.fdigital.form.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mcampos.ejb.fdigital.form.AnotoForm;
import br.com.mcampos.ejb.user.company.Company;

/**
 * The persistent class for the anoto_form_user database table.
 * 
 */
@Entity
@Table( name = "anoto_form_user" )
public class AnotoFormUser implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AnotoFormUserPK id;

	@Column( name = "afu_from_dt", nullable = false )
	@Temporal( TemporalType.TIMESTAMP )
	private Date fromDate;

	@Column( name = "afu_to_dt" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date toDate;

	@ManyToOne
	@JoinColumn( name = "usr_id_in", nullable = false, insertable = true, updatable = true )
	private Company client;

	// bi-directional many-to-one association to AnotoForm
	@ManyToOne
	@JoinColumn( name = "frm_id_in", nullable = false, insertable = false, updatable = false )
	private AnotoForm form;

	public AnotoFormUser( )
	{
	}

	public AnotoFormUserPK getId( )
	{
		return this.id;
	}

	public void setId( AnotoFormUserPK id )
	{
		this.id = id;
	}

	public Date getFromDate( )
	{
		return this.fromDate;
	}

	public AnotoForm getForm( )
	{
		return this.form;
	}

	public void setForm( AnotoForm anotoForm )
	{
		this.form = anotoForm;
		getId( ).setFormId( getForm( ) != null ? getForm( ).getId( ) : null );
	}

	public Company getClient( )
	{
		return this.client;
	}

	public void setClient( Company client )
	{
		this.client = client;
	}

	public Date getToDate( )
	{
		return this.toDate;
	}

	public void setToDate( Date toDate )
	{
		this.toDate = toDate;
	}

	public void setFromDate( Date fromDate )
	{
		this.fromDate = fromDate;
	}

}