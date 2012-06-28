package br.com.mcampos.web.core.dbwidgets;

import org.zkoss.composite.Composite;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Intbox;


@Composite
public class DBIntbox extends Intbox implements DBWidget
{
	private static final long serialVersionUID = -1436555019140679706L;

	public DBIntbox( int i ) throws WrongValueException
	{
		super( i );
	}

	public DBIntbox()
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
