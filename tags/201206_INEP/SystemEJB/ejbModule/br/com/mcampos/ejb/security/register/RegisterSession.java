package br.com.mcampos.ejb.security.register;

import javax.ejb.Remote;

import br.com.mcampos.dto.RegisterDTO;

@Remote
public interface RegisterSession
{
	Boolean register( RegisterDTO dto ) throws Exception;

	public Boolean hasLogin( RegisterDTO dto ) throws Exception;

	public Boolean validate( String token, String password ) throws Exception;
}
