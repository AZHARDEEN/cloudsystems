package br.com.mcampos.dto.inep.reporting;

public class NotasFinaisDTO extends BaseSubscriptionDTO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5886176822792153041L;
	Integer mediaTarefa1;
	Integer mediaTarefa2;
	Integer mediaTarefa3;
	Integer mediaTarefa4;

	public Integer getMediaTarefa1( )
	{
		if ( this.mediaTarefa1 == null ) {
			this.mediaTarefa1 = 0;
		}
		return this.mediaTarefa1;
	}

	public void setMediaTarefa1( Integer mediaTarefa1 )
	{
		this.mediaTarefa1 = mediaTarefa1;
	}

	public Integer getMediaTarefa2( )
	{
		if ( this.mediaTarefa2 == null ) {
			this.mediaTarefa2 = 0;
		}
		return this.mediaTarefa2;
	}

	public void setMediaTarefa2( Integer mediaTarefa2 )
	{
		this.mediaTarefa2 = mediaTarefa2;
	}

	public Integer getMediaTarefa3( )
	{
		if ( this.mediaTarefa3 == null ) {
			this.mediaTarefa3 = 0;
		}
		return this.mediaTarefa3;
	}

	public void setMediaTarefa3( Integer mediaTarefa3 )
	{
		this.mediaTarefa3 = mediaTarefa3;
	}

	public Integer getMediaTarefa4( )
	{
		if ( this.mediaTarefa4 == null ) {
			this.mediaTarefa4 = 0;
		}
		return this.mediaTarefa4;
	}

	public void setMediaTarefa4( Integer mediaTarefa4 )
	{
		this.mediaTarefa4 = mediaTarefa4;
	}

	public Integer getNotaFinal( )
	{
		float n;
		n = ( getMediaTarefa1( ).intValue( ) + getMediaTarefa2( ).intValue( ) +
				getMediaTarefa3( ).intValue( ) + getMediaTarefa4( ).intValue( ) );
		n /= 4;
		return Math.round( n );
	}

	@Override
	public String[ ] getFields( )
	{
		String[ ] fields = new String[ 6 ];
		fields[ 0 ] = getSubscription( );
		fields[ 1 ] = getMediaTarefa1( ).toString( );
		fields[ 2 ] = getMediaTarefa2( ).toString( );
		fields[ 3 ] = getMediaTarefa3( ).toString( );
		fields[ 4 ] = getMediaTarefa4( ).toString( );
		fields[ 5 ] = getNotaFinal( ).toString( );
		return fields;
	}

	@Override
	public String getHeader( )
	{
		return "Inscrição;MediaTarefa1;MediaTarefa2;MediaTarefa3;MediaTarefa4;NotaFinal";
	}

}
