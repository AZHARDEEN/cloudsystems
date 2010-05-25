package br.com.mcampos.dto.user.attributes;


import br.com.mcampos.dto.core.SimpleTableDTO;

import java.util.ArrayList;

public class GenderDTO extends SimpleTableDTO
{
    /**
     *
     */
    private static final long serialVersionUID = 3160924071819518136L;
    protected ArrayList<TitleDTO> titles;

    public GenderDTO()
    {
        super();
    }

    public GenderDTO( Integer id, String description )
    {
        super( id, description );
    }

    public void setTitles( ArrayList<TitleDTO> titles )
    {
        this.titles = titles;
    }

    public ArrayList<TitleDTO> getTitles()
    {
        return titles;
    }

    public String getDisplayName()
    {
        return getDescription();
    }
}


