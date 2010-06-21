package br.com.mcampos.ejb.cloudsystem.user.media.type.session;


import br.com.mcampos.ejb.cloudsystem.user.media.type.entity.UserMediaType;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "UserMediaTypeSession", mappedName = "CloudSystems-EjbPrj-UserMediaTypeSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class UserMediaTypeSessionBean extends Crud<Integer, UserMediaType> implements UserMediaTypeSessionLocal
{
    public void delete( Integer key ) throws ApplicationException
    {
        delete( UserMediaType.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public UserMediaType get( Integer key ) throws ApplicationException
    {
        return get( UserMediaType.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<UserMediaType> getAll() throws ApplicationException
    {
        return ( List<UserMediaType> )getResultList( UserMediaType.getAll );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Integer getNextId() throws ApplicationException
    {
        return nextIntegerId( UserMediaType.nextId );
    }
}
