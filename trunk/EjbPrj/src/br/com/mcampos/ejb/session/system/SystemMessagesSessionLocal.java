package br.com.mcampos.ejb.session.system;

import br.com.mcampos.ejb.entity.system.SystemMessage;

import br.com.mcampos.exception.ApplicationException;

import br.com.mcampos.exception.ApplicationRuntimeException;

import java.util.List;

import javax.ejb.Local;

@Local
public interface SystemMessagesSessionLocal
{
    SystemMessage persistSystemMessage( SystemMessage systemMessage );

    SystemMessage mergeSystemMessage( SystemMessage systemMessage );

    void removeSystemMessage( SystemMessage systemMessage );

    List<SystemMessage> getSystemMessageByCriteria( String jpqlStmt, int firstResult, int maxResults );

    List<SystemMessage> getSystemMessageFindAll();

    List<SystemMessage> getSystemMessageFindAllByRange( int firstResult, int maxResults );
    
    void throwException ( Integer typeId, Integer messageId ) throws ApplicationException;    

    void throwRuntimeException ( Integer typeId, Integer messageId ) throws ApplicationRuntimeException;    
}
