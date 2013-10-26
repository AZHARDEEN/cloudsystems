package br.com.mcampos.jpa;

import java.io.Serializable;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import br.com.mcampos.jpa.user.Company;

@MappedSuperclass
public abstract class BaseCompanyEntity implements BaseEntity, Serializable
{
	private static final long serialVersionUID = 5952185756919546022L;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "usr_id_in", insertable = false, updatable = false, nullable = false )
	private Company company;

	@Override
	public abstract BaseCompanyPK getId( );

	public BaseCompanyEntity( )
	{

	}

	public BaseCompanyEntity( Company c )
	{
		this.setCompany( c );
	}

	public Company getCompany( )
	{
		return this.company;
	}

	public void setCompany( Company company )
	{
		this.company = company;
		this.getId( ).setCompanyId( company != null ? company.getId( ) : null );
	}

	public Integer getCompanyId( )
	{
		return this.getId( ).getCompanyId( );
	}

	public int compareTo( BaseCompanyEntity other )
	{
		if ( other == null ) {
			return -1;
		}
		if ( other == this ) {
			return 0;
		}
		return this.getId( ).compareTo( other.getId( ) );
	}

}
