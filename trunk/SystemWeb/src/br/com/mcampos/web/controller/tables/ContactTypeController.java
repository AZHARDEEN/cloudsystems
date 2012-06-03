package br.com.mcampos.web.controller.tables;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import br.com.mcampos.ejb.user.contact.ContactTypeSession;
import br.com.mcampos.ejb.user.contact.type.ContactType;
import br.com.mcampos.web.core.SimpleTableController;

public class ContactTypeController extends SimpleTableController<ContactTypeSession, ContactType>
{
	private static final long serialVersionUID = -7673327609882590832L;

	@Wire
	private Label infoMask;

	@Wire
	private Label infoUniq;

	@Wire
	private Textbox mask;

	@Wire
	private Checkbox uniq;

	@Override
	protected Class<ContactTypeSession> getSessionClass( )
	{
		return ContactTypeSession.class;
	}

	@Override
	protected void showFields( ContactType entity )
	{
		super.showFields( entity );
		this.infoMask.setValue( entity.getMask( ) );
		this.infoUniq.setValue( entity.getDuplicate( ) ? "Sim" : "Não" );
		this.mask.setValue( entity.getMask( ) );
		this.uniq.setChecked( entity.getDuplicate( ) );
	}

	@Override
	protected void updateTargetEntity( ContactType target )
	{
		super.updateTargetEntity( target );
		target.setMask( this.mask.getValue( ) );
		target.setDuplicate( this.uniq.isChecked( ) );
	}

}
