package br.com.mcampos.web.controller.tables;

import java.util.Collection;
import java.util.List;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import br.com.mcampos.ejb.user.document.type.DocumentType;
import br.com.mcampos.ejb.user.document.type.DocumentTypeSession;
import br.com.mcampos.web.core.SimpleTableController;

public class DocumentTypeController extends SimpleTableController<DocumentTypeSession, DocumentType>
{
	private static final long serialVersionUID = 3799944939662563061L;

	@Wire
	private Label infoMask;

	@Wire
	private Label infoUniq;

	@Wire
	private Textbox mask;

	@Wire
	private Checkbox uniq;

	@Override
	protected Class<DocumentTypeSession> getSessionClass( )
	{
		return DocumentTypeSession.class;
	}

	@Override
	protected void showFields( Collection<DocumentType> entities )
	{
		List<DocumentType> list = ( List<DocumentType> ) entities;
		super.showFields( entities );
		this.infoMask.setValue( list.get( 0 ).getMask( ) );
		this.infoUniq.setValue( list.get( 0 ).getUniq( ) ? "Sim" : "Não" );
		this.mask.setValue( list.get( 0 ).getMask( ) );
		this.uniq.setChecked( list.get( 0 ).getUniq( ));
	}

	@Override
	protected void updateTargetEntity( DocumentType target )
	{
		super.updateTargetEntity( target );
		target.setMask( this.mask.getValue( ) );
		target.setUniq( this.uniq.isChecked( ) );
	}
}
