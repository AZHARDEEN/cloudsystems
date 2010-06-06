package br.com.mcampos.ejb.cloudsystem.user.attribute.userstatus.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.attributes.UserStatusDTO;
import br.com.mcampos.ejb.cloudsystem.user.attribute.userstatus.UserStatusUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.userstatus.entity.UserStatus;
import br.com.mcampos.ejb.cloudsystem.user.attribute.userstatus.session.session.UserStatusSessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "UserStatusFacade", mappedName = "CloudSystems-EjbPrj-UserStatusFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class UserStatusFacadeBean extends AbstractSecurity implements UserStatusFacade
{
    public static final Integer messageId = 21;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private UserStatusSessionLocal session;

    public UserStatusFacadeBean()
    {
    }

    public Integer nextId( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        return session.nextIntegerId();
    }

    public void delete( AuthenticationDTO auth, Integer id ) throws ApplicationException
    {
        authenticate( auth );
        UserStatus entity = session.get( id );
        if ( entity == null )
            throwException( 3 );
        session.delete( id );
    }

    public UserStatusDTO get( AuthenticationDTO auth, Integer id ) throws ApplicationException
    {
        authenticate( auth );
        return UserStatusUtil.copy( session.get( id ) );
    }

    public UserStatusDTO add( AuthenticationDTO auth, UserStatusDTO dto ) throws ApplicationException
    {
        authenticate( auth );
        UserStatus entity = session.get( dto.getId() );
        if ( entity != null )
            throwException( 1 );
        entity = session.add( UserStatusUtil.createEntity( dto ) );
        return UserStatusUtil.copy( entity );
    }

    public UserStatusDTO update( AuthenticationDTO auth, UserStatusDTO dto ) throws ApplicationException
    {
        authenticate( auth );
        UserStatus entity = session.get( dto.getId() );
        if ( entity == null )
            throwException( 1 );
        entity = session.update( UserStatusUtil.update( entity, dto ) );
        return UserStatusUtil.copy( entity );
    }

    public List<UserStatusDTO> getAll() throws ApplicationException
    {
        return UserStatusUtil.toDTOList( session.getAll() );
    }

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }
}
