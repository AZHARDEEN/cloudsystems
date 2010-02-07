package br.com.mcampos.ejb.session.user;

import br.com.mcampos.ejb.entity.user.Client;

import br.com.mcampos.ejb.entity.user.pk.ClientsPK;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless( name = "ClientSession", mappedName = "CloudSystems-EjbPrj-ClientSession" )
public class ClientSessionBean implements ClientSessionLocal
{
    @PersistenceContext( unitName="EjbPrj" )
    private EntityManager em;

    public ClientSessionBean()
    {
    }

    public Client persistClients( Client clients )
    {
        em.persist(clients);
        return clients;
    }

    public Client mergeClients( Client clients )
    {
        return em.merge(clients);
    }

    public void removeClients( Client clients )
    {
        clients = em.find(Client.class, new ClientsPK(clients.getFromDate(), clients.getClientId(), clients.getUserId()));
        em.remove(clients);
    }

    public List<Client> getClientsByCriteria( String jpqlStmt, int firstResult, int maxResults )
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

    /** <code>select o from Clients o </code> */
    public List<Client> getClientsFindAll()
    {
        return em.createNamedQuery("Clients.findAll").getResultList();
    }

    /** <code>select o from Clients o </code> */
    public List<Client> getClientsFindAllByRange( int firstResult, int maxResults )
    {
        Query query = em.createNamedQuery("Clients.findAll");
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    /** <code>select o from Clients o where o.owner.id = :owner and ( toDate is null or toDate >= now() ) </code> */
    public List<Client> getClientsFindAllActive( Integer owner )
    {
        return em.createNamedQuery("Clients.findAllActive").setParameter("owner", owner).getResultList();
    }

    /** <code>select o from Clients o where o.owner.id = :owner and ( toDate is null or toDate >= now() ) </code> */
    public List<Client> getClientsFindAllActiveByRange( Integer owner, int firstResult, int maxResults )
    {
        Query query = em.createNamedQuery("Clients.findAllActive").setParameter("owner", owner);
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    /** <code>select o from Clients o where o.owner.id = :owner and o.client.id = :client and ( toDate is null or toDate >= now() ) </code> */
    public Client getClientsFind( Integer owner, Integer client )
    {
        return (Client) em.createNamedQuery("Clients.find")
            .setParameter("owner", owner)
            .setParameter("client", client).getSingleResult();
    }
    
    public Long getCount ( Integer owner )
    {
        Long count = null;
        
        try {
            count = (Long) em.createNamedQuery("Clients.countActive")
                .setParameter("owner", owner)
                .getSingleResult();
        }
        catch ( NoResultException e ) 
        {
            count = new Long (0);
        }
        return count;
    }
}
