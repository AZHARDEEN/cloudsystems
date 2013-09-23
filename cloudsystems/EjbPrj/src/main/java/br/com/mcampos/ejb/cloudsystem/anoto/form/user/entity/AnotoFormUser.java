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
	@Column( name = "frm_id_in", nullable = false, insertable = false, updatable = false )
	private Integer formId;

	@ManyToOne( optional = false, fetch = FetchType.EAGER )
	@JoinColumn(
			name = "frm_id_in", columnDefinition = "Integer", insertable = false,
			updatable = false, nullable = false, referencedColumnName = "frm_id_in" )
	private AnotoForm form;

	@ManyToOne( optional = false )
	@JoinColumn( name = "usr_id_in" )
	private Company company;

	public AnotoFormUser( )
	{
	}

	public Date getFromDate( )
	{
		return fromDate;
	}

	public void setFromDate( Date afu_from_dt )
	{
		fromDate = afu_from_dt;
	}

	public Integer getSequence( )
	{
		return sequence;
	}

	public void setSequence( Integer afu_seq_in )
	{
		sequence = afu_seq_in;
	}

	public Date getToDate( )
	{
		return toDate;
	}

	public void setToDate( Date afu_to_dt )
	{
		toDate = afu_to_dt;
	}

	public Integer getFormId( )
	{
		return formId;
	}

	public void setFormId( Integer frm_id_in )
	{
		formId = frm_id_in;
	}

	public void setForm( AnotoForm form )
	{
		this.form = form;
		setFormId( form != null ? form.getId( ) : null );
	}

	public AnotoForm getForm( )
	{
		return form;
	}

	public void setCompany( Company user )
	{
		company = user;
	}

	public Company getCompany( )
	{
		return company;
	}
}