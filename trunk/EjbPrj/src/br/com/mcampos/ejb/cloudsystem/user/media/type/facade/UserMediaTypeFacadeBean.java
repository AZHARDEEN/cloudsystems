package br.com.mcampos.ejb.cloudsystem.user.media.type.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.attributes.UserMediaTypeDTO;
import br.com.mcampos.ejb.cloudsystem.user.media.type.UserMediaTypeUtil;
import br.com.mcampos.ejb.cloudsystem.user.media.type.entity.UserMediaType;
import br.com.mcampos.ejb.cloudsystem.user.media.type.session.UserMediaTypeSessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "UserMediaTypeFacade", mappedName = "CloudSystems-EjbPrj-UserMediaTypeFacade" )
public class UserMediaTypeFacadeBean extends AbstractSecurity implements UserMediaTypeFacade
{
    public static final Integer messageId = 31;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private UserMediaTypeSessionLocal session;

    public UserMediaTypeFacadeBean()
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

    public List<UserMediaTypeDTO> getAll( AuthenticationDTO currentUser ) throws ApplicationException
    {
        authenticate( currentUser );
        List<UserMediaType> list = session.getAll();
        return UserMediaTypeUtil.toDTOList( list );
    }

    public Integer getNextId( AuthenticationDTO currentUser ) throws ApplicationException
    {
        authenticate( currentUser );
        return session.getNextId();
    }

    public void add( AuthenticationDTO currentUser, UserMediaTypeDTO dto ) throws ApplicationException
    {
        authenticate( currentUser );
        UserMediaType e = session.get( dto.getId() );
        if ( e != null )
            throwException( 1 );
        e = UserMediaTypeUtil.createEntity( dto );
        session.add( e );
    }

    public void update( AuthenticationDTO currentUser, UserMediaTypeDTO dto ) throws ApplicationException
    {
        authenticate( currentUser );
        UserMediaType e = session.get( dto.getId() );
        if ( e == null )
            throwException( 2 );
        e = UserMediaTypeUtil.update( e, dto );
        session.update( e );
    }

    public void delete( AuthenticationDTO currentUser, Integer id ) throws ApplicationException
    {
        authenticate( currentUser );
        UserMediaType e = session.get( id );
        if ( e == null )
            throwException( 2 );
        session.delete( e.getId() );
    }
}
