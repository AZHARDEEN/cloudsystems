package br.com.mcampos.ejb.session.system.SystemMessage;

import br.com.mcampos.ejb.entity.system.SystemMessage;

import br.com.mcampos.exception.ApplicationException;

import br.com.mcampos.exception.ApplicationRuntimeException;

import java.util.List;

import javax.ejb.Local;

@Local
public interface SystemMessagesSessionLocal
{
    void throwException( Integer typeId, Integer messageId ) throws ApplicationException;

    void throwRuntimeException( Integer typeId, Integer messageId ) throws ApplicationRuntimeException;
}
