package br.com.mcampos.ejb.session.address;


import br.com.mcampos.dto.address.StateDTO;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface StateSession
{
	void add( StateDTO state );

	void update( StateDTO state );

	void delete( String countryId, Integer regionId, Integer id );

	public List<StateDTO> getAll();

	public List<StateDTO> getAll( Integer countryId );
}
