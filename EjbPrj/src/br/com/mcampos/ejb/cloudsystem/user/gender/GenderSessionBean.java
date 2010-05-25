package br.com.mcampos.ejb.cloudsystem.user.gender;


import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "GenderSession", mappedName = "CloudSystems-EjbPrj-GenderSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class GenderSessionBean extends Crud<Integer, Gender> implements GenderSessionLocal
{
    public GenderSessionBean()
    {
    }

    public void delete( Integer key ) throws ApplicationException
    {
        delete( Gender.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Gender get( Integer key ) throws ApplicationException
    {
        return get( Gender.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<Gender> getAll() throws ApplicationException
    {
        return getAll( Gender.getAll );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Integer getNextId() throws ApplicationException
    {
        Integer nextId = null;

        try {
            nextId = ( Integer )getSingleResult( Gender.getNextId );
        }
        catch ( Exception e ) {
            nextId = null;
        }
        if ( nextId == null )
            nextId = 1;
        nextId++;
        return nextId;
    }
}
