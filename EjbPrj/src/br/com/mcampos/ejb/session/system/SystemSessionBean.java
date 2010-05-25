package br.com.mcampos.ejb.session.system;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.dto.user.login.AccessLogTypeDTO;
import br.com.mcampos.ejb.cloudsystem.security.entity.Menu;
import br.com.mcampos.ejb.cloudsystem.security.entity.Role;
import br.com.mcampos.ejb.cloudsystem.security.entity.Task;
import br.com.mcampos.ejb.cloudsystem.security.entity.TaskMenu;
import br.com.mcampos.ejb.cloudsystem.security.entity.TaskMenuPK;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.cloudsystem.security.accesslog.AccessLogType;
import br.com.mcampos.ejb.entity.security.PermissionAssignment;
import br.com.mcampos.ejb.entity.security.Subtask;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless( name = "SystemSession", mappedName = "CloudSystems-EjbPrj-SystemSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class SystemSessionBean extends AbstractSecurity implements SystemSessionLocal
{
    @PersistenceContext( unitName = "EjbPrj" )
    private EntityManager em;

    private static final int systemMessageType = 6;

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
    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<MenuDTO> getParentMenus( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );

        List<Menu> list;
        try {
            list = ( List<Menu> )getEntityManager().createNamedQuery( "Menu.findAll" ).getResultList();
            return toMenuDTOList( list );
        }
        catch ( NoResultException e ) {
            e = null;
            return Collections.emptyList();
        }
    }

    protected List<MenuDTO> toMenuDTOList( List<Menu> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<MenuDTO> listDTO = new ArrayList<MenuDTO>( list.size() );
        for ( Menu m : list ) {
            listDTO.add( DTOFactory.copy( m, true ) );
        }
        return listDTO;
    }

    protected List<MenuDTO> toMenuDTOListFromTaskMenu( List<TaskMenu> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<MenuDTO> listDTO = new ArrayList<MenuDTO>( list.size() );
        for ( TaskMenu m : list ) {
            listDTO.add( DTOFactory.copy( m.getMenu(), false ) );
        }
        return listDTO;
    }


    protected List<RoleDTO> toRoleDTOListFromPermission( List<PermissionAssignment> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<RoleDTO> listDTO = new ArrayList<RoleDTO>( list.size() );
        for ( PermissionAssignment m : list ) {
            listDTO.add( m.getRole().toDTO() );
        }
        return listDTO;
    }


    protected List<TaskDTO> toTaskDTOListFromTaskMenu( List<TaskMenu> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<TaskDTO> listDTO = new ArrayList<TaskDTO>( list.size() );
        for ( TaskMenu m : list ) {
            listDTO.add( DTOFactory.copy( m.getTask() ) );
        }
        System.out.println( "toTaskDTOListFromTaskMenu" );
        return listDTO;
    }

    protected List<TaskDTO> toTaskDTOList( List<Task> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<TaskDTO> listDTO = new ArrayList<TaskDTO>( list.size() );
        for ( Task m : list ) {
            listDTO.add( DTOFactory.copy( m ) );
        }
        return listDTO;
    }


    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<TaskDTO> getMenuTasks( AuthenticationDTO auth, Integer menuId ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );

        if ( SysUtils.isZero( menuId ) )
            throwException( 4 );

        try {
            System.out.println( "SystemSessionBean.getMenuTasks" );
            Menu menu = getEntityManager().find( Menu.class, menuId );
            if ( menu != null ) {
                System.out.println( "SystemSessionBean.Refreshing Menu" );
                getEntityManager().refresh( menu );
                System.out.println( "SystemSessionBean.getting Task List" );
                return toTaskDTOListFromTaskMenu( menu.getTasks() );
            }
        }
        catch ( NoResultException e ) {
            e = null;
        }
        return Collections.emptyList();
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
            if ( entity.getSequence() != dto.getSequence() ||
                 ( entity.getParentMenu() != null && entity.getParentMenu().getId() != dto.getParentId() ) ) {
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
     * Obtém o próximo formId disponível.
     * Esta função obtém o próximo número disponível para o formId do Menu (Max(formId)+1).
     * Não há necessidade de usar type para inclusão visto que a atualização desta
     * tabela é mímina.
     *
     * @param auth .
     * @return O próximo formId disponível.
     */
    public Integer getNextMenuId( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        return getNextMenuId();
    }

    /**
     * This is a very private function ( there is no AuthenticationDTO)
     * SHOULD NEVER BE PULIC
     * @return next free formId number.
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
     * @return next free formId number.
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
        Menu entity = getEntityManager().find( Menu.class, menuId.getId() );
        if ( entity == null )
            throwException( 4 );
        getEntityManager().remove( entity );
    }


    /**
     * Verifica a existência de alguma type do menu no banco de dados.
     * Existe uma chave única na tabela de menu que garante a unicidade do par (parent_id + type).
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

            q = getEntityManager().createNamedQuery( "Menu.findSequence" );
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


    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<TaskDTO> getTasks( AuthenticationDTO auth ) throws ApplicationException
    {
        List<Task> list;

        authenticate( auth, Role.systemAdmimRoleLevel );

        try {
            list = ( List<Task> )getEntityManager().createNamedQuery( "Task.findAll" ).getResultList();
            return toTaskDTOList( list );
        }
        catch ( NoResultException e ) {
            e = null;
            return Collections.emptyList();
        }
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<TaskDTO> getRootTasks( AuthenticationDTO auth ) throws ApplicationException
    {
        List<Task> list;

        authenticate( auth, Role.systemAdmimRoleLevel );

        try {
            list = ( List<Task> )getEntityManager().createNamedQuery( Task.rootTasks ).getResultList();
            return toTaskDTOList( list );
        }
        catch ( NoResultException e ) {
            e = null;
            return Collections.emptyList();
        }
    }


    public TaskDTO update( AuthenticationDTO auth, TaskDTO dto ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Task entity = DTOFactory.copy( dto );
        getEntityManager().merge( entity );
        return entity.toDTO();
    }

    public TaskDTO add( AuthenticationDTO auth, TaskDTO dto ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Task entity = DTOFactory.copy( dto );
        getEntityManager().persist( entity );
        if ( dto.getParent() != null ) {
            Task masterTask = getEntityManager().find( Task.class, dto.getParentId() );
            if ( masterTask != null ) {
                Subtask subTask = new Subtask();
                subTask.setTask( masterTask );
                subTask.setSubTask( entity );
                getEntityManager().persist( subTask );
            }
        }
        return entity.toDTO();
    }

    public Boolean validate( TaskDTO dto, Boolean isNew ) throws ApplicationException
    {
        return null;
    }

    public void delete( AuthenticationDTO auth, TaskDTO id ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Task entity = getEntityManager().find( Task.class, id );
        if ( entity != null )
            getEntityManager().remove( entity );
    }

    public Integer getNextTaskId( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        String sql;

        sql = "SELECT COALESCE ( MAX ( TSK_ID_IN ), 0 ) + 1 AS ID FROM TASK";
        Query query = getEntityManager().createNativeQuery( sql );
        try {
            return ( Integer )query.getSingleResult();
        }
        catch ( Exception e ) {
            return 1;
        }
    }

    public List<AccessLogTypeDTO> getAccessLogTypes( AuthenticationDTO auth ) throws ApplicationException
    {
        /*Não existe a necessidade de ser mais que um usuário autenticado para obter a lista de access log type*/
        authenticate( auth );
        try {
            return copyList( ( List<AccessLogType> )em.createNamedQuery( "AccessLogType.findAll" ).getResultList() );
        }
        catch ( NoResultException e ) {
            e = null;
            return Collections.emptyList();
        }
    }

    protected List<AccessLogTypeDTO> copyList( List<AccessLogType> list )
    {
        List<AccessLogTypeDTO> dtos = null;

        if ( list == null || list.size() == 0 )
            return Collections.emptyList();
        dtos = new ArrayList<AccessLogTypeDTO>( list.size() );
        for ( AccessLogType item : list )
            dtos.add( DTOFactory.copy( item ) );
        return dtos;
    }


    public Integer getNextAccessLogTypeId( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );

        return ( Integer )nativeQuerySingleResult( "Select max( coalesce ( alt_id_in, 0 ) ) + 1 as idMax from access_log_type" );
    }

    public AccessLogTypeDTO update( AuthenticationDTO auth, AccessLogTypeDTO dto ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );

        if ( validate( dto, false ) ) {
            AccessLogType entity;
            entity = DTOFactory.copy( dto );
            getEntityManager().merge( entity );
            return DTOFactory.copy( entity );
        }
        else
            return null;
    }

    public AccessLogTypeDTO add( AuthenticationDTO auth, AccessLogTypeDTO dto ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );

        if ( validate( dto, true ) ) {
            AccessLogType entity;
            entity = DTOFactory.copy( dto );
            getEntityManager().persist( entity );
            return DTOFactory.copy( entity );
        }
        else
            return null;
    }

    public Boolean validate( AccessLogTypeDTO dto, Boolean isNew ) throws ApplicationException
    {
        if ( dto == null )
            throwCommomException( 3 );
        AccessLogType entity = ( AccessLogType )get( AccessLogType.class, dto.getId() );
        if ( isNew ) {
            if ( entity != null )
                throwCommomException( 5 );
        }
        else {
            if ( entity == null )
                throwCommomException( 6 );
        }
        if ( SysUtils.isEmpty( dto.getDescription() ) )
            throwCommomException( 7 );
        return true;
    }

    public void delete( AuthenticationDTO auth, AccessLogTypeDTO id ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        if ( id == null || SysUtils.isZero( id.getId() ) )
            throwCommomException( 3 );
        AccessLogType entity = ( AccessLogType )get( AccessLogType.class, id.getId() );
        if ( entity == null )
            throwCommomException( 8 );
        try {
            getEntityManager().remove( entity );
        }
        catch ( EJBException e ) {
            throwCommomRuntimeException( 9 );
        }
    }

    protected AccessLogType getAccessLogType( Integer id )
    {
        return ( AccessLogType )get( AccessLogType.class, id );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public TaskDTO getTask( AuthenticationDTO auth, Integer taskId ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Task entity = getEntityManager().find( Task.class, taskId );
        return entity != null ? entity.toDTO() : null;
    }

    public void addMenuTask( AuthenticationDTO auth, MenuDTO menu, TaskDTO task ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Menu menuEntity = getEntityManager().find( Menu.class, menu.getId() );
        Task taskEntity = getEntityManager().find( Task.class, task.getId() );
        if ( menuEntity != null && taskEntity != null ) {
            TaskMenu tm = new TaskMenu();

            tm.setTask( taskEntity );
            tm.setMenu( menuEntity );
            getEntityManager().persist( tm );
        }
    }


    public void removeMenuTask( AuthenticationDTO auth, MenuDTO menu, TaskDTO task ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Menu menuEntity = getEntityManager().find( Menu.class, menu.getId() );
        Task taskEntity = getEntityManager().find( Task.class, task.getId() );
        if ( menuEntity != null && taskEntity != null ) {
            TaskMenu tm = getEntityManager().find( TaskMenu.class, new TaskMenuPK( menu.getId(), task.getId() ) );

            if ( tm != null )
                getEntityManager().remove( tm );
        }
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<MenuDTO> getMenus( AuthenticationDTO auth, TaskDTO dtoTask ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Task taskEntity = getEntityManager().find( Task.class, dtoTask.getId() );
        if ( taskEntity != null ) {
            List<TaskMenu> list = null;
            try {
                list = getEntityManager().createNamedQuery( TaskMenu.findByTask ).setParameter( 1, taskEntity ).getResultList();
                return toMenuDTOListFromTaskMenu( list );
            }
            catch ( NoResultException e ) {
                return Collections.emptyList();
            }

        }
        return Collections.emptyList();
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<RoleDTO> getRoles( AuthenticationDTO auth, TaskDTO dtoTask ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Task taskEntity = getEntityManager().find( Task.class, dtoTask.getId() );
        if ( taskEntity != null ) {
            List<PermissionAssignment> list = null;
            try {
                Query query = getEntityManager().createNamedQuery( PermissionAssignment.findByTask );

                query.setParameter( 1, taskEntity );
                list = query.getResultList();
                return toRoleDTOListFromPermission( list );
            }
            catch ( NoResultException e ) {
                return Collections.emptyList();
            }
        }
        return Collections.emptyList();
    }
}

