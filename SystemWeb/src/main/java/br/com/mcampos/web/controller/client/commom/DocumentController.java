package br.com.mcampos.web.controller.client.commom;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import br.com.mcampos.ejb.user.document.UserDocument;
import br.com.mcampos.web.core.combobox.DocumentTypeCombobox;

public class DocumentController extends BaseUserAttrListController<UserDocument>
{
	private static final long serialVersionUID = -3999386922233720288L;

	@Wire( "#documentList" )
	private Listbox listbox;

	@Wire
	private DocumentTypeCombobox documentType;

	@Wire
	private Textbox documentId;

	@Wire
	private Textbox documentComent;

	@Override
	protected Listbox getListbox( )
	{
		return listbox;
	}

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
	protected UserDocument createNew( )
	{
		UserDocument c = new UserDocument( );
		update( c );
		return c;
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
		// TODO Auto-generated method stub
		return false;
	}
}
