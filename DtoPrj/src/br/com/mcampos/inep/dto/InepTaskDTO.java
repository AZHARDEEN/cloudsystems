package br.com.mcampos.inep.dto;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class InepTaskDTO extends SimpleTableDTO
{
    @SuppressWarnings( "compatibility:-7735987700032290759" )
    private static final long serialVersionUID = 1L;

    public InepTaskDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public InepTaskDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public InepTaskDTO( Integer integer )
    {
        super( integer );
    }

    public InepTaskDTO()
    {
        super();
    }
}
