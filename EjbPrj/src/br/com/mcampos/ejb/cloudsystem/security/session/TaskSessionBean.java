package br.com.mcampos.ejb.cloudsystem.security.session;


import br.com.mcampos.ejb.cloudsystem.security.entity.Task;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "TaskSession", mappedName = "CloudSystems-EjbPrj-TaskSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class TaskSessionBean extends Crud<Integer, Task> implements TaskSessionLocal
{
    public TaskSessionBean()
    {
    }


    public void delete( Integer key ) throws ApplicationException
    {
        if ( SysUtils.isZero( key ) )
            return;
        super.delete( Task.class, key );
    }

    public Task get( Integer key ) throws ApplicationException
    {
        if ( SysUtils.isZero( key ) )
            return null;
        return super.get( Task.class, key );
    }


    public List<Task> getAll() throws ApplicationException
    {
        return ( List<Task> )getResultList( Task.findAll );
    }


    public List<Task> getRoots() throws ApplicationException
    {
        return ( List<Task> )getResultList( Task.rootTasks );
    }

    public List<Task> getSubtasks( Task task ) throws ApplicationException
    {
        List<Task> tasks = Collections.emptyList();//( List<Task> )getResultList( Task.subTasks, task );
        return tasks;
    }

}
