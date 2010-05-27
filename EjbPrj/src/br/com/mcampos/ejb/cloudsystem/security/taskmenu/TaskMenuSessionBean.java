package br.com.mcampos.ejb.cloudsystem.security.taskmenu;


import br.com.mcampos.ejb.cloudsystem.security.menu.Menu;
import br.com.mcampos.ejb.cloudsystem.security.task.Task;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "TaskMenuSession", mappedName = "CloudSystems-EjbPrj-TaskMenuSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class TaskMenuSessionBean extends Crud<TaskMenuPK, TaskMenu> implements TaskMenuSessionLocal
{
    public TaskMenuSessionBean()
    {
    }


    public TaskMenu add( Menu menu, Task task ) throws ApplicationException
    {
        TaskMenu tm;

        tm = get( menu, task );
        if ( tm == null ) {
            tm = add( new TaskMenu( menu, task ) );
            getEntityManager().refresh( menu );
            getEntityManager().refresh( task );
        }
        return tm;
    }

    public void delete( Menu menu, Task task ) throws ApplicationException
    {
        TaskMenu tm = get( menu, task );
        if ( tm != null ) {
            getEntityManager().remove( tm );
            getEntityManager().refresh( menu );
            getEntityManager().refresh( task );
        }
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public TaskMenu get( Menu menu, Task task ) throws ApplicationException
    {
        return get( TaskMenu.class, new TaskMenuPK( menu.getId(), task.getId() ) );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<TaskMenu> getAll( Menu menu ) throws ApplicationException
    {
        if ( menu == null )
            return Collections.emptyList();
        return ( List<TaskMenu> )getResultList( TaskMenu.findByMenu, menu );
    }
}

