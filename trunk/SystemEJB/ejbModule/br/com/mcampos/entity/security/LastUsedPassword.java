package br.com.mcampos.entity.security;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the last_used_password database table.
 * 
 */
@Entity
@NamedQueries( {
		@NamedQuery(
				name = LastUsedPassword.findAllByLogin,
				query = "select o from LastUsedPassword o where o.login = ?1 order by o.fromDate" )
} )
@Table( name = "last_used_password", schema = "public" )
public class LastUsedPassword implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static final String findAllByLogin = "LastUsedPassword.findAll";

	@EmbeddedId
	private LastUsedPasswordPK id;

	@Column( name = "lup_from_dt", nullable = false )
	@Temporal( TemporalType.TIMESTAMP )
	private Date fromDate;

	@Column( name = "lup_to_dt" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date toDate;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn(
			name = "usr_id_in", nullable = false, referencedColumnName = "usr_id_in", columnDefinition = "Integer",
			updatable = false, insertable = false )
	private Login login;

	public LastUsedPassword( )
	{
	}

	public LastUsedPassword( Login login )
	{
		getId( ).set( login );
	}

	public LastUsedPasswordPK getId( )
	{
		if ( id == null ) {
			id = new LastUsedPasswordPK( );
		}
		return id;
	}

	public void setId( LastUsedPasswordPK id )
	{
		this.id = id;
	}

	public Date getFromDate( )
	{
		return fromDate;
	}

	public void setFromDate( Date lupFromDt )
	{
		fromDate = lupFromDt;
	}

	public Date getToDate( )
	{
		return toDate;
	}

	public void setToDate( Date lupToDt )
	{
		toDate = lupToDt;
	}

	public Login getLogin( )
	{
		return login;
	}

	public void setLogin( Login login )
	{
		this.login = login;
		if ( getLogin( ) != null ) {
			getId( ).setId( getLogin( ).getId( ) );
		}
	}

}