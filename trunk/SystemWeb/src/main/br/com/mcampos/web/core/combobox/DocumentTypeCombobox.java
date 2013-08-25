package br.com.mcampos.web.core.combobox;

import java.util.List;

import br.com.mcampos.ejb.user.document.type.DocumentType;
import br.com.mcampos.ejb.user.document.type.DocumentTypeSession;

public class DocumentTypeCombobox extends ComboboxExt<DocumentTypeSession, DocumentType>
{
	private static final long serialVersionUID = 2731416939477095689L;

	@Override
	protected Class<DocumentTypeSession> getSessionClass( )
	{
		return DocumentTypeSession.class;
	}

	@Override
	protected void load( )
	{
		load( (List<DocumentType>) getSession( ).getAll( ), null, true );
	}

}
