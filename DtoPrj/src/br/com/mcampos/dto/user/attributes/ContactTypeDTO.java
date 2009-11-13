package br.com.mcampos.dto.user.attributes;

import br.com.mcampos.dto.core.DisplayNameDTO;

import java.io.Serializable;

public class ContactTypeDTO extends DisplayNameDTO
{
    private Boolean allowDuplicate;
    private String description;
    private Integer id;
    private String mask;
    
    
    public ContactTypeDTO()
    {
        super();
    }
    
    public ContactTypeDTO ( Integer id, String description, String mask, Boolean allowDuplicate )
    {
        init ( id, description, mask, allowDuplicate );
    }
    
    protected void init ( Integer id, String description, String mask, Boolean allowDuplicate )
    {
        this.id = id;
        this.description = description;
        this.mask = mask;
        this.allowDuplicate = allowDuplicate;
    }

    public void setAllowDuplicate( Boolean allowDuplicate )
    {
        this.allowDuplicate = allowDuplicate;
    }

    public Boolean getAllowDuplicate()
    {
        return allowDuplicate;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setMask( String mask )
    {
        this.mask = mask;
    }

    public String getMask()
    {
        return mask;
    }

    public String getDisplayName()
    {
        return getDescription();
    }
}
