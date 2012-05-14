package br.com.mcampos.ejb.inep.task;


import br.com.mcampos.ejb.cloudsystem.user.attribute.gender.entity.Gender;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "InepTaskSession", mappedName = "CloudSystems-EjbPrj-InepTaskSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class InepTaskSessionBean extends Crud<Integer, InepTask> implements InepTaskSessionLocal
{
    public InepTaskSessionBean()
    {
    }

    @Override
    public void delete( Integer key ) throws ApplicationException
    {
        delete( InepTask.class, key );
    }

    @Override
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public InepTask get( Integer key ) throws ApplicationException
    {
        return get( InepTask.class, key );
    }

    @Override
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<InepTask> getAll() throws ApplicationException
    {
        return getAll( InepTask.getAll );
    }

    @Override
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Integer getNextId() throws ApplicationException
    {
        return nextIntegerId( InepTask.getNextId );
    }
}
