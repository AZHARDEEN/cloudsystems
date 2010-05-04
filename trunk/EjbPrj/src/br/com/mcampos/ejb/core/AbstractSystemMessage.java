package br.com.mcampos.ejb.core;

import br.com.mcampos.ejb.core.BasicSessionBean.BasicSessionBean;
import br.com.mcampos.ejb.session.system.SystemMessage.SystemMessagesSessionBean;
import br.com.mcampos.ejb.session.system.SystemMessage.SystemMessagesSessionLocal;
import br.com.mcampos.exception.ApplicationException;

import javax.ejb.EJB;

public abstract class AbstractSystemMessage extends BasicSessionBean implements SystemMessageInterface
{

    @EJB
    SystemMessagesSessionLocal systemMessage;


    public AbstractSystemMessage()
    {
        super();
    }

    /**
     * Throws an ApplicationException.
     * Throws an ApplicationException that can(runTime = true), or cannot (runTime = false), causa a rollback
     * transaction.
     * All the system Messages is stored in database
     *
     * @param typeId SystemMessageTypeId - Table SystemMessageType
     * @param id - Message ID.
     * @param runTime true indicates a runtime exception.
     * @throws ApplicationException
     */
    public void throwException( int typeId, int id, boolean runTime ) throws ApplicationException
    {
        if ( runTime )
            getSystemMessage().throwRuntimeException( typeId, id );
        else
            getSystemMessage().throwException( typeId, id );
    }

    /**
     * Throws an ApplicationException.
     * Throws an ApplicationException that can(runTime = true), or cannot (runTime = false), causa a rollback
     * transaction.
     * All the system Messages is stored in database
     *
     * @param id - Message ID.
     * @param runTime true indicates a runtime exception.
     * @throws ApplicationException
     */
    public void throwException( int id, boolean runTime ) throws ApplicationException
    {
        throwException( getMessageTypeId(), id, runTime );
    }


    /**
     * Throws an ApplicationException.
     * Throws an ApplicationException that cannot cause a rollback transaction.
     * All the system Messages is stored in database
     *
     * @param id - Message ID.
     * @throws ApplicationException
     */
    public void throwException( int id ) throws ApplicationException
    {
        throwException( getMessageTypeId(), id, false );
    }


    /**
     * Throws an ApplicationException.
     * Throws an ApplicationException that CAN, and WILL, cause a rollback transaction.
     * All the system Messages is stored in database
     * It´s is important to note that this formId is from MessageType = 2
     *
     * @param id - Message ID.
     * @throws ApplicationException
     */
    public void throwRuntimeException( int id ) throws ApplicationException
    {
        throwException( getMessageTypeId(), id, true );
    }


    /**
     * Throws an Commom System ApplicationException.
     * Throws an ApplicationException that cannot cause a rollback transaction.
     * All the system Messages is stored in database
     * A Commom System has it´s own systemMessageType (2)
     * It´s is important to note that this formId is from MessageType = 2
     *
     * @param id - Message ID.
     * @throws ApplicationException
     */
    public void throwCommomException( int id ) throws ApplicationException
    {
        throwException( SystemMessagesSessionBean.systemCommomMessageTypeId, id, false );
    }


    /**
     * Throws an Commom System ApplicationException.
     * Throws an Commom System ApplicationException that CAN, and WILL, cause a rollback transaction.
     * All the system Messages is stored in database.
     * A Commom System has it´s own systemMessageType (2)
     *
     * @param id - Message ID.
     * @throws ApplicationException
     */
    public void throwCommomRuntimeException( int id ) throws ApplicationException
    {
        throwException( SystemMessagesSessionBean.systemCommomMessageTypeId, id, true );
    }

    public SystemMessagesSessionLocal getSystemMessage()
    {
        return systemMessage;
    }
}
