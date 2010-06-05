package br.com.mcampos.dto.user.attributes;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class DocumentTypeDTO extends SimpleTableDTO
{
	/**
	 *
	 */
	private static final long serialVersionUID = 8436760228037673894L;
	private String mask;


	public static final int typeCPF = 1;
	public static final int typeCNPJ = 10;
	public static final int typeIdentity = 2;
	public static final int typeEmail = 6;

	public DocumentTypeDTO()
	{
		super();
	}

	public DocumentTypeDTO( Integer id )
	{
		super( id );
	}

	public DocumentTypeDTO( Integer id, String name, String mask )
	{
		super( id, name );
		setMask( mask );
	}

	public void setMask( String mask )
	{
		this.mask = mask;
	}

	public String getMask()
	{
		return mask;
	}

	public static DocumentTypeDTO createDocumentTypeCPF()
	{
		return new DocumentTypeDTO( typeCPF );
	}

	public static DocumentTypeDTO createDocumentTypeIdentity()
	{
		return new DocumentTypeDTO( typeIdentity );
	}

	public static DocumentTypeDTO createDocumentTypeEmail()
	{
		return new DocumentTypeDTO( typeEmail );
	}

	public static DocumentTypeDTO createDocumentTypeCNPJ()
	{
		return new DocumentTypeDTO( typeCNPJ );
	}
}
