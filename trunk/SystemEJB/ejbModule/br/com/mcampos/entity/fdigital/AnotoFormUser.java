package br.com.mcampos.entity.fdigital;

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

import br.com.mcampos.entity.user.Company;

/**
 * The persistent class for the anoto_form_user database table.
 * 
 */
@Entity
@Table( name = "anoto_form_user", schema = "public" )
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
		return id;
	}

	public void setId( AnotoFormUserPK id )
	{
		this.id = id;
	}

	public Date getFromDate( )
	{
		return fromDate;
	}

	public AnotoForm getForm( )
	{
		return form;
	}

	public void setForm( AnotoForm anotoForm )
	{
		form = anotoForm;
		getId( ).setFormId( getForm( ) != null ? getForm( ).getId( ) : null );
	}

	public Company getClient( )
	{
		return client;
	}

	public void setClient( Company client )
	{
		this.client = client;
	}

	public Date getToDate( )
	{
		return toDate;
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