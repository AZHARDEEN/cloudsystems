package br.com.mcampos.ejb.cloudsystem.user.contact.session;


import br.com.mcampos.ejb.cloudsystem.user.Users;
import br.com.mcampos.ejb.cloudsystem.user.contact.entity.UserContact;
import br.com.mcampos.ejb.cloudsystem.user.contact.entity.UserContactPK;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.Query;


@Stateless( name = "UserContactSession", mappedName = "CloudSystems-EjbPrj-UserContactSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class UserContactSessionBean extends Crud<UserContactPK, UserContact> implements UserContactSessionLocal
{
    public void delete( Users user )
    {
        Query query = getEntityManager().createNamedQuery( UserContact.deleteFromUser );
        query.setParameter( 1, user ).executeUpdate();
    }

    @Override
    public UserContact add( UserContact entity ) throws ApplicationException
    {
        entity = super.add( entity );
        entity.getUser().addContact( entity );
        return entity;
    }
}
