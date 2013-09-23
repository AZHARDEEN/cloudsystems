package br.com.mcampos.ejb.cloudsystem.media.facade;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MediaDTO;

@Remote
public interface MediaFacade
{
	public MediaDTO add( AuthenticationDTO auth, MediaDTO entity ) throws ApplicationException;

	public void delete( AuthenticationDTO auth, Integer key ) throws ApplicationException;

	byte[ ] getObject( AuthenticationDTO auth, Integer key ) throws ApplicationException;

	List<MediaDTO> getAllPgc( AuthenticationDTO auth ) throws ApplicationException;
}
