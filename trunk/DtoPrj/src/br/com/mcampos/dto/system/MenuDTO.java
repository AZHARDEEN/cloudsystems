package br.com.mcampos.dto.system;

import br.com.mcampos.dto.core.SimpleTableDTO;

import java.util.LinkedList;
import java.util.List;

public class MenuDTO extends SimpleTableDTO
{
    private Integer sequence;
    private String url;
    private List<MenuDTO> subMenu;

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

    public void setSubMenu( List<MenuDTO> subMenu )
    {
        this.subMenu = subMenu;
    }

    public List<MenuDTO> getSubMenu()
    {
        if ( subMenu == null )
            subMenu = new LinkedList<MenuDTO>();
        return subMenu;
    }

    public void addSubMenu( MenuDTO subMenu )
    {
        if ( getSubMenu().contains( subMenu ) == false )
            getSubMenu().add( subMenu );
    }
}
