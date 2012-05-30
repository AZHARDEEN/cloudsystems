package br.com.mcampos.ejb.cloudsystem.security.accesslog;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.login.AccessLogTypeDTO;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface AccessLogTypeFacade
{
	List<AccessLogTypeDTO> getAll( AuthenticationDTO currentUser ) throws ApplicationException;

	Integer getNextId( AuthenticationDTO currentUser ) throws ApplicationException;

	void add( AuthenticationDTO currentUser, AccessLogTypeDTO dto ) throws ApplicationException;

	void update( AuthenticationDTO currentUser, AccessLogTypeDTO dto ) throws ApplicationException;

	void delete( AuthenticationDTO currentUser, AccessLogTypeDTO dto ) throws ApplicationException;
}
