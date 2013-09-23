package br.com.mcampos.ejb.cloudsystem.user.collaborator.type.facade;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.collaborator.CollaboratorTypeDTO;
import br.com.mcampos.ejb.cloudsystem.AbstractSecurity;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.type.CollaboratorTypeUtil;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.type.entity.CollaboratorType;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.type.session.CollaboratorTypeSessionLocal;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "CollaboratorTypeFacade", mappedName = "CollaboratorTypeFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class CollaboratorTypeFacadeBean extends AbstractSecurity implements CollaboratorTypeFacade
{
    public static final Integer messageId = 30;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private CollaboratorTypeSessionLocal session;

    public CollaboratorTypeFacadeBean()
    {
    }

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }

    public List<CollaboratorTypeDTO> getAll( AuthenticationDTO currentUser ) throws ApplicationException
    {
        authenticate( currentUser );
        List<CollaboratorType> list = session.getAll();
        return CollaboratorTypeUtil.toDTOList( list );
    }

    public Integer getNextId( AuthenticationDTO currentUser ) throws ApplicationException
    {
        authenticate( currentUser );
        return session.getNextId();
    }

    public void add( AuthenticationDTO currentUser, CollaboratorTypeDTO dto ) throws ApplicationException
    {
        authenticate( currentUser );
        CollaboratorType e = session.get( dto.getId() );
        if ( e != null )
            throwException( 1 );
        e = CollaboratorTypeUtil.createEntity( dto );
        session.add( e );
    }

    public void update( AuthenticationDTO currentUser, CollaboratorTypeDTO dto ) throws ApplicationException
    {
        authenticate( currentUser );
        CollaboratorType e = session.get( dto.getId() );
        if ( e == null )
            throwException( 2 );
        e = CollaboratorTypeUtil.update( e, dto );
        session.update( e );
    }

    public void delete( AuthenticationDTO currentUser, CollaboratorTypeDTO dto ) throws ApplicationException
    {
        authenticate( currentUser );
        CollaboratorType e = session.get( dto.getId() );
        if ( e == null )
            throwException( 2 );
        session.delete( e.getId() );
    }
}
