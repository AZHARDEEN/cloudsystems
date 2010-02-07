package br.com.mcampos.ejb.session.user;

import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.user.Collaborator;
import br.com.mcampos.ejb.entity.user.Users;
import br.com.mcampos.ejb.entity.user.pk.CollaboratorPK;
import br.com.mcampos.ejb.entity.user.attributes.CollaboratorType;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless( name = "CollaboratorSession", mappedName = "CloudSystems-EjbPrj-CollaboratorSession" )
public class CollaboratorSessionBean implements CollaboratorSessionLocal
{
    @PersistenceContext( unitName="EjbPrj" )
    private EntityManager em;

    public CollaboratorSessionBean()
    {
    }

    public CollaboratorType persistCollaboratorType( CollaboratorType collaboratorType )
    {
        em.persist(collaboratorType);
        return collaboratorType;
    }

    public CollaboratorType mergeCollaboratorType( CollaboratorType collaboratorType )
    {
        return em.merge(collaboratorType);
    }

    public void removeCollaboratorType( CollaboratorType collaboratorType )
    {
        collaboratorType = em.find(CollaboratorType.class, collaboratorType.getId());
        em.remove(collaboratorType);
    }

    public List<CollaboratorType> getCollaboratorTypeByCriteria( String jpqlStmt, int firstResult, int maxResults )
    {
        Query query = em.createQuery(jpqlStmt);
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    /** <code>select o from CollaboratorType o</code> */
    public List<CollaboratorType> getCollaboratorTypeFindAll()
    {
        return em.createNamedQuery("CollaboratorType.findAll").getResultList();
    }

    /** <code>select o from CollaboratorType o</code> */
    public List<CollaboratorType> getCollaboratorTypeFindAllByRange( int firstResult, int maxResults )
    {
        Query query = em.createNamedQuery("CollaboratorType.findAll");
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    public Collaborator persistCollaborator( Collaborator collaborator )
    {
        em.persist(collaborator);
        return collaborator;
    }

    public Collaborator mergeCollaborator( Collaborator collaborator )
    {
        return em.merge(collaborator);
    }

    public void removeCollaborator( Collaborator collaborator )
    {
        collaborator = em.find(Collaborator.class, new CollaboratorPK(collaborator.getCollaboratorId(), collaborator.getCompanyId(), collaborator.getFromDate()));
        em.remove(collaborator);
    }

    public List<Collaborator> getCollaboratorByCriteria( String jpqlStmt, int firstResult, int maxResults )
    {
        Query query = em.createQuery(jpqlStmt);
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    /** <code>select o from Collaborator o where o.toDate is null </code> */
    public List<Collaborator> getCollaboratorFindAll()
    {
        return em.createNamedQuery("Collaborator.findAll").getResultList();
    }

    /** <code>select o from Collaborator o where o.toDate is null </code> */
    public List<Collaborator> getCollaboratorFindAllByRange( int firstResult, int maxResults )
    {
        Query query = em.createNamedQuery("Collaborator.findAll");
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    /** <code>select count(o) from Collaborator o where o.toDate is null and o.company.id = :companyId and o.collaboratorType.id = 1</code> */
    public Integer getCollaboratorHasManager( Object companyId )
    {
        return (Integer ) em.createNamedQuery("Collaborator.hasManager")
            .setParameter("companyId", companyId)
            .getSingleResult();
    }

    /** <code>select o.collaboratorId from Collaborator o where o.toDate is null and o.company.id = :companyId and o.collaboratorType.id = 1 and o.person.id = :personId</code> */
    public Boolean getCollaboratorIsManager( Object companyId, Object personId )
    {
        return (Boolean) em.createNamedQuery("Collaborator.isManager")
            .setParameter("companyId", companyId)
            .setParameter("personId", personId).getSingleResult();
    }

    /** <code>select o from Collaborator o where o.company.id = :companyId and o.person.id = :personId and o.toDate is null</code> */
    public List<Collaborator> getCollaboratorHasCollaborator( Object companyId, Object personId )
    {
        return em.createNamedQuery("Collaborator.hasCollaborator").setParameter("companyId", companyId).setParameter("personId", personId).getResultList();
    }


    /** <code>select o from Collaborator o where o.person.id = :personId and o.toDate is null</code> */
    public List<Collaborator> getCompanies( Integer personId )
    {
        return em.createNamedQuery("Collaborator.findCompanies")
            .setParameter("personId", personId).getResultList();
    }

    public Integer getBusinessEntityCount( Integer personId )
    {
        Long count;
        
        try {
            count = (Long)em.createNamedQuery("Collaborator.countBusinessEntity")
                .setParameter("personId", personId).getSingleResult();
            return count.intValue();
        }
        catch ( NoResultException e ) {
            e = null;
            return 0;
        }
    }

    public UserDTO getBusinessEntity( Integer businessId, Integer currentUserId )
    {
        try {
            Collaborator col = (Collaborator)em.createNamedQuery("Collaborator.getBusiness")
                .setParameter("companyId", businessId)
                .setParameter("personId", currentUserId)
                .getSingleResult();
            return DTOFactory.copy ( col.getCompany() );
        }
        catch ( NoResultException e ) {
            e = null;
            return null;
        }
    }


    public List<ListUserDTO> getBusinessEntityByRange( Integer currentUserId, int firstResult, int maxResults )
    {
        Query query = em.createNamedQuery("Collaborator.getBusinessList")
            .setParameter("personId", currentUserId);
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        List<Collaborator> businessList = ( List<Collaborator>) query.getResultList();
        List<ListUserDTO> list = new ArrayList<ListUserDTO> ();
        for ( Collaborator item : businessList ) {
            list.add ( DTOFactory.copy ( (Users) item.getCompany() ) );
        }
        return list;
    }
}
