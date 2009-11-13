package br.com.mcampos.dto.user.attributes;

import br.com.mcampos.dto.core.DisplayNameDTO;

public class CivilStateDTO extends DisplayNameDTO implements Comparable< CivilStateDTO >
{
    Integer id;
    String description;
    
    public CivilStateDTO()
    {
        super();
    }

    public CivilStateDTO( Integer id, String description )
    {
        super();
        this.id = id;
        this.description = description;
    }

    public String getDisplayName()
    {
        return getDescription();
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public int compareTo( CivilStateDTO o )
    {
        return getId().compareTo( o.getId() );
    }
}
