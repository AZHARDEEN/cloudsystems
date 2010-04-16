package br.com.mcampos.ejb.cloudsystem.security.session;


import br.com.mcampos.ejb.cloudsystem.security.entity.Menu;
import br.com.mcampos.ejb.cloudsystem.security.entity.Task;
import br.com.mcampos.ejb.cloudsystem.security.entity.TaskMenu;
import br.com.mcampos.ejb.cloudsystem.security.entity.TaskMenuPK;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import javax.ejb.Stateless;

@Stateless( name = "TaskMenuSession", mappedName = "CloudSystems-EjbPrj-TaskMenuSession" )
public class TaskMenuSessionBean extends Crud<TaskMenuPK, TaskMenu> implements TaskMenuSessionLocal
{
    public TaskMenuSessionBean()
    {
    }


    public TaskMenu add( Menu menu, Task task ) throws ApplicationException
    {
        TaskMenu tm = add( new TaskMenu( menu, task ) );
        //menu.add( tm );
        //task.add( tm );
        return tm;
    }

    public void delete( Menu menu, Task task ) throws ApplicationException
    {
        TaskMenu tm = get( menu, task );
        if ( tm != null ) {
            getEntityManager().remove( tm );
            //menu.remove( tm );
            //task.remove( tm );
        }
    }

    public TaskMenu get( Menu menu, Task task ) throws ApplicationException
    {
        return get( TaskMenu.class, new TaskMenuPK( menu.getId(), task.getId() ) );
    }
}

