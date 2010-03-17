package br.com.mcampos.ejb.session.user;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.security.PermissionAssignment;
import br.com.mcampos.ejb.cloudsystem.security.entity.Role;
import br.com.mcampos.ejb.entity.security.SubjectRole;
import br.com.mcampos.ejb.entity.security.Subtask;
import br.com.mcampos.ejb.cloudsystem.security.entity.Task;
import br.com.mcampos.ejb.entity.security.TaskMenu;
import br.com.mcampos.ejb.cloudsystem.security.entity.Menu;
import br.com.mcampos.ejb.entity.user.Collaborator;
import br.com.mcampos.ejb.entity.user.Users;
import br.com.mcampos.ejb.session.system.SystemMessage.SystemMessagesSessionBean;
import br.com.mcampos.exception.ApplicationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless( name = "CollaboratorSession", mappedName = "CloudSystems-EjbPrj-CollaboratorSession" )
public class CollaboratorSessionBean extends AbstractSecurity implements CollaboratorSessionLocal
{
    @PersistenceContext( unitName = "EjbPrj" )
    private EntityManager em;

    public CollaboratorSessionBean()
    {
    }

    public Integer getBusinessEntityCount( AuthenticationDTO auth ) throws ApplicationException
    {
        Long count;

        authenticate( auth );
        try {
            count = ( Long )em.createNamedQuery( "Collaborator.countBusinessEntity" ).setParameter( "personId", auth.getUserId() )
                    .getSingleResult();
            return count.intValue();
        }
        catch ( NoResultException e ) {
            e = null;
            return 0;
        }
    }


    protected Collaborator getCollaborator( AuthenticationDTO auth, Integer businessId )
    {
        try {
            Collaborator col = ( Collaborator )em.createNamedQuery( "Collaborator.getBusiness" )
                .setParameter( "companyId", businessId ).setParameter( "personId", auth.getUserId() ).getSingleResult();
            return col;
        }
        catch ( NoResultException e ) {
            e = null;
            return null;
        }

    }

    public UserDTO getBusinessEntity( AuthenticationDTO auth, Integer businessId ) throws ApplicationException
    {
        authenticate( auth );
        try {
            Collaborator col = getCollaborator( auth, businessId );
            return DTOFactory.copy( col.getCompany() );
        }
        catch ( NoResultException e ) {
            e = null;
            return null;
        }
    }


    public List<ListUserDTO> getBusinessEntityByRange( AuthenticationDTO auth, int firstResult,
                                                       int maxResults ) throws ApplicationException
    {
        authenticate( auth );
        Query query = em.createNamedQuery( "Collaborator.getBusinessList" ).setParameter( "personId", auth.getUserId() );
        if ( firstResult > 0 ) {
            query = query.setFirstResult( firstResult );
        }
        if ( maxResults > 0 ) {
            query = query.setMaxResults( maxResults );
        }
        List<Collaborator> businessList = ( List<Collaborator> )query.getResultList();
        List<ListUserDTO> list = new ArrayList<ListUserDTO>();
        for ( Collaborator item : businessList ) {
            list.add( DTOFactory.copy( ( Users )item.getCompany() ) );
        }
        return list;
    }

    /**
     * Obtem todas as roles do usuário autenticado.
     * As roles são a base para todo o esquema de segurança do sistema.
     * Inclusive para obter o parentMenu de acesso ao sistema.
     *
     * @param auth DTO do usuário autenticado.
     * @return A lista de roles do usuário ou null.
     */
    protected List<Role> getRoles( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );

        List resultList;
        ArrayList<Role> roles = null;

