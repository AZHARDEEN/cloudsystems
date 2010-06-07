package br.com.mcampos.dto.address;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class StateDTO extends SimpleTableDTO
{
    /**
     *
     */
    private static final long serialVersionUID = 1046569607923921204L;
    private String abbreviation;

    public StateDTO()
    {
        super();
    }

    public void setAbbreviation( String abbreviation )
    {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation()
    {
        return abbreviation;
    }

    @Override
    public String toString()
    {
        return getAbbreviation() + " - " + getDescription();
    }
}
