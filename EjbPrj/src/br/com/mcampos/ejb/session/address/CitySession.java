package br.com.mcampos.ejb.session.address;


import br.com.mcampos.dto.address.CityDTO;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface CitySession
{
	void add( CityDTO city );

	void update( CityDTO city );

	void delete( Integer id );

	List<CityDTO> getAll();

	CityDTO get( Integer id );

	List<CityDTO> getAll( String stateId, Integer countryId );
}
