package br.com.mcampos.ejb.cloudsystem.user.attribute.contacttype.facade;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.attributes.ContactTypeDTO;
import br.com.mcampos.ejb.cloudsystem.AbstractSecurity;
import br.com.mcampos.ejb.cloudsystem.user.attribute.contacttype.ContactTypeUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.contacttype.entity.ContactType;
import br.com.mcampos.ejb.cloudsystem.user.attribute.contacttype.session.ContactTypeSessionLocal;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "ContactTypeFacade", mappedName = "ContactTypeFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class ContactTypeFacadeBean extends AbstractSecurity implements ContactTypeFacade
{
    public static final Integer messageId = 21;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private ContactTypeSessionLocal session;

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }

    public Integer nextId( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        return null;
    }

    public void delete( AuthenticationDTO auth, Integer id ) throws ApplicationException
    {
        authenticate( auth );
        session.delete( id );
    }

    public ContactTypeDTO get( AuthenticationDTO auth, Integer id ) throws ApplicationException
    {
        authenticate( auth );
        ContactType entity = session.get( id );
        return ContactTypeUtil.copy( entity );
    }

    public ContactTypeDTO add( AuthenticationDTO auth, ContactTypeDTO dto ) throws ApplicationException
    {
        authenticate( auth );
        ContactType entity = session.get( dto.getId() );
        if ( entity != null )
            throwException( 1 );
        entity = session.add( ContactTypeUtil.createEntity( dto ) );
        return ContactTypeUtil.copy( entity );
    }

    public ContactTypeDTO update( AuthenticationDTO auth, ContactTypeDTO dto ) throws ApplicationException
    {
        authenticate( auth );
        ContactType entity = session.get( dto.getId() );
        if ( entity == null )
            throwException( 2 );
        entity = session.add( ContactTypeUtil.update( entity, dto ) );
        return ContactTypeUtil.copy( entity );
    }

    public List<ContactTypeDTO> getAll() throws ApplicationException
    {
        return ContactTypeUtil.toDTOList( session.getAll() );
    }
}
