package br.com.mcampos.ejb.cloudsystem.user.attribute.contacttype.session;


import br.com.mcampos.dto.user.attributes.ContactTypeDTO;
import br.com.mcampos.ejb.cloudsystem.user.attribute.contacttype.entity.ContactType;
import br.com.mcampos.ejb.cloudsystem.user.attribute.usertype.entity.entity.UserType;
import br.com.mcampos.ejb.core.util.DTOFactory;

import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless( name = "ContactTypeSession", mappedName = "CloudSystems-EjbPrj-ContactTypeSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class ContactTypeSessionBean extends Crud<Integer, ContactType> implements ContactTypeSessionLocal
{
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
