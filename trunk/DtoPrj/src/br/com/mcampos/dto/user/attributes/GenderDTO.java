package br.com.mcampos.dto.user.attributes;

import br.com.mcampos.dto.core.DisplayNameDTO;

import java.util.ArrayList;

public class GenderDTO extends DisplayNameDTO implements Comparable< GenderDTO >
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3160924071819518136L;
	protected Integer id;
    protected String  description;
    protected ArrayList<TitleDTO> titles;
    
    public GenderDTO()
    {
        super();
    }
    
    public GenderDTO ( Integer id, String description, ArrayList<TitleDTO> titles )
    {
        super ();
        init ( id, description, titles );
    }

    public GenderDTO ( Integer id, String description )
    {
        super ();
        init ( id, description, null );
    }

    public GenderDTO ( GenderDTO dto )
    {
        super ();
        init ( dto.getId(), dto.getDescription(), dto.getTitles()  );
    }
    
    protected void init ( Integer id, String description, ArrayList<TitleDTO> titles )
    {
        this.id  = id;
        this.description = description;
        this.titles = titles;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
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

    public int compareTo( GenderDTO o )
    {
        return getId().compareTo( o.getId() );
    }
}




