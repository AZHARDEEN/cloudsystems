package br.com.mcampos.ejb.session.system;

import br.com.mcampos.ejb.entity.system.SystemMessage;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface SystemMessagesSession
{
    SystemMessage persistSystemMessage( SystemMessage systemMessage );

    SystemMessage mergeSystemMessage( SystemMessage systemMessage );

    void removeSystemMessage( SystemMessage systemMessage );

    List<SystemMessage> getSystemMessageByCriteria( String jpqlStmt, int firstResult, int maxResults );

    List<SystemMessage> getSystemMessageFindAll();

    List<SystemMessage> getSystemMessageFindAllByRange( int firstResult, int maxResults );

    void throwMessage ( Integer messageId );
}
