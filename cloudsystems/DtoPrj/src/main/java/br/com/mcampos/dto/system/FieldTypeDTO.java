package br.com.mcampos.dto.system;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class FieldTypeDTO extends SimpleTableDTO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3918949070600736886L;
	public static final Integer typeString   = 1;
    public static final Integer typeInteger  = 2;
    public static final Integer typeDate     = 3;
    public static final Integer typeHour     = 4;
    public static final Integer typeDecimal  = 5;
    public static final Integer typeBoolean   = 6;

    public FieldTypeDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public FieldTypeDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public FieldTypeDTO( Integer integer )
    {
        super( integer );
    }

    public FieldTypeDTO()
    {
        super();
    }
}
