package br.com.mcampos.web.inep.controller;

import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

import br.com.mcampos.jpa.inep.InepDistribution;

public class DlgComment extends Window
{
	private static final long serialVersionUID = -7655874360902980047L;


	private InepDistribution distribution;

	private Button cmdSaveComment;

	public InepDistribution getDistribution( )
	{
		return this.distribution;
	}

	public void setDistribution( InepDistribution d )
	{
		this.distribution = d;
	}

	@Override
	public void onClose( )
	{
		super.onClose( );
		detach( );
	}

	public Button getCmdSaveComment( )
	{
		if ( this.cmdSaveComment == null ) {
			this.cmdSaveComment = (Button) getFellow( "cmdSaveComment" );
		}
		return this.cmdSaveComment;
	}
}
