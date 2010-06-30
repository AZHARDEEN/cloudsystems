package br.com.mcampos.ejb.session.system.SystemMessage;


import br.com.mcampos.ejb.entity.system.SystemMessage;
import br.com.mcampos.ejb.entity.system.SystemMessagePK;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.exception.ApplicationRuntimeException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "SystemMessagesSession", mappedName = "CloudSystems-EjbPrj-SystemMessagesSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class SystemMessagesSessionBean implements SystemMessagesSessionLocal
{
    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    public static final Integer systemCommomMessageTypeId = 2;


    public SystemMessagesSessionBean()
    {
    }

    public void throwException( Integer typeId, Integer id ) throws ApplicationException
    {
        SystemMessage msg = em.find( SystemMessage.class, new SystemMessagePK( id, typeId ) );
        if ( msg != null ) {
            throw new ApplicationException( id, msg.getMessage() );
        }
        else
            throw new ApplicationException( id, "SystemMessage [" + id.toString() + "] não está definida" );
    }


    public void throwRuntimeException( Integer typeId, Integer id ) throws ApplicationRuntimeException
    {
        SystemMessage msg = em.find( SystemMessage.class, new SystemMessagePK( id, typeId ) );
        if ( msg != null )
            throw new ApplicationRuntimeException( id, msg.getMessage() );
        else
            throw new ApplicationRuntimeException( id, "SystemMessage [" + id.toString() + "] não está definida" );
    }

}
