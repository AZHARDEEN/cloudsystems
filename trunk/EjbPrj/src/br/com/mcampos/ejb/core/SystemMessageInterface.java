package br.com.mcampos.ejb.core;

import br.com.mcampos.ejb.session.system.SystemMessage.SystemMessagesSessionLocal;
import br.com.mcampos.exception.ApplicationException;

public interface SystemMessageInterface
{
    /**
     * Get SystemMessageSession, thru it´s local interface.
     *
     * @return SystemMessageSession - Local Interface
     */
    SystemMessagesSessionLocal getSystemMessage();


    /**
     * Get a default message type id.
     *
     * @return the id of system message type
     */
    Integer getMessageTypeId();


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
    void throwException( int typeId, int id, boolean runTime ) throws ApplicationException;


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
    void throwException( int id, boolean runTime ) throws ApplicationException;

    /**
     * Throws an ApplicationException.
     * Throws an ApplicationException that cannot cause a rollback transaction.
     * All the system Messages is stored in database
     *
     * @param id - Message ID.
     * @throws ApplicationException
     */
    void throwException( int id ) throws ApplicationException;

    /**
     * Throws an ApplicationException.
     * Throws an ApplicationException that CAN, and WILL, cause a rollback transaction.
     * All the system Messages is stored in database
     *
     * @param id - Message ID.
     * @throws ApplicationException
     */
    void throwRuntimeException( int id ) throws ApplicationException;

    /**
     * Throws an Commom System ApplicationException.
     * Throws an ApplicationException that cannot cause a rollback transaction.
     * All the system Messages is stored in database
     * A Commom System has it´s own systemMessageType (2)
     * It´s is important to note that this id is from MessageType = 2
     *
     * @param id - Message ID.
     * @throws ApplicationException
     */
    void throwCommomException( int id ) throws ApplicationException;


    /**
     * Throws an Commom System ApplicationException.
     * Throws an Commom System ApplicationException that CAN, and WILL, cause a rollback transaction.
     * All the system Messages is stored in database.
     * A Commom System has it´s own systemMessageType (2)
     * It´s is important to note that this id is from MessageType = 2
     *
     * @param id - Message ID.
     * @throws ApplicationException
     */
    void throwCommomRuntimeException( int id ) throws ApplicationException;
}
