package br.com.mcampos.ejb.cloudsystem.anoto.form.user.entity;

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

import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;

@Entity
@NamedQueries( { @NamedQuery( name = AnotoFormUser.nextSequence,
		query = "select max (o.sequence) from AnotoFormUser o where o.form = ?1 " ),
		@NamedQuery( name = AnotoFormUser.getAll,
				query = "select o from AnotoFormUser o where o.form.id = ?1 and o.toDate is null " ),
		@NamedQuery( name = AnotoFormUser.getUser,
				query = "select o from AnotoFormUser o where o.form.id = ?1 and o.company.id = ?2 and o.toDate is null " ),
		@NamedQuery( name = AnotoFormUser.getFormUser,
				query = "select o from AnotoFormUser o where o.company = ?1 and o.toDate IS NULL " ) } )
@Table( name = "anoto_form_user" )
@IdClass( AnotoFormUserPK.class )
public class AnotoFormUser implements Serializable
{
	public static final String nextSequence = "AnotoFormUser.nextSequence";
	public static final String getFormUser = "AnotoFormUser.getFormUser";
	public static final String getUser = "AnotoFormUser.getUser";
	public static final String getAll = "AnotoFormUser.getAll";

	private static final long serialVersionUID = 7934535745287389394L;

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
	@Column( name = "frm_id_in", nullable = false )
	private Integer formId;

	@ManyToOne( optional = false, fetch = FetchType.EAGER )
	@JoinColumn(
			name = "frm_id_in", columnDefinition = "Integer", insertable = false,
			updatable = false, nullable = false, referencedColumnName = "frm_id_in" )
	private AnotoForm form;

	@ManyToOne( optional = false )
	@JoinColumn( name = "usr_id_in", insertable = false, updatable = false )
	private Company company;

	public AnotoFormUser( )
	{
	}

	public Date getFromDate( )
	{
		return this.fromDate;
	}

	public void setFromDate( Date afu_from_dt )
	{
		this.fromDate = afu_from_dt;
	}

	public Integer getSequence( )
	{
		return this.sequence;
	}

	public void setSequence( Integer afu_seq_in )
	{
		this.sequence = afu_seq_in;
	}

	public Date getToDate( )
	{
		return this.toDate;
	}

	public void setToDate( Date afu_to_dt )
	{
		this.toDate = afu_to_dt;
	}

	public Integer getFormId( )
	{
		return this.formId;
	}

	public void setFormId( Integer frm_id_in )
	{
		this.formId = frm_id_in;
	}

	public void setForm( AnotoForm form )
	{
		this.form = form;
		this.setFormId( form != null ? form.getId( ) : null );
	}

	public AnotoForm getForm( )
	{
		return this.form;
	}

	public void setCompany( Company user )
	{
		this.company = user;
	}

	public Company getCompany( )
	{
		return this.company;
	}
}
