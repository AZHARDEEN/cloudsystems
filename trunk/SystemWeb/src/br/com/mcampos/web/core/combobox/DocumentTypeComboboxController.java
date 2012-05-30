package br.com.mcampos.web.core.combobox;

import br.com.mcampos.ejb.user.document.type.DocumentTypeSession;

public class DocumentTypeComboboxController extends BaseDBCombobox<DocumentTypeSession>
{
	private static final long serialVersionUID = 3154564484768914086L;

	@Override
	protected Class<DocumentTypeSession> getSessionClass( )
	{
		return DocumentTypeSession.class;
	}

}
