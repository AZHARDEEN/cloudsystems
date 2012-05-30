package br.com.mcampos.ejb.user.person;


import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.user.usertype.UserType;


@Stateless( name = "PersonSession", mappedName = "PersonSession" )
public class PersonSessionBean extends SimpleSessionBean<Person> implements PersonSession, PersonSessionLocal
{
	@Override
	protected Class<Person> getEntityClass()
	{
		return Person.class;
	}

	@Override
	public Person merge( Person newEntity )
	{
		if ( newEntity.getUserType( ) == null ) {
			newEntity.setUserType( getEntityManager( ).find( UserType.class, UserType.typePerson ) );
		}
		newEntity = super.merge( newEntity );
		return newEntity;
	}
}