        try {
            resultList = em.createNamedQuery( "SubjectRole.findCollaboratorRoles" ).setParameter( "id", auth.getUserId() )
                    .getResultList();
            if ( resultList.size() > 0 ) {
                roles = new ArrayList<Role>( resultList.size() );
                for ( SubjectRole sr : ( List<SubjectRole> )resultList ) {
                    roles.add( sr.getRole() );
                }
            }
            return roles;
        }
        catch ( NoResultException e ) {
            e = null;
            return Collections.EMPTY_LIST;
        }
    }


    protected List<Role> getRoles( Collaborator entity )
    {
        if ( entity == null )
            return Collections.EMPTY_LIST;

        List resultList;
        ArrayList<Role> roles = null;

        try {
            resultList = em.createNamedQuery( "SubjectRole.findCollaboratorRoles" )
                    .setParameter( "id", entity.getPerson().getId() ).getResultList();
            if ( resultList.size() > 0 ) {
                roles = new ArrayList<Role>( resultList.size() );
                for ( SubjectRole sr : ( List<SubjectRole> )resultList ) {
                    roles.add( sr.getRole() );
                }
            }
            return roles;
        }
        catch ( NoResultException e ) {
            e = null;
            return Collections.EMPTY_LIST;
        }
    }

    protected void getMenuList( List<Menu> menuList, Task entity )
    {
        if ( entity == null || menuList == null )
            return;
        getEntityManager().refresh( entity );
        for ( TaskMenu tm : entity.getTaskMenuList() ) {
            menuList.add( tm.getMenu() );

        }
        for ( Subtask childTask : entity.getSubtasks() ) {
            getMenuList( menuList, childTask.getSubTask() );
        }
    }

    protected void getMenuList( List<Menu> menuList, Role entity )
    {
        if ( entity == null || menuList == null )
            return;

        for ( PermissionAssignment p : entity.getPermissionAssignmentList() ) {
            getMenuList( menuList, p.getTask() );
        }
        for ( Role childRole : entity.getChildRoles() ) {
            getMenuList( menuList, childRole );
        }
    }

    protected void getMenuList( List<Menu> menuList, Collaborator entity )
    {
        if ( entity == null || menuList == null )
            return;

        List<Role> roles;

        roles = getRoles( entity );
        for ( Role r : roles ) {
            getMenuList( menuList, r );
        }
    }


    public List<MenuDTO> getMenuList( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );

        List<Collaborator> companies;
        List<Menu> menuList = new ArrayList<Menu>();
        List<MenuDTO> dtoList = new LinkedList<MenuDTO>();

        companies = getCompanies( auth );
        for ( Collaborator c : companies ) {
            getMenuList( menuList, c );
        }
        if ( menuList.size() == 0 )
            return Collections.EMPTY_LIST;
        for ( Menu m : menuList ) {
            addMenu( dtoList, m );
        }
        return dtoList;
    }

    protected MenuDTO addMenu( List menuList, Menu newMenu )
    {
        MenuDTO dto, parentDTO = null;
        int nIndex;

        if ( newMenu.getParentMenu() != null ) {
            //this menu has a parent menu.
            parentDTO = DTOFactory.copy( newMenu.getParentMenu(), false );
            nIndex = menuList.indexOf( parentDTO );
            if ( nIndex == -1 ) {
                //parent is not in list. Must Add.
                parentDTO = addMenu( menuList, newMenu.getParentMenu() );
            }
            else {
                parentDTO = ( MenuDTO )menuList.get( nIndex );
            }
            dto = DTOFactory.copy( newMenu, false );
            return addMenu( parentDTO.getSubMenu(), dto );
        }
        else {
            dto = DTOFactory.copy( newMenu, false );
            return addMenu( menuList, dto );
        }
    }

    protected MenuDTO addMenu( List menuList, MenuDTO dto )
    {
        int nIndex;

        nIndex = menuList.indexOf( dto );
        if ( nIndex == -1 ) {
            for ( MenuDTO sibling : ( List<MenuDTO> )menuList ) {
                if ( dto.getSequence() > sibling.getSequence() )
                    continue;
                nIndex = menuList.indexOf( sibling );
                break;
            }
            if ( nIndex == -1 )
                menuList.add( dto );
            else
                menuList.add( nIndex, dto );
            return dto;
        }
        else
            return ( MenuDTO )menuList.get( nIndex );
    }

    /**
     * Lista todas as ocorrências de vinculos entre o usuário atual e suas empresas
     * @param auth - Usuário Autenticado.
     * @return Lista das ocorrências ou NULL.
     */
    protected List<Collaborator> getCompanies( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );

        try {
            return em.createNamedQuery( "Collaborator.findCompanies" ).setParameter( "personId", auth.getUserId() )
                .getResultList();
        }
        catch ( NoResultException e ) {
            e = null;
            return Collections.EMPTY_LIST;
        }
    }

    public Integer getMessageTypeId()
    {
        return SystemMessagesSessionBean.systemCommomMessageTypeId;
    }

    public EntityManager getEntityManager()
    {
        return em;
    }
}
