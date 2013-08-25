package br.com.mcampos.ejb.core.entity;

import java.io.Serializable;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import br.com.mcampos.ejb.user.company.Company;

@MappedSuperclass
public abstract class BaseCompanyEntity implements Serializable
{
	private static final long serialVersionUID = 5952185756919546022L;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "usr_id_in", insertable = false, updatable = false, nullable = false )
	private Company company;

	public abstract void setCompanyId( Integer id );

	public BaseCompanyEntity( )
	{

	}

	public BaseCompanyEntity( Company c )
	{
		setCompany( c );
	}

	public Company getCompany( )
	{
		return company;
	}

	public void setCompany( Company company )
	{
		this.company = company;
		setCompanyId( company != null ? company.getId( ) : null );
	}

}
