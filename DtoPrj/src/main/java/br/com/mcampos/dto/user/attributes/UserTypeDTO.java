package br.com.mcampos.dto.user.attributes;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class UserTypeDTO extends SimpleTableDTO
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static final Integer userTypePerson = 1;


	public UserTypeDTO( SimpleTableDTO simpleTableDTO )
	{
		super( simpleTableDTO );
	}

	public UserTypeDTO( String integer, String string )
	{
		super( Integer.parseInt( integer ), string );
	}

	public UserTypeDTO( Integer integer )
	{
		super( integer );
	}

	public UserTypeDTO()
	{
		super();
	}

	public static UserTypeDTO createUserTypePerson()
	{
		return new UserTypeDTO( UserTypeDTO.userTypePerson );
	}
}
