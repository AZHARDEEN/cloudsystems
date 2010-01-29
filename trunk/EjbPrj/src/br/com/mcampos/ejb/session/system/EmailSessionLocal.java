package br.com.mcampos.ejb.session.system;

import br.com.mcampos.dto.system.SendMailDTO;

import br.com.mcampos.exception.ApplicationException;

import javax.ejb.Local;

@Local
public interface EmailSessionLocal
{
    SendMailDTO get ( Integer emailId ) throws ApplicationException;
}
