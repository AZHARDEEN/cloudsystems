package br.com.mcampos.dto.address;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class AddressTypeDTO extends SimpleTableDTO
{
	/**
	 *
	 */
	private static final long serialVersionUID = 5920660631361301995L;

	public AddressTypeDTO( Integer id, String description )
	{
		super( id, description );
	}

	public AddressTypeDTO( Integer id )
	{
		super( id );
	}

	public AddressTypeDTO()
	{
		super();
	}
}
