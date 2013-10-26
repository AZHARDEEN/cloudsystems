package br.com.mcampos.web.inep.jreport;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

public class GradeScriptlet extends JRDefaultScriptlet
{
	public float T1NF1( ) throws JRScriptletException
	{
		Integer field;

		float nf = 0;
		field = (Integer) this.getFieldValue( "task1_grade3" );
		if ( field != null ) {
			nf = field.floatValue( );
			return nf;
		}
		field = (Integer) this.getFieldValue( "task1_grade1" );
		if ( field != null ) {
			nf = field.floatValue( );
		}
		field = (Integer) this.getFieldValue( "task1_grade2" );
		if ( field != null ) {
			nf += field.floatValue( );
		}
		nf /= 2f;
		return nf;
	}
}
