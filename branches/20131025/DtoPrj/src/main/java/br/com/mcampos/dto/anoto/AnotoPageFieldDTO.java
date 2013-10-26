package br.com.mcampos.dto.anoto;


import br.com.mcampos.dto.system.FieldTypeDTO;

import java.io.Serializable;

public class AnotoPageFieldDTO implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2935004264865798925L;
	private Boolean icr;
    private AnotoPageDTO page;
    private String name;
    private Integer width;
    private Integer height;
    private Integer top;
    private Integer left;
    private FieldTypeDTO type;
    private Boolean export;
    private Boolean searchable;
    private Integer sequence;
    private Boolean pk = false;


    public AnotoPageFieldDTO()
    {
        super();
    }


    public AnotoPageFieldDTO( AnotoPageDTO page, String name, FieldTypeDTO type )
    {
        super();
        setPage( page );
        setName( name );
        setType( type );
    }


    public void setIcr( Boolean icr )
    {
        this.icr = icr;
    }

    public Boolean getIcr()
    {
        if ( icr == null )
            icr = true;
        return icr;
    }

    public void setPage( AnotoPageDTO page )
    {
        this.page = page;
    }

    public AnotoPageDTO getPage()
    {
        return page;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setWidth( Integer width )
    {
        this.width = width;
    }

    public Integer getWidth()
    {
        return width;
    }

    public void setHeight( Integer height )
    {
        this.height = height;
    }

    public Integer getHeight()
    {
        return height;
    }

    public void setTop( Integer top )
    {
        this.top = top;
    }

    public Integer getTop()
    {
        return top;
    }

    public void setLeft( Integer left )
    {
        this.left = left;
    }

    public Integer getLeft()
    {
        return left;
    }

    public void setType( FieldTypeDTO type )
    {
        this.type = type;
    }

    public FieldTypeDTO getType()
    {
        if ( type == null )
            type = new FieldTypeDTO( FieldTypeDTO.typeString );
        return type;
    }

    public void setExport( Boolean export )
    {
        this.export = export;
    }

    public Boolean getExport()
    {
        return export;
    }

    public void setSearchable( Boolean searchable )
    {
        this.searchable = searchable;
    }

    public Boolean getSearchable()
    {
        return searchable;
    }

    public void setSequence( Integer sequence )
    {
        this.sequence = sequence;
    }

    public Integer getSequence()
    {
        return sequence;
    }

    public void setPk( Boolean pk )
    {
        this.pk = pk;
    }

    public Boolean getPk()
    {
        return pk;
    }
}
