package br.com.mcampos.dto.user.attributes;

import br.com.mcampos.dto.core.DisplayNameDTO;

import java.io.Serializable;

public class DocumentTypeDTO extends DisplayNameDTO implements Comparable<DocumentTypeDTO>
{
    private Integer id;
    private String mask;
    private String name;


	public static final int typeCPF = 1;
    public static final int typeCNPJ = 10;
	public static final int typeIdentity = 2;
	public static final int typeEmail = 6;

    public DocumentTypeDTO()
    {
        super();
    }
	
	public DocumentTypeDTO( Integer id  )
	{
		setId ( id );
	}
    
    public DocumentTypeDTO( Integer id, String name, String mask )
    {
        super ();
        init ( id, name, mask );
    }
    
    protected void init ( Integer id, String name, String mask )
    {
        this.id = id;
        this.name = name;
        this.mask = mask;
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

    public void setName( String name )
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public String getDisplayName()
    {
        return getName();
    }
	
	public static DocumentTypeDTO createDocumentTypeCPF ( )
	{
		return new DocumentTypeDTO ( typeCPF );
	}

	public static DocumentTypeDTO createDocumentTypeIdentity ( )
	{
		return new DocumentTypeDTO ( typeIdentity );
	}

	public static DocumentTypeDTO createDocumentTypeEmail ( )
	{
		return new DocumentTypeDTO ( typeEmail );
	}

    public static DocumentTypeDTO createDocumentTypeCNPJ ( )
    {
        return new DocumentTypeDTO ( typeCNPJ );
    }


    public int compareTo( DocumentTypeDTO o )
    {
        if ( o == null || o.getId() == null )
            return 1;
        if ( getId() == null )
            return -1;
                           
        return getId().compareTo( o.getId() );
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj == null )
            return false;
        
        if ( obj instanceof DocumentTypeDTO  ) 
        {
            DocumentTypeDTO dto = ( DocumentTypeDTO ) obj;
            
            return ( getId().equals( dto.getId() ) );
        }
        else
            return super.equals( obj );
    }
}
