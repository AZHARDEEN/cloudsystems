package br.com.mcampos.web.fdigital.controller;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;

import br.com.mcampos.ejb.fdigital.form.AnotoForm;
import br.com.mcampos.ejb.fdigital.form.AnotoFormSession;
import br.com.mcampos.web.core.SimpleTableController;

public class AnotoFormController extends SimpleTableController<AnotoFormSession, AnotoForm>
{
	private static final long serialVersionUID = 4679060005827778998L;

	@Wire
	private Label recordIP;

	@Wire
	private Label recordIcrImage;

	@Wire
	private Label recordConcatPgc;

	@Wire
	private Label recordImagePath;

	@Override
	protected Class<AnotoFormSession> getSessionClass( )
	{
		return AnotoFormSession.class;
	}

	@Override
	protected void showFields( AnotoForm entity )
	{
		this.recordIP.setValue( entity.getFrmIpCh( ) );
		this.recordImagePath.setValue( entity.getFrmImageFilepathCh( ) );
		this.recordIcrImage.setValue( entity.getFrmIcrImageBt( ).toString( ) );
		this.recordConcatPgc.setValue( entity.getFrmConcatPgcBt( ).toString( ) );
		super.showFields( entity );
	}

}
