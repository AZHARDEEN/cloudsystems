package br.com.mcampos.ejb.user.company;

import javax.ejb.Stateless;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.user.BaseUserSession;
import br.com.mcampos.jpa.user.Company;
import br.com.mcampos.jpa.user.CompanyType;
import br.com.mcampos.jpa.user.UserType;
import br.com.mcampos.jpa.user.Users;

/**
 * Session Bean implementation class CompanySessionBean
 */
@Stateless( name = "CompanySession", mappedName = "CompanySession" )
public class CompanySessionBean extends BaseUserSession<Company> implements CompanySessionLocal, CompanySession
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7023372297044506519L;

	@Override
	protected Class<Company> getEntityClass( )
	{
		return Company.class;
	}

	@Override
	@Deprecated
	public Company merge( Company newEntity )
	{
		this.configureDefault( newEntity );
		newEntity = super.merge( newEntity );
		this.lazyLoad( newEntity );
		return newEntity;
	}

	@Override
	public Company add( PrincipalDTO auth, Company newEntity )
	{
		return this.add( newEntity );
	}

	@Override
	public Company add( Company newEntity )
	{
		this.configureDefault( newEntity );
		newEntity = super.add( newEntity );
		this.lazyLoad( newEntity );
		return newEntity;
	}

	private void configureDefault( Company newEntity )
	{
		super.configureDefault( (Users) newEntity );
		if ( newEntity.getUserType( ) == null ) {
			newEntity.setUserType( this.getEntityManager( ).find( UserType.class, UserType.typeCompany ) );
		}
		if ( newEntity.getType( ) == null ) {
			newEntity.setType( this.getEntityManager( ).find( CompanyType.class, CompanyType.defaultType ) );
		}
	}

}
