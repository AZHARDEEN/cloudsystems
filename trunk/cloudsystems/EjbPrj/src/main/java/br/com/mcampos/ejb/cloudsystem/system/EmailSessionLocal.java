package br.com.mcampos.ejb.cloudsystem.system;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.system.SendMailDTO;

import java.io.Serializable;

import javax.ejb.Local;


@Local
public interface EmailSessionLocal extends Serializable
{
    SendMailDTO get( Integer emailId ) throws ApplicationException;
}
