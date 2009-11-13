package br.com.mcampos.ejb.session.system;

import br.com.mcampos.ejb.entity.system.SystemMessage;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless( name = "SystemMessagesSession", mappedName = "CloudSystems-EjbPrj-SystemMessagesSession" )
@Remote
@Local
public class SystemMessagesSessionBean implements SystemMessagesSession, SystemMessagesSessionLocal
{
    @PersistenceContext( unitName="EjbPrj" )
    private EntityManager em;

    public SystemMessagesSessionBean()
    {
    }

    public SystemMessage persistSystemMessage( SystemMessage systemMessage )
    {
        em.persist(systemMessage);
        return systemMessage;
    }

    public SystemMessage mergeSystemMessage( SystemMessage systemMessage )
    {
        return em.merge(systemMessage);
    }

    public void removeSystemMessage( SystemMessage systemMessage )
    {
        systemMessage = em.find(SystemMessage.class, systemMessage.getId());
        em.remove(systemMessage);
    }

    public List<SystemMessage> getSystemMessageByCriteria( String jpqlStmt, int firstResult, int maxResults )
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

    /** <code>select o from SystemMessage o</code> */
    public List<SystemMessage> getSystemMessageFindAll()
    {
        return em.createNamedQuery("SystemMessage.findAll").getResultList();
    }

    /** <code>select o from SystemMessage o</code> */
    public List<SystemMessage> getSystemMessageFindAllByRange( int firstResult, int maxResults )
    {
        Query query = em.createNamedQuery("SystemMessage.findAll");
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }
    
    public void throwMessage ( Integer messageId )
    {
        try {
            SystemMessage msg = em.find( SystemMessage.class, messageId );
            if ( msg != null ) {
                throw new EJBException ( msg.getMessage() );
            }
        } catch ( Exception e ){};
        throw new EJBException ( "SystemMessage [" + messageId.toString() + "] não está definida" );
    }
}
