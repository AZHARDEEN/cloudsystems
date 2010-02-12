package br.com.mcampos.ejb.session.system;

import br.com.mcampos.dto.security.AuthenticationDTO;

import br.com.mcampos.dto.system.MenuDTO;

import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.security.Role;
import br.com.mcampos.ejb.entity.system.Menu;

import br.com.mcampos.ejb.session.user.LoginSessionLocal;

import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless( name = "SystemSession", mappedName = "CloudSystems-EjbPrj-SystemSession" )
public class SystemSessionBean implements SystemSessionLocal
{
    @PersistenceContext( unitName = "EjbPrj" )
    private EntityManager em;

    @EJB
    LoginSessionLocal loginSession;

    @EJB
    SystemMessagesSessionLocal systemMessage;


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
                listDTO.add( DTOFactory.copy( m, true ) );
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

    /**
     * Atualiza o menu.
     * Esta função é usada para atualizar um dto (persistir) no banco de dados.
     *
     * @param auth - dto do usuário autenticado no sistema.
     * @param dto - o item a ser atualizado.
     */
    public void updateMenu( AuthenticationDTO auth, MenuDTO dto )
    {
        getLoginSession().authenticate( auth, Role.systemAdmimRoleLevel );

        if ( dto == null )
            getSystemMessage().throwRuntimeException( SystemMessagesSessionBean.systemCommomMessageTypeId, 3 );
        if ( SysUtils.isZero( dto.getId() ) )
            getSystemMessage().throwRuntimeException( SystemMessagesSessionBean.systemCommomMessageTypeId, 4 );
        if ( SysUtils.isZero( dto.getSequence() ) )
            getSystemMessage().throwRuntimeException( SystemMessagesSessionBean.systemCommomMessageTypeId, 4 );
        if ( SysUtils.isEmpty( dto.getDescription() ) )
            getSystemMessage().throwRuntimeException( SystemMessagesSessionBean.systemCommomMessageTypeId, 4 );

        /*
         * TODO: Melhorar o sistema de exceção...
         */

        Menu entity;

        entity = em.find( Menu.class, dto.getId() );
        if ( entity == null )
            getSystemMessage().throwRuntimeException( SystemMessagesSessionBean.systemCommomMessageTypeId, 4 );
        DTOFactory.copy( entity, dto );

        /*Do whe have the same parent */
        Menu oldParent = entity.getMenu();

        if ( oldParent != null )
            oldParent.removeMenu( entity );
        if ( dto.getParent() == null || SysUtils.isZero( dto.getParent().getId() ) )
            entity.setMenu( null );
        else {
            Menu parent;

            parent = em.find( Menu.class, dto.getParent().getId() );

            if ( parent != null && parent.getMenuList().size() > 0 )
                parent.setTargetURL( null );
            entity.setMenu( parent );
            em.merge( parent );
        }
        em.merge( entity );
    }

    public SystemMessagesSessionLocal getSystemMessage()
    {
        return systemMessage;
    }


}

