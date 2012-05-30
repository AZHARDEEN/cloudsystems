package br.com.mcampos.web.controller.tables;

import java.util.Collection;
import java.util.List;

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
	protected void showFields( Collection<ContactType> entities )
	{
		List<ContactType> list = ( List<ContactType> ) entities;
		super.showFields( entities );
		this.infoMask.setValue( list.get( 0 ).getMask( ) );
		this.infoUniq.setValue( list.get( 0 ).getDuplicate( ) ? "Sim" : "Não" );
		this.mask.setValue( list.get( 0 ).getMask( ) );
		this.uniq.setChecked( list.get( 0 ).getDuplicate( ) );
	}

	@Override
	protected void updateTargetEntity( ContactType target )
	{
		super.updateTargetEntity( target );
		target.setMask( this.mask.getValue( ) );
		target.setDuplicate( this.uniq.isChecked( ) );
	}

}
