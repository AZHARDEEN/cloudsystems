package br.com.mcampos.jpa;


public abstract class SimpleCodedTable<ENTITY> extends SimpleTable<ENTITY> implements SimpleEntity<ENTITY>
{
	private static final long serialVersionUID = -8559817582455274856L;

	public abstract void setCode( String code );

	public abstract String getCode( );

	public SimpleCodedTable( )
	{
		super( );
	}

	public SimpleCodedTable( Integer id, String description, String code )
	{
		super( id, description );
		setCode( code );
	}

	@Override
	public int compareTo( ENTITY object, Integer field )
	{
		switch ( field ) {
		case 2:
			return getCode( ).compareTo( ( (SimpleCodedTable<?>) object ).getCode( ) );
		default:
			return super.compareTo( object, field );
		}
	}

	@Override
	public String getField( Integer field )
	{
		switch ( field ) {
		case 2:
			return getCode( );
		default:
			return super.getField( field );
		}
	}

	@Override
	public String toString( )
	{
		return getCode( ) + " - " + getCode( );
	}

}
