package br.com.mcampos.ejb.cloudsystem;


public abstract class SimpleTable<ENTITY> implements SimpleEntity<ENTITY>
{
	private static final long serialVersionUID = -3732259968767551014L;

	@Override
	public abstract String getDescription( );

	@Override
	public abstract Integer getId();

	public SimpleTable()
	{
		super();
	}

	public SimpleTable( Integer id, String description )
	{
		setId( id );
		setDescription( description );
	}


	@Override
	public int compareTo( ENTITY object, Integer field )
	{
		switch ( field ) {
		case 0:
			return getId( ).compareTo( ( (SimpleTable<?>) object ).getId( ) );
		case 1:
			return getDescription( ).compareTo( ( (SimpleTable<?>) object ).getDescription( ) );
		default:
			return 0;
		}
	}

	@Override
	public boolean equals( Object obj )
	{
		return getId( ).equals( ( (SimpleTable<?>) obj ).getId( ) );
	}

	@Override
	public int compareTo( ENTITY o )
	{
		return getId( ).compareTo( ( (SimpleTable<?>) o ).getId( ) );
	}

	@Override
	public String getField( Integer field )
	{
		switch ( field ) {
		case 0:
			return getId().toString();
		case 1:
			return getDescription();
		default:
			return "";
		}
	}
}
