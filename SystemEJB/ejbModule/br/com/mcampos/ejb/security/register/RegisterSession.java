package br.com.mcampos.ejb.security.register;

import javax.ejb.Remote;

import br.com.mcampos.dto.RegisterDTO;
import br.com.mcampos.ejb.core.BaseSessionInterface;

@Remote
public interface RegisterSession extends BaseSessionInterface
{
	Boolean register( RegisterDTO dto ) throws Exception;

	public Boolean hasLogin( RegisterDTO dto ) throws Exception;

	public Boolean validate( String token, String password ) throws Exception;
}
