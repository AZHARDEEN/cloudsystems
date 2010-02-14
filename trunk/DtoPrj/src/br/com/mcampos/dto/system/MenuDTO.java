package br.com.mcampos.dto.system;

import br.com.mcampos.dto.core.SimpleTableDTO;

import java.util.ArrayList;
import java.util.List;

public class MenuDTO extends SimpleTableDTO
{
    private Integer sequence;
    private String targetURL;
    private List<MenuDTO> subMenu;

    private Boolean separatorBefore;
    private Boolean autocheck;
    private Boolean checked;
    private Boolean checkmark;
    private Boolean disabled;

    private MenuDTO parent;
    private Integer parentId;


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

    public void setTargetURL( String url )
    {
        this.targetURL = url;
    }

    public String getTargetURL()
    {
        return targetURL;
    }

    public void setSubMenu( List<MenuDTO> subMenu )
    {
        this.subMenu = subMenu;
    }

    public List<MenuDTO> getSubMenu()
    {
        if ( subMenu == null )
            subMenu = new ArrayList<MenuDTO>();
        return subMenu;
    }

    public void addSubMenu( MenuDTO subMenu )
    {
        if ( getSubMenu().contains( subMenu ) == false )
            getSubMenu().add( subMenu );
    }

    public void removeSubMenu( MenuDTO subMenu )
    {
        getSubMenu().remove( subMenu );
    }

    public void setSeparatorBefore( Boolean separatorBefore )
    {
        this.separatorBefore = separatorBefore;
    }

    public Boolean getSeparatorBefore()
    {
        return separatorBefore;
    }

    public void setAutocheck( Boolean autocheck )
    {
        this.autocheck = autocheck;
    }

    public Boolean getAutocheck()
    {
        return autocheck;
    }

    public void setChecked( Boolean checked )
    {
        this.checked = checked;
    }

    public Boolean getChecked()
    {
        return checked;
    }

    public void setCheckmark( Boolean checkmark )
    {
        this.checkmark = checkmark;
    }

    public Boolean getCheckmark()
    {
        return checkmark;
    }

    public void setDisabled( Boolean disabled )
    {
        this.disabled = disabled;
    }

    public Boolean getDisabled()
    {
        return disabled;
    }

    @Override
    public String toString()
    {
        return getDisplayName();
    }

    public void setParent( MenuDTO parent )
    {
        this.parent = parent;
        this.setParentId( parent != null ? parent.getId() : 0 );
    }

    public MenuDTO getParent()
    {
        return parent;
    }

    public void setParentId( Integer parentId )
    {
        this.parentId = parentId;
    }

    public Integer getParentId()
    {
        return parentId;
    }
}
