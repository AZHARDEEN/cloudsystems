package br.com.mcampos.ejb.cloudsystem.user.person.session;


import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless( name = "NewPersonSession", mappedName = "CloudSystems-EjbPrj-NewPersonSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class NewPersonSessionBean extends Crud<Integer, Person> implements NewPersonSessionLocal
{
	public NewPersonSessionBean()
	{
	}

	public void delete( Integer key ) throws ApplicationException
	{
		delete( Person.class, key );
	}

	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Person get( Integer key ) throws ApplicationException
	{
		return get( Person.class, key );
	}
}
