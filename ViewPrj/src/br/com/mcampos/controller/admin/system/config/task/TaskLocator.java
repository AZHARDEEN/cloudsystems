package br.com.mcampos.controller.admin.system.config.task;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.ejb.cloudsystem.security.facade.SecurityFacade;
import br.com.mcampos.ejb.cloudsystem.security.facade.TaskInterface;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.business.BusinessDelegate;

import java.util.List;

public class TaskLocator extends BusinessDelegate implements TaskInterface
{
    private SecurityFacade facade = null;

    public TaskLocator()
    {
        super();
    }

    public List<TaskDTO> getTasks( AuthenticationDTO auth ) throws ApplicationException
    {
        return getSessionBean().getTasks( auth );
    }

    public List<TaskDTO> getSubTasks( AuthenticationDTO auth, TaskDTO dto ) throws ApplicationException
    {
        return getSessionBean().getSubTasks ( auth, dto );
    }


    public TaskDTO update( AuthenticationDTO auth, TaskDTO dto ) throws ApplicationException
    {
        return getSessionBean().update( auth, dto );
    }

    public Integer getNextTaskId( AuthenticationDTO auth ) throws ApplicationException
    {
        Integer id;

        id = getSessionBean().getNextTaskId( auth );
        return ( id == null ? 0 : id );
    }

    public TaskDTO add( AuthenticationDTO auth, TaskDTO dto ) throws ApplicationException
    {
        return getSessionBean().add( auth, dto );
    }

    public Boolean validate( TaskDTO dto, Boolean isNew ) throws ApplicationException
    {
        return getSessionBean().validate( dto, isNew );
    }

    public void delete( AuthenticationDTO auth, TaskDTO id ) throws ApplicationException
    {
        getSessionBean().delete( auth, id );
    }

    public List<TaskDTO> getRootTasks( AuthenticationDTO auth ) throws ApplicationException
    {
        return getSessionBean().getRootTasks( auth );
    }

    public TaskDTO getTask( AuthenticationDTO auth, Integer taskId ) throws ApplicationException
    {
        return getSessionBean().getTask( auth, taskId );
    }

    public List<MenuDTO> getMenus( AuthenticationDTO auth, TaskDTO dtoTask ) throws ApplicationException
    {
        return getSessionBean().getMenus( auth, dtoTask );
    }

    public List<RoleDTO> getRoles( AuthenticationDTO auth, TaskDTO dtoTask ) throws ApplicationException
    {
        return getSessionBean().getRoles( auth, dtoTask );
    }

    public List<MenuDTO> getParentMenus ( AuthenticationDTO auth ) throws ApplicationException
    {
        return getSessionBean().getParentMenus( auth );
    }

    public RoleDTO getRootRole ( AuthenticationDTO auth ) throws ApplicationException
    {
        return getSessionBean().getRootRole ( auth );
    }

    public void add( AuthenticationDTO auth, TaskDTO dtoTask, MenuDTO menuDTO ) throws ApplicationException
    {
        getSessionBean().add ( auth, dtoTask, menuDTO );
    }

    public void add( AuthenticationDTO auth, TaskDTO dtoTask, RoleDTO roleDTO ) throws ApplicationException
    {
        getSessionBean().add ( auth, dtoTask, roleDTO );
    }

    public void remove( AuthenticationDTO auth, TaskDTO dtoTask,
                        MenuDTO menuDTO )throws ApplicationException
    {
        getSessionBean().remove ( auth, dtoTask, menuDTO );
    }

    public void remove( AuthenticationDTO auth, TaskDTO dtoTask,
                        RoleDTO roleDTO )throws ApplicationException
    {
        getSessionBean().remove ( auth, dtoTask, roleDTO );
    }

    protected SecurityFacade getSessionBean ()
    {
        if ( facade == null )
            facade = ( SecurityFacade )getEJBSession( SecurityFacade.class );
        return facade;
    }
}
