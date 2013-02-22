package br.com.mcampos.web.inep.controller;

import org.zkforge.fckez.FCKeditor;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.inep.entity.InepDistribution;

public class DlgComment extends Window
{
	private static final long serialVersionUID = -7655874360902980047L;

	private FCKeditor editor;

	private InepDistribution distribution;

	private Button cmdSaveComment;

	public InepDistribution getDistribution( )
	{
		return this.distribution;
	}

	public void setDistribution( InepDistribution d )
	{
		this.distribution = d;
		if ( d != null ) {
			getEditor( ).setValue( d.getObs( ) );
		}
	}

	public FCKeditor getEditor( )
	{
		if ( this.editor == null ) {
			this.editor = (FCKeditor) getFellow( "editCommend" );
		}
		return this.editor;
	}

	@Override
	public void onClose( )
	{
		if ( getDistribution( ) != null ) {
			getDistribution( ).setObs( this.editor.getValue( ) );
		}
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
