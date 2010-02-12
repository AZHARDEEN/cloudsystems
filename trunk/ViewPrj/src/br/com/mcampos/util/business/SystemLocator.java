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
}
