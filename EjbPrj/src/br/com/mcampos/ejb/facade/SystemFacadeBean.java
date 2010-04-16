package br.com.mcampos.ejb.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.dto.user.login.AccessLogTypeDTO;
import br.com.mcampos.ejb.cloudsystem.security.session.RoleSessionLocal;
import br.com.mcampos.ejb.session.system.SystemSessionLocal;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "SystemFacade", mappedName = "CloudSystems-EjbPrj-SystemFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class SystemFacadeBean implements SystemFacade
{
    @EJB
    SystemSessionLocal systemSession;

    @EJB
    RoleSessionLocal roleSession;

    public SystemFacadeBean()
    {
    }

    public SystemSessionLocal getSystemSession()
    {
        return systemSession;
    }

    /**
     * Obtem a lista dos menus de primeiro nível, ou seja,
     * aqueles menus que não possuem nenhum parentMenu pai.
     *
     * @param auth - dto do usuário autenticado no sistema.
     * @return Lista com os menus
     */
    @TransactionAttribute( TransactionAttributeType.NEVER )
    public List<MenuDTO> getParentMenus( AuthenticationDTO auth ) throws ApplicationException
    {
        if ( auth == null )
            return Collections.emptyList();
        return getSystemSession().getParentMenus( auth );
    }

    /**
     * Atualiza o parentMenu.
     * Esta função é usada para atualizar um dto (persistir) no banco de dados.
     *
     * @param auth - dto do usuário autenticado no sistema.
     * @param dto - o item a ser atualizado.
     */
    public MenuDTO update( AuthenticationDTO auth, MenuDTO dto ) throws ApplicationException
    {
        if ( auth == null )
            return null;
        if ( validate( dto, false ) )
            return getSystemSession().update( auth, dto );
        else
            return null;
    }

    /**
     * Obtém o próximo id disponível.
     * Esta função obtém o próximo número disponível para o id do Menu (Max(id)+1).
     * Não há necessidade de usar type para inclusão visto que a atualização desta
     * tabela é mímina.
     *
     * @param auth .
     * @return O próximo id disponível.
     */
    public Integer getNextMenuId( AuthenticationDTO auth ) throws ApplicationException
    {
        if ( auth == null )
            return 0;
        return getSystemSession().getNextMenuId( auth );
    }

    /**
     * Adiciona um novo registro (Menu) no banco de dados - Persist
     * Insere um novo menu no banco de dados.
     *
     * @param auth.
     * @param dto - DTO com os dados no novo menu.
     */
    public MenuDTO add( AuthenticationDTO auth, MenuDTO dto ) throws ApplicationException
    {
        if ( auth == null )
            return null;
        if ( validate( dto, true ) )
            return getSystemSession().add( auth, dto );
        else
            return null;
    }

    public Boolean validate( MenuDTO dto, Boolean isNew ) throws ApplicationException
    {
        if ( dto == null )
            return false;
        if ( SysUtils.isEmpty( dto.getDescription() ) )
            return false;
        if ( !isNew ) {
            if ( SysUtils.isZero( dto.getId() ) )
                return false;
        }
        return true;
    }

    /**
     * Each menu is configured to show in a especific order, and this is the order.
     *
     *
     * @param auth
     * @param parentId
     * @return next available type.
     * @throws ApplicationException
     */
    public Integer getNextSequence( AuthenticationDTO auth, Integer parentId ) throws ApplicationException
    {
        if ( auth == null )
            return 0;
        return getSystemSession().getNextSequence( auth, parentId );
    }

    public void delete( AuthenticationDTO auth, MenuDTO menuId ) throws ApplicationException
    {
        if ( auth == null || menuId == null )
            return;
        getSystemSession().delete( auth, menuId );
    }

    @TransactionAttribute( TransactionAttributeType.NEVER )
    public List<TaskDTO> getMenuTasks( AuthenticationDTO auth, Integer menuId ) throws ApplicationException
    {
        if ( auth == null || SysUtils.isZero( menuId ) )
            return Collections.emptyList();
        System.out.println( "SystemFacade.getMenuTasks" );
        return getSystemSession().getMenuTasks( auth, menuId );
    }

    @TransactionAttribute( TransactionAttributeType.NEVER )
    public List<TaskDTO> getTasks( AuthenticationDTO auth ) throws ApplicationException
    {
        return getSystemSession().getTasks( auth );
    }

    public TaskDTO update( AuthenticationDTO auth, TaskDTO dto ) throws ApplicationException
    {
        return getSystemSession().update( auth, dto );
    }

    public TaskDTO add( AuthenticationDTO auth, TaskDTO dto ) throws ApplicationException
    {
        return getSystemSession().add( auth, dto );
    }

    public Boolean validate( TaskDTO dto, Boolean isNew ) throws ApplicationException
    {
        return getSystemSession().validate( dto, isNew );
    }

    public void delete( AuthenticationDTO auth, TaskDTO id ) throws ApplicationException
    {
        getSystemSession().delete( auth, id );
    }

    public Integer getNextTaskId( AuthenticationDTO auth ) throws ApplicationException
    {
        return getSystemSession().getNextTaskId( auth );
    }

    public List<AccessLogTypeDTO> getAccessLogTypes( AuthenticationDTO auth ) throws ApplicationException
    {
        return getSystemSession().getAccessLogTypes( auth );
    }

    public Integer getNextAccessLogTypeId( AuthenticationDTO auth ) throws ApplicationException
    {
        return getSystemSession().getNextAccessLogTypeId( auth );
    }

    public AccessLogTypeDTO update( AuthenticationDTO auth, AccessLogTypeDTO dto ) throws ApplicationException
    {
        return getSystemSession().update( auth, dto );
    }

    public AccessLogTypeDTO add( AuthenticationDTO auth, AccessLogTypeDTO dto ) throws ApplicationException
    {
        return getSystemSession().add( auth, dto );
    }

    public Boolean validate( AccessLogTypeDTO dto, Boolean isNew ) throws ApplicationException
    {
        return getSystemSession().validate( dto, isNew );
    }

    public void delete( AuthenticationDTO auth, AccessLogTypeDTO id ) throws ApplicationException
    {
        getSystemSession().delete( auth, id );
    }

    @TransactionAttribute( TransactionAttributeType.NEVER )
    public List<TaskDTO> getRootTasks( AuthenticationDTO auth ) throws ApplicationException
    {
        return getSystemSession().getRootTasks( auth );
    }

    @TransactionAttribute( TransactionAttributeType.NEVER )
    public TaskDTO getTask( AuthenticationDTO auth, Integer taskId ) throws ApplicationException
    {
        return getSystemSession().getTask( auth, taskId );
    }

    public void addMenuTask( AuthenticationDTO auth, MenuDTO menu, TaskDTO task ) throws ApplicationException
    {
        getSystemSession().addMenuTask( auth, menu, task );
    }

    public void removeMenuTask( AuthenticationDTO auth, MenuDTO menu, TaskDTO task ) throws ApplicationException
    {
        getSystemSession().removeMenuTask( auth, menu, task );
    }

    @TransactionAttribute( TransactionAttributeType.NEVER )
    public List<MenuDTO> getMenus( AuthenticationDTO auth, TaskDTO dtoTask ) throws ApplicationException
    {
        return getSystemSession().getMenus( auth, dtoTask );
    }

    @TransactionAttribute( TransactionAttributeType.NEVER )
    public List<RoleDTO> getRoles( AuthenticationDTO auth, TaskDTO dtoTask ) throws ApplicationException
    {
        return getSystemSession().getRoles( auth, dtoTask );
    }

    public RoleDTO getRootRole( AuthenticationDTO auth ) throws ApplicationException
    {
        return roleSession.getRootRole().toDTO();
    }
}
