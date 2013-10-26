package br.com.mcampos.web.core.file;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseController;

public class FilePathDialog extends BaseController<Window>
{
	private static final long serialVersionUID = -2643599435996770286L;

	private String initialPath;

	@Wire
	private Label lblPath;

	@Listen( "onClick = #cmdCancel" )
	public void onClickCancel( )
	{
		Component target = getMainWindow( );

		if ( target != null ) {
			target.detach( );
		}
	}

	@Listen( "onClick = #cmdSubmit; onOK = textbox " )
	public void onClickOk( )
	{
		if ( validate( ) == true ) {
			try {
				onOk( );
				onClickCancel( );
			} catch( Exception e ) {
				showErrorMessage( "Não foi possível completar a ação", "Confirmar Ação" );
			}
		}
	}

	public void onOk( )
	{

	}

	protected boolean validate( )
	{
		return true;
	}

	/**
	 * @return the initialPath
	 */
	public String getInitialPath( )
	{
		if ( SysUtils.isEmpty( initialPath ) )
			initialPath = Executions.getCurrent( ).getDesktop( ).getWebApp( ).getRealPath( "/" );
		return initialPath;
	}

	/**
	 * @param initialPath
	 *            the initialPath to set
	 */
	public void setInitialPath( String initialPath )
	{
		this.initialPath = initialPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.mcampos.web.core.BaseController#doAfterCompose(org.zkoss.zk.ui
	 * .Component)
	 */
	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		if ( getMainWindow( ) != null )
			getMainWindow( ).setTitle( "Diretórios - " + getInitialPath( ) );
	}

}
