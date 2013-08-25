package br.com.mcampos.web.core.report;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Window;

import br.com.mcampos.web.core.BaseLoggedController;

public class JasperReportController extends BaseLoggedController<Window>
{
	private static final long serialVersionUID = 2753873781502371152L;
	public static final String paramName = "JasperReportSessionParameter";

	@Wire( "iframe" )
	private Iframe frame;

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		ReportItem item = (ReportItem) popSessionParameter( paramName );
		if ( item != null && item.getMedia( ) != null ) {
			getFrame( ).setContent( item.getMedia( ) );
		}

	}

	public Iframe getFrame( )
	{
		return this.frame;
	}

}
