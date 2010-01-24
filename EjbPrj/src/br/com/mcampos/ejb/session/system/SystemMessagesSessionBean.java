package br.com.mcampos.ejb.session.system;

import br.com.mcampos.ejb.entity.system.SystemMessage;

import br.com.mcampos.ejb.entity.system.SystemMessagePK;
import br.com.mcampos.exception.ApplicationException;

import br.com.mcampos.exception.ApplicationRuntimeException;

import java.util.List;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless( name = "SystemMessagesSession", mappedName = "CloudSystems-EjbPrj-SystemMessagesSession" )
public class SystemMessagesSessionBean implements SystemMessagesSessionLocal
{
    @PersistenceContext( unitName="EjbPrj" )
    private EntityManager em;
    
    public static final Integer systemCommomMessageTypeId = 2;


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
    
    public void throwException ( Integer typeId, Integer id ) throws ApplicationException
    {
        SystemMessage msg = em.find( SystemMessage.class, new SystemMessagePK ( id, typeId ) );
        if ( msg != null ) 
            throw new ApplicationException ( id, msg.getMessage() );
        else
            throw new ApplicationException ( id, "SystemMessage [" + id.toString() + "] não está definida" );
    }


    public void throwRuntimeException ( Integer typeId, Integer id ) throws ApplicationRuntimeException
    {
        SystemMessage msg = em.find( SystemMessage.class, new SystemMessagePK ( id, typeId ) );
        if ( msg != null ) 
            throw new ApplicationRuntimeException ( id, msg.getMessage() );
        else
            throw new ApplicationRuntimeException ( id, "SystemMessage [" + id.toString() + "] não está definida" );
    }

}
