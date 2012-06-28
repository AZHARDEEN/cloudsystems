package br.com.mcampos.dto.inep.reporting;

public class NotasFinaisDTO extends BaseSubscriptionDTO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5886176822792153041L;
	Double mediaTarefa1;
	Double mediaTarefa2;
	Double mediaTarefa3;
	Double mediaTarefa4;

	public Double getMediaTarefa1( )
	{
		if ( this.mediaTarefa1 == null ) {
			this.mediaTarefa1 = (double) 0;
		}
		return this.mediaTarefa1;
	}

	public void setMediaTarefa1( Double mediaTarefa1 )
	{
		this.mediaTarefa1 = mediaTarefa1;
	}

	public Double getMediaTarefa2( )
	{
		if ( this.mediaTarefa2 == null ) {
			this.mediaTarefa2 = (double) 0;
		}
		return this.mediaTarefa2;
	}

	public void setMediaTarefa2( Double mediaTarefa2 )
	{
		this.mediaTarefa2 = mediaTarefa2;
	}

	public Double getMediaTarefa3( )
	{
		if ( this.mediaTarefa3 == null ) {
			this.mediaTarefa3 = (double) 0;
		}
		return this.mediaTarefa3;
	}

	public void setMediaTarefa3( Double mediaTarefa3 )
	{
		this.mediaTarefa3 = mediaTarefa3;
	}

	public Double getMediaTarefa4( )
	{
		if ( this.mediaTarefa4 == null ) {
			this.mediaTarefa4 = (double) 0;
		}
		return this.mediaTarefa4;
	}

	public void setMediaTarefa4( Double mediaTarefa4 )
	{
		this.mediaTarefa4 = mediaTarefa4;
	}

	public Double getNotaFinal( )
	{
		double n = 0;
		n += getMediaTarefa1( ).doubleValue( ) > 5 ? 0 : getMediaTarefa1( ).doubleValue( );
		n += getMediaTarefa2( ).doubleValue( ) > 5 ? 0 : getMediaTarefa2( ).doubleValue( );
		n += getMediaTarefa3( ).doubleValue( ) > 5 ? 0 : getMediaTarefa3( ).doubleValue( );
		n += getMediaTarefa4( ).doubleValue( ) > 5 ? 0 : getMediaTarefa4( ).doubleValue( );
		n /= 4;
		return n;
	}

	@Override
	public String[ ] getFields( )
	{
		String[ ] fields = new String[ 6 ];
		fields[ 0 ] = getSubscription( );
		fields[ 1 ] = convert( getMediaTarefa1( ) );
		fields[ 2 ] = convert( getMediaTarefa2( ) );
		fields[ 3 ] = convert( getMediaTarefa3( ) );
		fields[ 4 ] = convert( getMediaTarefa4( ) );
		fields[ 5 ] = convert( getNotaFinal( ) );
		return fields;
	}

	private String convert( Double v )
	{
		if ( v == null ) {
			return "";
		}
		if ( v <= 5.0 ) {
			return v.toString( );
		}
		else if ( v == 6.0 ) {
			return "A";
		}
		else if ( v == 7.0 ) {
			return "B";
		}
		else {
			return "";
		}
	}

	@Override
	public String getHeader( )
	{
		return "Inscrição;MediaTarefa1;MediaTarefa2;MediaTarefa3;MediaTarefa4;NotaFinal";
	}

}
