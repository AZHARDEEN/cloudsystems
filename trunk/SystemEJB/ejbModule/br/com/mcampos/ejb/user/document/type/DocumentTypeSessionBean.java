package br.com.mcampos.ejb.user.document.type;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.entity.user.DocumentType;

/**
 * Session Bean implementation class DocumentTypeSessionBean
 */
@Stateless( mappedName = "DocumentTypeSession", name = "DocumentTypeSession" )
@LocalBean
public class DocumentTypeSessionBean extends SimpleSessionBean<DocumentType> implements DocumentTypeSession,
DocumentTypeSessionLocal
{

	@Override
	protected Class<DocumentType> getEntityClass( )
	{
		return DocumentType.class;
	}

}
