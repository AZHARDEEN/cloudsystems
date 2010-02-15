package br.com.mcampos.dto.system;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class TaskDTO extends SimpleTableDTO
{
    public TaskDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public TaskDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public TaskDTO( Integer integer )
    {
        super( integer );
    }

    public TaskDTO()
    {
        super();
    }
}
