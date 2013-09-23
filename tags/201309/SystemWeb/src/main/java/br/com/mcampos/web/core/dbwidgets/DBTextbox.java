package br.com.mcampos.web.core.dbwidgets;

import org.zkoss.composite.Composite;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Textbox;

@Composite ( name = "DBTextbox" )
public class DBTextbox extends Textbox implements DBWidget
{
	private static final long serialVersionUID = -4504710666255311568L;

	public DBTextbox( String string ) throws WrongValueException
	{
		super( string );
	}

	public DBTextbox()
	{
		super();
	}

	@Override
	public void setPrimaryKey( Boolean bSet )
	{
		setAttribute( DBWidget.primeryKeyAttributeName, bSet );
	}

	@Override
	public Boolean isPrimaryKey()
	{
		Boolean id = ( Boolean ) getAttribute( DBWidget.primeryKeyAttributeName );
		if ( id == null ) {
			id = false;
		}
		return id;
	}
}
