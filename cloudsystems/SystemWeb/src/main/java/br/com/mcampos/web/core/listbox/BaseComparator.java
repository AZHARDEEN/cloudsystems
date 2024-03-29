package br.com.mcampos.web.core.listbox;

import java.io.Serializable;
import java.util.Comparator;

import br.com.mcampos.jpa.BasicEntityRenderer;

public class BaseComparator implements Comparator<BasicEntityRenderer<?>>, Serializable
{
	private static final long serialVersionUID = 1L;
	private boolean asc = true;
	private int fieldIndex = 0;

	public BaseComparator( int fieldIndex, boolean bDirection )
	{
		super( );
		this.fieldIndex = fieldIndex;
		this.asc = bDirection;
	}

	@SuppressWarnings( { "rawtypes", "unchecked" } )
	@Override
	public int compare( BasicEntityRenderer<?> o1, BasicEntityRenderer<?> o2 )
	{
		BasicEntityRenderer b1 = o1;
		int nRet = b1.compareTo( o2, getFieldIndex( ) );
		if ( isAsc( ) == false ) {
			nRet *= -1;
		}
		return nRet;
	}

	protected void setAsc( boolean asc )
	{
		this.asc = asc;
	}

	protected boolean isAsc( )
	{
		return this.asc;
	}

	protected void setFieldIndex( int fieldIndex )
	{
		this.fieldIndex = fieldIndex;
	}

	protected int getFieldIndex( )
	{
		return this.fieldIndex;
	}
}
