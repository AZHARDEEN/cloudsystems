package br.com.mcampos.ejb.session.system;

import br.com.mcampos.dto.security.AuthenticationDTO;

import br.com.mcampos.dto.system.MenuDTO;

import br.com.mcampos.dto.system.TaskDTO;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.security.Role;
import br.com.mcampos.ejb.entity.security.TaskMenu;
import br.com.mcampos.ejb.entity.system.Menu;

import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless( name = "SystemSession", mappedName = "CloudSystems-EjbPrj-SystemSession" )
public class SystemSessionBean extends AbstractSecurity implements SystemSessionLocal
{
    @PersistenceContext( unitName = "EjbPrj" )
    private EntityManager em;

    private static int systemMessageType = 6;

    public SystemSessionBean()
    {
    }


    /**
     * Obtem a lista dos menus de primeiro nível, ou seja,
     * aqueles menus que não possuem nenhum parentMenu pai.
     *
     * @param auth - dto do usuário autenticado no sistema.
     * @return a lista de menus
     */
    public List<MenuDTO> get( AuthenticationDTO auth ) throws ApplicationException
    {
        List<Menu> list;

        authenticate( auth, Role.systemAdmimRoleLevel );

        try {
            getEntityManager().clear();
            getEntityManager().flush();
            list = ( List<Menu> )getEntityManager().createNamedQuery( "Menu.findAll" ).getResultList();
            if ( list == null || list.size() == 0 )
                return Collections.emptyList();
            ArrayList<MenuDTO> listDTO = new ArrayList<MenuDTO>( list.size() );
            for ( Menu m : list ) {
                getEntityManager().refresh( m );
                listDTO.add( DTOFactory.copy( m, true ) );
            }
            return listDTO;
        }
        catch ( NoResultException e ) {
            e = null;
            return Collections.emptyList();
        }
    }


    public List<TaskDTO> getTasks( AuthenticationDTO auth, Integer menuId ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );

        if ( SysUtils.isZero( menuId ) )
            throwException( 4 );

        try {
            Menu menu = em.find( Menu.class, menuId );
            ArrayList<TaskDTO> listDTO = new ArrayList<TaskDTO>( menu.getTasks().size() );
            for ( TaskMenu m : menu.getTasks() ) {
                getEntityManager().refresh( m );
                listDTO.add( DTOFactory.copy( m.getTask() ) );
            }
            return listDTO;
        }
        catch ( NoResultException e ) {
            e = null;
            return Collections.emptyList();
        }
    }


    public Boolean validate( MenuDTO dto, Boolean isNew ) throws ApplicationException
    {
        if ( dto == null )
            throwCommomException( 3 );
        if ( isNew ) {
            /*this is an insert operation*/
            if ( SysUtils.isZero( dto.getId() ) )
                dto.setId( getNextId() );
        }
        else {
            /*this is an update operation. Must have an id*/
            if ( SysUtils.isZero( dto.getId() ) )
                throwCommomException( 4 );
        }
        if ( SysUtils.isEmpty( dto.getDescription() ) )
            throwException( 6 );
        return true;
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
        authenticate( auth, Role.systemAdmimRoleLevel );
        validate( dto, false );

        Menu entity;

        entity = getEntityManager().find( Menu.class, dto.getId() );
        if ( entity == null )
            throwCommomRuntimeException( 4 );
        changeParent( entity, dto ); /*this line MUST be before DTOFactory.copy*/
        DTOFactory.copy( entity, dto );
        if ( entity.getSubMenus().size() > 0 ) {
            /*A parent menu cannot have a target url! I guess!!!*/
            entity.setTargetURL( null );
        }
    }

    protected void changeParent( Menu entity, MenuDTO dto ) throws ApplicationException
    {
        Menu newParent = null, oldParent;

        /*Get old parent and new parent, if any*/
        oldParent = entity.getParentMenu();
        if ( oldParent != null && oldParent.equals( newParent ) == false ) {
            oldParent.removeMenu( entity );
        }
        if ( SysUtils.isZero( dto.getParentId() ) == false ) {
            newParent = getEntityManager().find( Menu.class, dto.getParentId() );
            if ( newParent == null )
                throwRuntimeException( 3 );
        }
        entity.setParentMenu( newParent );
        if ( oldParent != null )
            getEntityManager().merge( oldParent );
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
        authenticate( auth, Role.systemAdmimRoleLevel );
        return getNextId();
    }

    /**
     * This is a very private function ( there is no AuthenticationDTO)
     * SHOULD NEVER BE PULIC
     * @return next free id number.
     */
    private Integer getNextId()
    {
        int id;

        try {
            id = ( Integer )getEntityManager().createNativeQuery( "SELECT MAX(MNU_ID_IN) FROM MENU" ).getSingleResult();
            id++;
        }
        catch ( Exception e ) {
            id = 1;
            e = null;
        }
        return id;
    }

    public Integer getNextSequence( AuthenticationDTO auth, Integer parentId ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        return getNextSequence( parentId );
    }


    /**
     * This is a very private function ( there is no AuthenticationDTO)
     * SHOULD NEVER BE PULIC
     * @return next free id number.
     */
    private Integer getNextSequence( Integer parentId ) throws ApplicationException
    {
        if ( SysUtils.isZero( parentId ) )
            return 0;
        Integer sequence;

        try {
            Query q;

            q = getEntityManager().createNamedQuery( "Menu.nexSequence" );
            q.setParameter( 1, parentId );
            sequence = ( Integer )q.getSingleResult();
            /*
             * In this case, we do not need increment. A native query do it by itsefl.
             */
        }
        catch ( NoResultException e ) {
            sequence = 1;
            e = null;
        }
        return sequence;
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
        authenticate( auth, Role.systemAdmimRoleLevel );
        validate( dto, true );

        Menu entity, parentMenu = null;
        /*If exists, cannot add*/
        entity = getEntityManager().find( Menu.class, dto.getId() );
        if ( entity != null )
            throwException( 2 );

        if ( SysUtils.isZero( dto.getParentId() ) == false ) {
            /*Do we have this parent?*/
            parentMenu = getEntityManager().find( Menu.class, dto.getParentId() );
            if ( parentMenu == null )
                throwException( 3 );
        }
        entity = DTOFactory.copy( dto );
        entity.setParentMenu( parentMenu );
        getEntityManager().persist( entity );
    }

    public Integer getMessageTypeId()
    {
        return systemMessageType;
    }

    public EntityManager getEntityManager()
    {
        return em;
    }

    public void delete( AuthenticationDTO auth, Integer menuId ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        if ( SysUtils.isZero( menuId ) )
            throwException( 4 );
        Menu entity = em.find( Menu.class, menuId );
        if ( entity == null )
            throwException( 4 );
        getEntityManager().remove( entity );
    }


}

