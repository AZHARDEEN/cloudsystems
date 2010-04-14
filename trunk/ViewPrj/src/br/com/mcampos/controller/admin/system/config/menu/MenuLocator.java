package br.com.mcampos.controller.admin.system.config.menu;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.ejb.core.MenuInterface;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.util.business.SystemLocator;

import java.util.Collections;
import java.util.List;

public class MenuLocator extends SystemLocator implements MenuInterface
{
    public MenuLocator()
    {
        super();
    }

    /**
     * Obtem a lista dos menus de primeiro nível, ou seja,
     * aqueles menus que não possuem nenhum parentMenu pai.
     *
     * @param auth - dto do usuário autenticado no sistema.
     * @return List<MenuDTO> - Lista dos menus
     */
    public List<MenuDTO> getMenus( AuthenticationDTO auth ) throws ApplicationException
    {
        if ( auth == null )
            return Collections.EMPTY_LIST;
        return getSessionBean().getMenus( auth );
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
        if ( auth == null || validate( dto, false ) == false )
            return null;
        return getSessionBean().update( auth, dto );
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
        return getSessionBean().getNextMenuId( auth );
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
        if ( auth == null || validate( dto, true ) == false )
            return null;
        return getSessionBean().add( auth, dto );
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
        return getSessionBean().getNextSequence( auth, parentId );
    }

    public Boolean validate( MenuDTO dto, Boolean isNew )
    {
        if ( dto == null )
            return false;
        return true;
    }

    public void delete( AuthenticationDTO auth, MenuDTO menuId ) throws ApplicationException
    {
        if ( auth == null || menuId == null )
            return;
        getSessionBean().delete( auth, menuId );
    }

    public List<TaskDTO> getMenuTasks( AuthenticationDTO auth, Integer menuId ) throws ApplicationException
    {
        if ( auth == null || SysUtils.isZero( menuId ) )
            return Collections.emptyList();
        return getSessionBean().getMenuTasks( auth, menuId );
    }

    public List<TaskDTO> getRootTasks( AuthenticationDTO auth ) throws ApplicationException
    {
        return getSessionBean().getRootTasks( auth );
    }

    public void addMenuTask ( AuthenticationDTO auth, MenuDTO menu, TaskDTO task ) throws ApplicationException
    {
        getSessionBean().addMenuTask ( auth, menu, task );
    }
}
