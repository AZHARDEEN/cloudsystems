package br.com.mcampos.web.controller.client.commom;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.user.document.UserDocument;
import br.com.mcampos.web.core.combobox.DocumentTypeCombobox;

public class DocumentDialogController extends BaseUserItemDlgController<UserDocument>
{
	private static final long serialVersionUID = 7476205094666965592L;

	@Wire
	private DocumentTypeCombobox documentType;

	@Wire
	private Textbox documentId;

	@Wire
	private Textbox documentComent;

	protected DocumentTypeCombobox getDocumentType( )
	{
		return documentType;
	}

	protected Textbox getDocumentId( )
	{
		return documentId;
	}

	protected Textbox getDocumentComent( )
	{
		return documentComent;
	}

	@Override
	protected void showRecord( UserDocument doc )
	{
		if( doc != null ) {
			if( getDocumentType( ) != null ) {
				getDocumentType( ).find( doc.getType( ) );
			}
			getDocumentId( ).setValue( doc.getCode( ) );
			getDocumentComent( ).setValue( doc.getAdditionalInfo( ) );
		}
		else {
			if( getDocumentType( ) != null ) {
				getDocumentType( ).setSelectedIndex( 0 );
			}
			getDocumentId( ).setRawValue( "" );
			getDocumentComent( ).setRawValue( "" );
		}
	}

	@Override
	protected void update( UserDocument c )
	{
		c.setType( getDocumentType( ).getSelectedValue( ) );
		c.setCode( getDocumentId( ).getValue( ) );
		c.setAdditionalInfo( getDocumentComent( ).getValue( ) );
	}

	@Override
	protected boolean validate( UserDocument data )
	{
		return true;
	}

	@Override
	protected UserDocument createEntity( )
	{
		return new UserDocument( );
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		showRecord( getEntity( ) );
	}

}
