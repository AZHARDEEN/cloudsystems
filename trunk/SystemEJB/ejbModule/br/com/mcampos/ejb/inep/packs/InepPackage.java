package br.com.mcampos.ejb.inep.packs;

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

import br.com.mcampos.ejb.user.company.Company;

/**
 * The persistent class for the inep_package database table.
 * 
 */
@Entity
@Table( name = "inep_package", schema = "inep" )
@NamedQueries( { @NamedQuery( name = InepPackage.getAll, query = "select o from InepPackage o where o.company = ?1" ) } )
public class InepPackage implements Serializable, Comparable<InepPackage>
{
	private static final long serialVersionUID = 1L;
	public static final String getAll = "InepPackage.getAll";

	@EmbeddedId
	private InepPackagePK id;

	@Column( name = "pct_code_ch" )
	private String description;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "usr_id_in", insertable = false, updatable = false )
	private Company company;

	@Column( name = "pct_init_dt" )
	@Temporal( TemporalType.DATE )
	private Date initDate;

	@Column( name = "pct_end_dt" )
	@Temporal( TemporalType.DATE )
	private Date endDate;

	public InepPackage( )
	{
	}

	public InepPackagePK getId( )
	{
		if ( this.id == null ) {
			this.id = new InepPackagePK( );
		}
		return this.id;
	}

	public void setId( InepPackagePK id )
	{
		this.id = id;
	}

	public String getDescription( )
	{
		return this.description;
	}

	public void setDescription( String pctCodeCh )
	{
		this.description = pctCodeCh;
	}

	public Company getCompany( )
	{
		return this.company;
	}

	public void setCompany( Company company )
	{
		this.company = company;
	}

	public int compareTo( InepPackage object, Integer field )
	{
		switch ( field ) {
		case 0:
			return getId( ).getId( ).compareTo( object.getId( ).getId( ) );
		case 1:
			return getDescription( ).compareTo( object.getDescription( ) );
		default:
			return 0;
		}
	}

	@Override
	public boolean equals( Object obj )
	{
		return getId( ).equals( ( (InepPackage) obj ).getId( ) );
	}

	@Override
	public int compareTo( InepPackage o )
	{
		return getId( ).compareTo( o.getId( ) );
	}

	public String getField( Integer field )
	{
		switch ( field ) {
		case 0:
			return getId( ).getId( ).toString( );
		case 1:
			return getDescription( );
		default:
			return "";
		}
	}

	public Date getInitDate( )
	{
		return this.initDate;
	}

	public void setInitDate( Date initDate )
	{
		this.initDate = initDate;
	}

	public Date getEndDate( )
	{
		return this.endDate;
	}

	public void setEndDate( Date endDate )
	{
		this.endDate = endDate;
	}
}