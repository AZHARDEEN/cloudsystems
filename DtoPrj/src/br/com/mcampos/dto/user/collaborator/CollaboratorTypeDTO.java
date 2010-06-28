package br.com.mcampos.dto.user.collaborator;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class CollaboratorTypeDTO extends SimpleTableDTO
{
    private Boolean inheritRole;

    public CollaboratorTypeDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public CollaboratorTypeDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public CollaboratorTypeDTO( Integer integer )
    {
        super( integer );
    }

    public CollaboratorTypeDTO()
    {
        super();
    }

    public void setInheritRole( Boolean inheritRole )
    {
        this.inheritRole = inheritRole;
    }

    public Boolean getInheritRole()
    {
        return inheritRole;
    }
}
