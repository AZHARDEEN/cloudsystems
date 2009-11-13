package br.com.mcampos.dto.user;

import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;


import java.io.Serializable;

public class UserDocumentDTO implements Serializable, Comparable<UserDocumentDTO>
{

	public static final int typeCPF = 1;
    public static final int typeCNPJ = 10;
	public static final int typeIdentity = 2;
	public static final int typeEmail = 6;
	
	protected static final String forbidenCPFChars = "[\\-.\\/]";


    private Integer userId;
    private DocumentTypeDTO documentType;
    
    private String code;
    private String additionalInfo;
    
    
    
    public UserDocumentDTO()
    {
        super();
    }
	
    public void setCode( String code )
    {
        this.code = code;
    }

    public String getCode()
    {
        return removeDocumentFormat( this.code );
    }

    public void setAdditionalInfo( String additionalInfo )
    {
        this.additionalInfo = additionalInfo;
    }

    public String getAdditionalInfo()
    {
        return additionalInfo;
    }

    public void setUserId( Integer user )
    {
        this.userId = user;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setDocumentType( DocumentTypeDTO documentType )
    {
        this.documentType = documentType;
    }

    public DocumentTypeDTO getDocumentType()
    {
        return documentType;
    }

	public static UserDocumentDTO createUserDocumentCPF ( String cpf )
	{
		UserDocumentDTO dto = new UserDocumentDTO ( );
        
        dto.setDocumentType( DocumentTypeDTO.createDocumentTypeCPF() );
	    dto.setCode(cpf );            
        return dto;
	}
	
	protected String removeDocumentFormat ( String document )
	{
		String code;
		
		if ( document == null || document.length() == 0 )
			return null;
	    code = document.trim();
		if ( getDocumentType() != null ) {
			switch ( getDocumentType().getId() ) {
				case UserDocumentDTO.typeCPF:
                case UserDocumentDTO.typeCNPJ:
					code = code.replaceAll( forbidenCPFChars, "" );
					break;
				case UserDocumentDTO.typeEmail:
					code = code.toLowerCase();
					break;
			}
		}
		return code;
	}

    public int compareTo( UserDocumentDTO o )
    {
        int nRet = getDocumentType().compareTo( o.getDocumentType() );
        if ( nRet != 0 )
            return nRet;
        return getUserId().compareTo( o.getUserId() );
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof UserDocumentDTO ) 
        {
            UserDocumentDTO dto = ( UserDocumentDTO ) obj;
            
            if ( getUserId() != null ) {
                return getDocumentType().equals( dto.getDocumentType() )
                    && getUserId().equals( dto.getUserId() );
            }
            else
                return getDocumentType().equals( dto.getDocumentType() );
        }
        return super.equals( obj );
    }
}
