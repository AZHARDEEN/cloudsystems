package br.com.mcampos.dto.system;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class MenuDTO extends SimpleTableDTO
{
    private Integer sequence;
    private String url;

    public MenuDTO()
    {
        super();
    }

    public void setSequence( Integer sequence )
    {
        this.sequence = sequence;
    }

    public Integer getSequence()
    {
        return sequence;
    }

    public void setUrl( String url )
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }
}
