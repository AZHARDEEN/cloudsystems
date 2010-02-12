package br.com.mcampos.ejb.facade;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.ejb.session.system.SystemSessionLocal;

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
     * aqueles menus que não possuem nenhum menu pai.
     *
     * @param auth - dto do usuário autenticado no sistema.
     * @return Lista com os menus
     */
    public List<MenuDTO> getMenuList( AuthenticationDTO auth )
    {
        if ( auth == null )
            return Collections.EMPTY_LIST;

        return getSystemSession().getMenuList( auth );
    }
}
