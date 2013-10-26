package br.com.mcampos.ejb.cloudsystem.anoto.facade;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.dto.anoto.PgcPageDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MediaDTO;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;


@Remote
public interface ReviseFacade extends Serializable
{
    List<PgcFieldDTO> getFields( AuthenticationDTO auth, PgcPageDTO page ) throws ApplicationException;

    byte[] getObject( MediaDTO key ) throws ApplicationException;

    void update( AuthenticationDTO auth, PgcFieldDTO dto ) throws ApplicationException;

    void setStatus( AuthenticationDTO auth, PgcPageDTO page, Integer status ) throws ApplicationException;
}
