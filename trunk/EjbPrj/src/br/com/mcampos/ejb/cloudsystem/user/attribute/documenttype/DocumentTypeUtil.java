package br.com.mcampos.ejb.cloudsystem.user.attribute.documenttype;


import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;
import br.com.mcampos.ejb.cloudsystem.user.attribute.documenttype.entity.DocumentType;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DocumentTypeUtil
{
	public DocumentTypeUtil()
	{
		super();
	}

	public static DocumentType createEntity( DocumentTypeDTO dto )
	{
		if ( dto == null )
			return null;
		DocumentType entity = new DocumentType( dto.getId() );
		return update( entity, dto );
	}

	public static DocumentTypeDTO copy( DocumentType entity )
	{
		if ( entity == null )
			return null;

		return new DocumentTypeDTO( entity.getId(), entity.getDescription(), entity.getMask() );
	}

	public static DocumentType update( DocumentType entity, DocumentTypeDTO dto )
	{
		if ( dto == null || entity == null )
			return null;

		entity.setDescription( dto.getDescription() );
		entity.setMask( dto.getMask() );
		return entity;
	}

	public static List<DocumentTypeDTO> toDTOList( List<DocumentType> list )
	{
		if ( SysUtils.isEmpty( list ) )
			return Collections.emptyList();
		ArrayList<DocumentTypeDTO> listDTO = new ArrayList<DocumentTypeDTO>( list.size() );
		for ( DocumentType m : list ) {
			listDTO.add( copy( m ) );
		}
		return listDTO;
	}

}
