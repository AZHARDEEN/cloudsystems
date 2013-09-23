package br.com.mcampos.ejb.cloudsystem.user.contact.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.user.Users;
import br.com.mcampos.ejb.cloudsystem.user.attribute.contacttype.entity.ContactType;
import br.com.mcampos.ejb.cloudsystem.user.attribute.contacttype.session.ContactTypeSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.contact.entity.UserContact;
import br.com.mcampos.ejb.cloudsystem.user.contact.entity.UserContactPK;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;


@Stateless( name = "UserContactSession", mappedName = "UserContactSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class UserContactSessionBean extends Crud<UserContactPK, UserContact> implements UserContactSessionLocal
{

    @EJB
    ContactTypeSessionLocal contactTypeSession;


    @Override
    public void delete( Users user )
    {
        if ( user == null )
            return;
        Query query = getEntityManager().createNamedQuery( UserContact.deleteFromUser );
        query.setParameter( 1, user ).executeUpdate();
        user.getContacts().clear();
    }

    @Override
    public UserContact add( UserContact entity ) throws ApplicationException
    {
        linkToContactType( entity );
        entity = super.add( entity );
        entity.getUser().addContact( entity );
        linkToUser( entity );
        return entity;
    }

    private void linkToContactType( UserContact entity ) throws ApplicationException
    {
        if ( entity == null || entity.getContactType() == null )
            return;
        ContactType type = contactTypeSession.get( entity.getContactType().getId() );
        entity.setContactType( type );
    }

    @Override
    public void refresh( Users user, List<UserContact> contacts ) throws ApplicationException
    {
        if ( user == null )
            return;
        delete( user );
        for ( UserContact d : contacts ) {
            add( d );
        }
    }

    private void linkToUser( UserContact entity )
    {
        if ( entity != null ) {
            Users user = entity.getUser();
            if ( user != null )
                user.addContact( entity );
        }
    }

    private void unlinkToUser( UserContact entity )
    {
        if ( entity != null ) {
            Users user = entity.getUser();
            if ( user != null )
                user.removeContact( entity );
        }
    }

    @Override
    public UserContact update( UserContact entity ) throws ApplicationException
    {
        linkToContactType( entity );
        return super.update( entity );
    }
}
