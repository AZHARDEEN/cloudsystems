package br.com.mcampos.web.controller.tables.title;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;

import br.com.mcampos.ejb.user.person.title.TitleSession;
import br.com.mcampos.jpa.user.Title;
import br.com.mcampos.web.core.SimpleTableController;
import br.com.mcampos.web.core.dbwidgets.DBWidget;

public class TitleController extends SimpleTableController<TitleSession, Title>
{
	private static final long serialVersionUID = 4442065382919286435L;

	@Wire( "#infoAbbrev" )
	private Label labelAbbrev;

	@Wire( "#abbreviation" )
	private DBWidget abbrev;

	@Override
	protected Class<TitleSession> getSessionClass( )
	{
		return TitleSession.class;
	}

	@Override
	protected void showFields( Title title )
	{
		super.showFields( title );
		this.labelAbbrev.setValue( title.getAbreviation( ) );
		this.abbrev.setText( title.getAbreviation( ) );
	}

	@Override
	protected void updateTargetEntity( Title target )
	{
		super.updateTargetEntity( target );
		target.setAbreviation( this.abbrev.getText( ) );
	}
}
