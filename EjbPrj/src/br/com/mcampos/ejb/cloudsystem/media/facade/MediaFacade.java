package br.com.mcampos.ejb.cloudsystem.media.facade;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.exception.ApplicationException;

import javax.ejb.Remote;

@Remote
public interface MediaFacade
{
    public MediaDTO add( AuthenticationDTO auth, MediaDTO entity ) throws ApplicationException;

    public void delete( AuthenticationDTO auth, Integer key ) throws ApplicationException;
}
