package br.com.mcampos.ejb.session.system.SystemMessage;


import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.exception.ApplicationRuntimeException;

import java.io.Serializable;

import javax.ejb.Local;


@Local
public interface SystemMessagesSessionLocal extends Serializable
{
    void throwException( Integer typeId, Integer messageId ) throws ApplicationException;

    void throwRuntimeException( Integer typeId, Integer messageId ) throws ApplicationRuntimeException;
}
