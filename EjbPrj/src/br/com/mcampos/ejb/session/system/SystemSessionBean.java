package br.com.mcampos.ejb.session.system;

import br.com.mcampos.dto.security.AuthenticationDTO;

import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.dto.system.MenuDTO;

import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.security.Role;
import br.com.mcampos.ejb.entity.security.Task;
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
    public List<MenuDTO> getMenus( AuthenticationDTO auth ) throws ApplicationException
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


    public List<TaskDTO> getMenuTasks( AuthenticationDTO auth, Integer menuId ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );

        if ( SysUtils.isZero( menuId ) )
            throwException( 4 );

        try {
            Menu menu = em.find( Menu.class, menuId );
            ArrayList<TaskDTO> listDTO = new ArrayList<TaskDTO>( menu.getTasks().size() );
            for ( TaskMenu m : menu.getTasks() ) {
                getEntityManager().refresh( m );
                listDTO.add( DTOFactory.copy( m.getTask(), false ) );
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
                dto.setId( getNextMenuId() );
            if ( existsSequence( dto.getParentId(), dto.getSequence() ) )
                dto.setSequence( getNextSequence( dto.getParentId() ) );
        }
        else {
            /*this is an update operation. Must have an id*/
            if ( SysUtils.isZero( dto.getId() ) )
                throwCommomException( 4 );
            Menu entity = getEntityManager().find( Menu.class, dto.getId() );
            if ( entity == null )
                throwCommomRuntimeException( 4 );
            if ( entity.getSequence() != dto.getSequence() || entity.getParentMenu().getId() != dto.getParentId() ) {
                /*
                 * ou a sequence foi alterada ou o menu pai foi alterado.
                 * Em ambos os casos, deve-se verificar a existencia do para (parent_id + sequence)
                 */
                if ( existsSequence( dto.getParentId(), dto.getSequence() ) )
                    dto.setSequence( getNextSequence( dto.getParentId() ) );
            }
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
    public MenuDTO update( AuthenticationDTO auth, MenuDTO dto ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        validate( dto, false );

        Menu entity;

        entity = getEntityManager().find( Menu.class, dto.getId() );
        changeParent( entity, dto ); /*this line MUST be before DTOFactory.copy*/
        DTOFactory.copy( entity, dto );
        if ( entity.getSubMenus().size() > 0 ) {
            /*A parent menu cannot have a target url! I guess!!!*/
            entity.setTargetURL( null );
        }
        return DTOFactory.copy( entity, true );
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
    public Integer getNextMenuId( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        return getNextMenuId();
    }

    /**
     * This is a very private function ( there is no AuthenticationDTO)
     * SHOULD NEVER BE PULIC
     * @return next free id number.
     */
    private Integer getNextMenuId()
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
    private Integer getNextSequence( int parentId ) throws ApplicationException
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
    public MenuDTO add( AuthenticationDTO auth, MenuDTO dto ) throws ApplicationException
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
        return DTOFactory.copy( entity, true );
    }

    public Integer getMessageTypeId()
    {
        return systemMessageType;
    }

    public EntityManager getEntityManager()
    {
        return em;
    }

    public void delete( AuthenticationDTO auth, MenuDTO menuId ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        if ( menuId == null )
            return;

        if ( SysUtils.isZero( menuId.getId() ) )
            throwException( 4 );
        Menu entity = em.find( Menu.class, menuId.getId() );
        if ( entity == null )
            throwException( 4 );
        getEntityManager().remove( entity );
    }


    /**
     * Verifica a existência de alguma sequence do menu no banco de dados.
     * Existe uma chave única na tabela de menu que garante a unicidade do par (parent_id + sequence).
     * Se existir tal combinação, o sistema deverá gerar outra.
     *
     * @param parent Id do menu pai (mnu_parent_id_in)
     * @param sequence A sequencia que deseja pesquisar (mnu_sequence_in)
     * @return Menu se existir ou null.
     */
    protected boolean existsSequence( int parent, int sequence )
    {
        Integer menu;

        try {
            Query q;

            q = em.createNamedQuery( "Menu.findSequence" );
            q.setParameter( 1, parent );
            q.setParameter( 2, sequence );
            menu = ( Integer )q.getSingleResult();
            return ( menu == null || menu == 0 ? false : true );
        }
        catch ( NoResultException e ) {
            e = null;
            menu = null;
        }
        return false;
    }


    public List<TaskDTO> getTasks( AuthenticationDTO auth ) throws ApplicationException
    {
        List<Task> list;

        authenticate( auth, Role.systemAdmimRoleLevel );

        try {
            getEntityManager().clear();
            getEntityManager().flush();
            list = ( List<Task> )getEntityManager().createNamedQuery( "Task.findAll" ).getResultList();
            if ( list == null || list.size() == 0 )
                return Collections.emptyList();
            ArrayList<TaskDTO> listDTO = new ArrayList<TaskDTO>( list.size() );
            for ( Task m : list ) {
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

    public TaskDTO update( AuthenticationDTO auth, TaskDTO dto )
    {
        return null;
    }

    public TaskDTO add( AuthenticationDTO auth, TaskDTO dto )
    {
        return null;
    }

    public Boolean validate( TaskDTO dto, Boolean isNew )
    {
        return null;
    }

    public void delete( AuthenticationDTO auth, TaskDTO id )
    {
    }

    public Integer getNextTaskId( AuthenticationDTO auth )
    {
        return 0;
    }
}

