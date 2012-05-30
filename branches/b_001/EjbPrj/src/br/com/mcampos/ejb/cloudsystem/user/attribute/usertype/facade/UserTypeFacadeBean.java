package br.com.mcampos.ejb.cloudsystem.user.attribute.usertype.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.attributes.UserTypeDTO;
import br.com.mcampos.ejb.cloudsystem.user.attribute.usertype.UserTypeUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.usertype.entity.entity.UserType;
import br.com.mcampos.ejb.cloudsystem.user.attribute.usertype.session.UserTypeSessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "UserTypeFacade", mappedName = "CloudSystems-EjbPrj-UserTypeFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class UserTypeFacadeBean extends AbstractSecurity implements UserTypeFacade
{
    public static final Integer messageId = 21;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private UserTypeSessionLocal session;

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

    public UserTypeDTO get( AuthenticationDTO auth, Integer id ) throws ApplicationException
    {
        authenticate( auth );
        UserType entity = session.get( id );
        return UserTypeUtil.copy( entity );
    }

    public UserTypeDTO add( AuthenticationDTO auth, UserTypeDTO dto ) throws ApplicationException
    {
        authenticate( auth );
        UserType entity = session.get( dto.getId() );
        if ( entity != null )
            throwException( 1 );
        entity = session.add( UserTypeUtil.createEntity( dto ) );
        return UserTypeUtil.copy( entity );
    }

    public UserTypeDTO update( AuthenticationDTO auth, UserTypeDTO dto ) throws ApplicationException
    {
        authenticate( auth );
        UserType entity = session.get( dto.getId() );
        if ( entity == null )
            throwException( 2 );
        entity = session.add( UserTypeUtil.update( entity, dto ) );
        return UserTypeUtil.copy( entity );
    }

    public List<UserTypeDTO> getAll() throws ApplicationException
    {
        return UserTypeUtil.toDTOList( session.getAll() );
    }
}
