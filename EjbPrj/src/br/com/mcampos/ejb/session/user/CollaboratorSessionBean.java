package br.com.mcampos.ejb.session.user;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.security.Role;
import br.com.mcampos.ejb.entity.security.SubjectRole;
import br.com.mcampos.ejb.entity.user.Collaborator;
import br.com.mcampos.ejb.entity.user.Users;
import br.com.mcampos.ejb.entity.user.pk.CollaboratorPK;
import br.com.mcampos.ejb.entity.user.attributes.CollaboratorType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless( name = "CollaboratorSession", mappedName = "CloudSystems-EjbPrj-CollaboratorSession" )
public class CollaboratorSessionBean implements CollaboratorSessionLocal
{
    @PersistenceContext( unitName = "EjbPrj" )
    private EntityManager em;

    @EJB
    LoginSessionLocal login;

    public CollaboratorSessionBean()
    {
    }

    public CollaboratorType persistCollaboratorType( CollaboratorType collaboratorType )
    {
        em.persist( collaboratorType );
        return collaboratorType;
    }

    public CollaboratorType mergeCollaboratorType( CollaboratorType collaboratorType )
    {
        return em.merge( collaboratorType );
    }

    public void removeCollaboratorType( CollaboratorType collaboratorType )
    {
        collaboratorType = em.find( CollaboratorType.class, collaboratorType.getId() );
        em.remove( collaboratorType );
    }

    public List<CollaboratorType> getCollaboratorTypeByCriteria( String jpqlStmt, int firstResult, int maxResults )
    {
        Query query = em.createQuery( jpqlStmt );
        if ( firstResult > 0 ) {
            query = query.setFirstResult( firstResult );
        }
        if ( maxResults > 0 ) {
            query = query.setMaxResults( maxResults );
        }
        return query.getResultList();
    }

    /** <code>select o from CollaboratorType o</code> */
    public List<CollaboratorType> getCollaboratorTypeFindAll()
    {
        return em.createNamedQuery( "CollaboratorType.findAll" ).getResultList();
    }

    /** <code>select o from CollaboratorType o</code> */
    public List<CollaboratorType> getCollaboratorTypeFindAllByRange( int firstResult, int maxResults )
    {
        Query query = em.createNamedQuery( "CollaboratorType.findAll" );
        if ( firstResult > 0 ) {
            query = query.setFirstResult( firstResult );
        }
        if ( maxResults > 0 ) {
            query = query.setMaxResults( maxResults );
        }
        return query.getResultList();
    }

    public Collaborator persistCollaborator( Collaborator collaborator )
    {
        em.persist( collaborator );
        return collaborator;
    }

    public Collaborator mergeCollaborator( Collaborator collaborator )
    {
        return em.merge( collaborator );
    }

    public void removeCollaborator( Collaborator collaborator )
    {
        collaborator = em.find( Collaborator.class,
                                new CollaboratorPK( collaborator.getCollaboratorId(), collaborator.getCompanyId(),
                                                    collaborator.getFromDate() ) );
        em.remove( collaborator );
    }

    public List<Collaborator> getCollaboratorByCriteria( String jpqlStmt, int firstResult, int maxResults )
    {
        Query query = em.createQuery( jpqlStmt );
        if ( firstResult > 0 ) {
            query = query.setFirstResult( firstResult );
        }
        if ( maxResults > 0 ) {
            query = query.setMaxResults( maxResults );
        }
        return query.getResultList();
    }

    /** <code>select o from Collaborator o where o.toDate is null </code> */
    public List<Collaborator> getCollaboratorFindAll()
    {
        return em.createNamedQuery( "Collaborator.findAll" ).getResultList();
    }

    /** <code>select o from Collaborator o where o.toDate is null </code> */
    public List<Collaborator> getCollaboratorFindAllByRange( int firstResult, int maxResults )
    {
        Query query = em.createNamedQuery( "Collaborator.findAll" );
        if ( firstResult > 0 ) {
            query = query.setFirstResult( firstResult );
        }
        if ( maxResults > 0 ) {
            query = query.setMaxResults( maxResults );
        }
        return query.getResultList();
    }

    /** <code>select count(o) from Collaborator o where o.toDate is null and o.company.id = :companyId and o.collaboratorType.id = 1</code> */
    public Integer getCollaboratorHasManager( Object companyId )
    {
        return ( Integer )em.createNamedQuery( "Collaborator.hasManager" ).setParameter( "companyId", companyId )
            .getSingleResult();
    }

    /** <code>select o.collaboratorId from Collaborator o where o.toDate is null and o.company.id = :companyId and o.collaboratorType.id = 1 and o.person.id = :personId</code> */
    public Boolean getCollaboratorIsManager( Object companyId, Object personId )
    {
        return ( Boolean )em.createNamedQuery( "Collaborator.isManager" ).setParameter( "companyId", companyId )
            .setParameter( "personId", personId ).getSingleResult();
    }

    /** <code>select o from Collaborator o where o.company.id = :companyId and o.person.id = :personId and o.toDate is null</code> */
    public List<Collaborator> getCollaboratorHasCollaborator( Object companyId, Object personId )
    {
        return em.createNamedQuery( "Collaborator.hasCollaborator" ).setParameter( "companyId", companyId )
            .setParameter( "personId", personId ).getResultList();
    }


    /** <code>select o from Collaborator o where o.person.id = :personId and o.toDate is null</code> */
    public List<Collaborator> getCompanies( Integer personId )
    {
        return em.createNamedQuery( "Collaborator.findCompanies" ).setParameter( "personId", personId ).getResultList();
    }

    public Integer getBusinessEntityCount( AuthenticationDTO auth )
    {
        Long count;

        getLogin().authenticate( auth );
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

    public UserDTO getBusinessEntity( AuthenticationDTO auth, Integer businessId )
    {
        getLogin().authenticate( auth );
        try {
            Collaborator col = getCollaborator( auth, businessId );
            return DTOFactory.copy( col.getCompany() );
        }
        catch ( NoResultException e ) {
            e = null;
            return null;
        }
    }


    public List<ListUserDTO> getBusinessEntityByRange( AuthenticationDTO auth, int firstResult, int maxResults )
    {
        getLogin().authenticate( auth );
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

    protected LoginSessionLocal getLogin()
    {
        return login;
    }


    /**
     * Obtem todas as roles do usuário autenticado.
     * As roles são a base para todo o esquema de segurança do sistema.
     * Inclusive para obter o menu de acesso ao sistema.
     *
     * @param auth DTO do usuário autenticado.
     * @return A lista de roles do usuário ou null.
     */
    public List<Role> getRoles( AuthenticationDTO auth )
    {
        getLogin().authenticate( auth );

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


    public List<MenuDTO> getMenuList( AuthenticationDTO auth, Integer companyId )
    {
        getLogin().authenticate( auth );

        Collaborator collaborator;

        collaborator = getCollaborator( auth, companyId );


        return menuList;
    }
}
