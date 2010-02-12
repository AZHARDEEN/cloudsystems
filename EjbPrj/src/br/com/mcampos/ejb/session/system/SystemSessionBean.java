package br.com.mcampos.ejb.session.system;

import br.com.mcampos.dto.security.AuthenticationDTO;

import br.com.mcampos.dto.system.MenuDTO;

import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.security.Role;
import br.com.mcampos.ejb.entity.system.Menu;
import br.com.mcampos.ejb.entity.user.Collaborator;

import br.com.mcampos.ejb.session.user.CollaboratorSessionLocal;

import br.com.mcampos.ejb.session.user.LoginSessionLocal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless( name = "SystemSession", mappedName = "CloudSystems-EjbPrj-SystemSession" )
public class SystemSessionBean implements SystemSessionLocal
{
    @PersistenceContext( unitName = "EjbPrj" )
    private EntityManager em;

    @EJB
    LoginSessionLocal loginSession;

    public SystemSessionBean()
    {
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
        List<Menu> list;

        getLoginSession().authenticate( auth, Role.systemAdmimRoleLevel );

        try {
            list = ( List<Menu> )em.createNamedQuery( "Menu.findAll" ).getResultList();
            if ( list == null || list.size() == 0 )
                return Collections.EMPTY_LIST;
            ArrayList<MenuDTO> listDTO = new ArrayList<MenuDTO>( list.size() );
            for ( Menu m : list ) {
                listDTO.add( DTOFactory.copy( m ) );
            }
            return listDTO;
        }
        catch ( NoResultException e ) {
            e = null;
            return Collections.EMPTY_LIST;
        }
    }

    public void setLoginSession( LoginSessionLocal loginSession )
    {
        this.loginSession = loginSession;
    }

    public LoginSessionLocal getLoginSession()
    {
        return loginSession;
    }
}
