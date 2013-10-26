package br.com.mcampos.dto.user.attributes;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class ContactTypeDTO extends SimpleTableDTO
{
    /**
     *
     */
    private static final long serialVersionUID = 5242040715166101448L;
    private Boolean allowDuplicate;
    private String mask;


    public ContactTypeDTO( Integer id, String description )
    {
        super( id, description );
    }

    public ContactTypeDTO( Integer id )
    {
        super( id );
    }

    public ContactTypeDTO()
    {
        super();
    }

    public void setAllowDuplicate( Boolean allowDuplicate )
    {
        this.allowDuplicate = allowDuplicate;
    }

    public Boolean getAllowDuplicate()
    {
        return allowDuplicate;
    }

    public void setMask( String mask )
    {
        this.mask = mask;
    }

    public String getMask()
    {
        return mask;
    }
}
