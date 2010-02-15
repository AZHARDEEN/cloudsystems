package br.com.mcampos.ejb.facade;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.dto.system.TaskDTO;
import br.com.mcampos.ejb.session.system.SystemSessionLocal;

import br.com.mcampos.exception.ApplicationException;

import br.com.mcampos.sysutils.SysUtils;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless( name = "SystemFacade", mappedName = "CloudSystems-EjbPrj-SystemFacade" )
@Remote
public class SystemFacadeBean implements SystemFacade
{
    @EJB
    SystemSessionLocal systemSession;

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
    public List<MenuDTO> get( AuthenticationDTO auth ) throws ApplicationException
    {
        if ( auth == null )
            return Collections.emptyList();
        return getSystemSession().get( auth );
    }

    /**
     * Atualiza o parentMenu.
     * Esta função é usada para atualizar um dto (persistir) no banco de dados.
     *
     * @param auth - dto do usuário autenticado no sistema.
     * @param dto - o item a ser atualizado.
     */
    public void update( AuthenticationDTO auth, MenuDTO dto ) throws ApplicationException
    {
        if ( auth == null )
            return;
        if ( validate( dto, false ) )
            getSystemSession().update( auth, dto );
    }

    /**
     * Obtém o próximo id disponível.
     * Esta função obtém o próximo número disponível para o id do Menu (Max(id)+1).
     * Não há necessidade de usar sequence para inclusão visto que a atualização desta
     * tabela é mímina.
     *
     * @param auth.
     * @return O próximo id disponível.
     */
    public Integer getNextId( AuthenticationDTO auth ) throws ApplicationException
    {
        if ( auth == null )
            return 0;
        return getSystemSession().getNextId( auth );
    }

    /**
     * Adiciona um novo registro (Menu) no banco de dados - Persist
     * Insere um novo menu no banco de dados.
     *
     * @param auth.
     * @param dto - DTO com os dados no novo menu.
     */
    public void add( AuthenticationDTO auth, MenuDTO dto ) throws ApplicationException
    {
        if ( auth == null )
            return;
        if ( validate( dto, true ) )
            getSystemSession().add( auth, dto );
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
     * @return next available sequence.
     * @throws ApplicationException
     */
    public Integer getNextSequence( AuthenticationDTO auth, Integer parentId ) throws ApplicationException
    {
        if ( auth == null )
            return 0;
        return getSystemSession().getNextSequence( auth, parentId );
    }

    public void delete( AuthenticationDTO auth, Integer menuId ) throws ApplicationException
    {
        if ( auth == null || SysUtils.isZero( menuId ) )
            return;
        getSystemSession().delete( auth, menuId );
    }

    public List<TaskDTO> getTasks( AuthenticationDTO auth, Integer menuId ) throws ApplicationException
    {
        if ( auth == null || SysUtils.isZero( menuId ) )
            return Collections.emptyList();
        return getSystemSession().getTasks( auth, menuId );
    }
}
