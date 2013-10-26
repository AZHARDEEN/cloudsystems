package br.com.mcampos.ejb.cloudsystem.user.attribute.contacttype.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.user.attribute.contacttype.entity.ContactType;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;



@Stateless( name = "ContactTypeSession", mappedName = "ContactTypeSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class ContactTypeSessionBean extends Crud<Integer, ContactType> implements ContactTypeSessionLocal
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1814968992436909166L;

	public void delete( Integer key ) throws ApplicationException
    {
        delete( ContactType.class, key );
    }

    public ContactType get( Integer key ) throws ApplicationException
    {
        return get( ContactType.class, key );
    }

    public List<ContactType> getAll() throws ApplicationException
    {
        return getAll( ContactType.getAll );
    }

    public Integer nextIntegerId() throws ApplicationException
    {
        return nextIntegerId( ContactType.nextId );
    }
}
