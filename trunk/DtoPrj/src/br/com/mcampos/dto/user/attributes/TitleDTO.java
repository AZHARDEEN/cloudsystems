package br.com.mcampos.dto.user.attributes;

import br.com.mcampos.dto.core.DisplayNameDTO;

public class TitleDTO extends DisplayNameDTO implements Comparable< TitleDTO >
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6432837000318062671L;
	private String abbreviation;
    private String description;
    private Integer id;


    public TitleDTO()
    {
        super();
    }

    public TitleDTO(Integer id, String description, String abbreviation)
    {
        super();
        init ( id, description, abbreviation );
    }
    
    
    protected void init ( Integer id, String description, String abbreviation )
    {
        this.id = id;
        this.description = description;
        this.abbreviation = abbreviation;
    }
    

    public void setAbbreviation( String abbreviation )
    {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation()
    {
        return abbreviation;
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

    public String getDisplayName()
    {
        return getAbbreviation() + " - " + getDescription();
    }

    public int compareTo( TitleDTO o )
    {
        return getId().compareTo( o.getId() );
    }
}
