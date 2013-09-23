package br.com.mcampos.ejb.cloudsystem.system;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.ApplicationRuntimeException;

import java.io.Serializable;

import javax.ejb.Local;


@Local
public interface SystemMessagesSessionLocal extends Serializable
{
    void throwException( Integer typeId, Integer messageId ) throws ApplicationException;

    void throwRuntimeException( Integer typeId, Integer messageId ) throws ApplicationRuntimeException;
}
