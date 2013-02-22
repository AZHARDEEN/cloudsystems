package br.com.mcampos.ejb.user.company;

import javax.ejb.Stateless;


import br.com.mcampos.ejb.user.BaseUserSession;
import br.com.mcampos.ejb.user.company.type.CompanyType;
import br.com.mcampos.ejb.user.usertype.UserType;

/**
 * Session Bean implementation class CompanySessionBean
 */
@Stateless(name = "CompanySession", mappedName = "CompanySession")
public class CompanySessionBean extends BaseUserSession<Company> implements CompanySessionLocal, CompanySession
{

	@Override
	protected Class<Company> getEntityClass( )
	{
		return Company.class;
	}

	@Override
	public Company merge(Company newEntity) {
		if ( newEntity.getUserType( ) == null ) {
			newEntity.setUserType( getEntityManager( ).find( UserType.class, UserType.typeCompany ) );
		}
		if ( newEntity.getType() == null ) {
			newEntity.setType( getEntityManager().find(CompanyType.class, CompanyType.defaultType) );
		}
		newEntity = super.merge(newEntity);
		lazyLoad( newEntity );
		return newEntity;
	}

}
