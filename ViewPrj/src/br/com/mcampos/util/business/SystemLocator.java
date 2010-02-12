package br.com.mcampos.util.business;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.ejb.facade.SystemFacade;

import java.util.Collections;
import java.util.List;

public class SystemLocator extends BusinessDelegate
{
    public SystemLocator()
    {
        super();
    }


    protected SystemFacade getSessionBean()
    {
        return ( SystemFacade )getEJBSession( SystemFacade.class );
    }


    /**
     * Obtem a lista dos menus de primeiro nível, ou seja,
     * aqueles menus que não possuem nenhum menu pai.
     *
     * @param auth - dto do usuário autenticado no sistema.
     * @return List<MenuDTO> - Lista dos menus
     */
    public List<MenuDTO> getMenuList( AuthenticationDTO auth )
    {
        if ( auth == null )
            return Collections.EMPTY_LIST;
        return getSessionBean().getMenuList( auth );
    }

    /**
     * Atualiza o menu.
     * Esta função é usada para atualizar um dto (persistir) no banco de dados.
     *
     * @param auth - dto do usuário autenticado no sistema.
     * @param dto - o item a ser atualizado.
     */
    public void updateMenu( AuthenticationDTO auth, MenuDTO dto )
    {
        if ( auth == null || dto == null )
            return;
        getSessionBean().updateMenu( auth, dto );
    }

}
