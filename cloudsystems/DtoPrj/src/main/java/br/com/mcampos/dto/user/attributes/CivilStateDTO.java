package br.com.mcampos.dto.user.attributes;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class CivilStateDTO extends SimpleTableDTO
{
	/**
	 *
	 */
	private static final long serialVersionUID = 9017140527037933790L;


	public CivilStateDTO( Integer id )
	{
		super( id );
	}

	public CivilStateDTO()
	{
		super();
	}

	public CivilStateDTO( Integer id, String description )
	{
		super( id, description );
	}
}
