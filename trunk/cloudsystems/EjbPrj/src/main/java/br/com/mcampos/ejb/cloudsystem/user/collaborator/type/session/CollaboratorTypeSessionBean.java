package br.com.mcampos.ejb.cloudsystem.user.collaborator.type.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.type.entity.CollaboratorType;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "CollaboratorTypeSession", mappedName = "CollaboratorTypeSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class CollaboratorTypeSessionBean extends Crud<Integer, CollaboratorType> implements CollaboratorTypeSessionLocal
{
    public void delete( Integer key ) throws ApplicationException
    {
        delete( CollaboratorType.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public CollaboratorType get( Integer key ) throws ApplicationException
    {
        return get( CollaboratorType.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<CollaboratorType> getAll() throws ApplicationException
    {
        return ( List<CollaboratorType> )getResultList( CollaboratorType.getAll );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Integer getNextId() throws ApplicationException
    {
        return nextIntegerId( CollaboratorType.nextId );
    }
}
