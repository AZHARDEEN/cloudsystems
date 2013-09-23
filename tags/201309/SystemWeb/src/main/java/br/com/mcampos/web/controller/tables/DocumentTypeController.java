package br.com.mcampos.web.controller.tables;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import br.com.mcampos.ejb.user.document.type.DocumentTypeSession;
import br.com.mcampos.jpa.user.DocumentType;
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
	protected void showFields( DocumentType entity )
	{
		super.showFields( entity );
		this.infoMask.setValue( entity.getMask( ) );
		this.infoUniq.setValue( entity.getUniq( ) ? "Sim" : "Não" );
		this.mask.setValue( entity.getMask( ) );
		this.uniq.setChecked( entity.getUniq( ) );
	}

	@Override
	protected void updateTargetEntity( DocumentType target )
	{
		super.updateTargetEntity( target );
		target.setMask( this.mask.getValue( ) );
		target.setUniq( this.uniq.isChecked( ) );
	}
}
