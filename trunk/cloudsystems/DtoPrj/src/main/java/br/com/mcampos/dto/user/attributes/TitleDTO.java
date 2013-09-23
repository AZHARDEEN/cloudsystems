package br.com.mcampos.dto.user.attributes;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class TitleDTO extends SimpleTableDTO
{
    /**
     *
     */
    private static final long serialVersionUID = 6432837000318062671L;
    private String abbreviation;


    public TitleDTO()
    {
        super();
    }

    public TitleDTO( Integer id, String description, String abbreviation )
    {
        super();
        init( id, description, abbreviation );
    }


    protected void init( Integer id, String description, String abbreviation )
    {
        setId( id );
        setDescription( description );
        setAbbreviation( abbreviation );
    }


    public void setAbbreviation( String abbreviation )
    {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation()
    {
        return abbreviation;
    }
}
