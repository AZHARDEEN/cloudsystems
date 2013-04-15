package br.com.mcampos.report.extension;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;
import br.com.mcampos.sysutils.SysUtils;

public class DocumentFormatter extends JRDefaultScriptlet
{
	public String getFormattedDocument( ) throws JRScriptletException
	{
		String value = (String) this.getFieldValue( "cpf" );
		if ( SysUtils.isEmpty( value ) )
			value = "";
		return value;
	}
}
