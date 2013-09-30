package br.com.mcampos.dto.inep;

import java.io.Serializable;

public class InepSubscriptionImportDTO implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3791123618753106996L;
	private String stationId;
	private String stationName;
	private String subscription;
	private String name;
	private String country;
	private String document;
	private String documentType;
	private String specialNeeds;

	public InepSubscriptionImportDTO( )
	{

	}

	public String getStationId( )
	{
		return this.stationId;
	}

	public void setStationId( String stationId )
	{
		this.stationId = stationId;
	}

	public String getStationName( )
	{
		return this.stationName;
	}

	public void setStationName( String stationName )
	{
		this.stationName = stationName;
	}

	public String getSubscription( )
	{
		return this.subscription;
	}

	public void setSubscription( String subscription )
	{
		this.subscription = subscription;
	}

	public String getName( )
	{
		return this.name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getCountry( )
	{
		return this.country;
	}

	public void setCountry( String country )
	{
		this.country = country;
	}

	public String getDocument( )
	{
		return this.document;
	}

	public void setDocument( String document )
	{
		this.document = document;
	}

	public String getDocumentType( )
	{
		return this.documentType;
	}

	public void setDocumentType( String documentType )
	{
		this.documentType = documentType;
	}

	public String getSpecialNeeds( )
	{
		return this.specialNeeds;
	}

	public void setSpecialNeeds( String specialNeeds )
	{
		this.specialNeeds = specialNeeds;
	}

	public void set( int columnIndex, String value )
	{
		if ( columnIndex < 0 || columnIndex > 7 ) {
			return;
		}
		switch ( columnIndex ) {
		case 0:
			this.setStationId( value );
			break;
		case 1:
			this.setStationName( value );
			break;
		case 2:
			this.setSubscription( value );
			break;
		case 3:
			this.setName( value );
			break;
		case 4:
			this.setCountry( value );
			break;
		case 5:
			this.setDocument( value );
			break;
		case 6:
			this.setDocumentType( value );
			break;
		case 7:
			this.setSpecialNeeds( value );
			break;
		}
	}

	public void set( int columnIndex, double value )
	{
		if ( columnIndex < 0 || columnIndex > 7 ) {
			return;
		}
		switch ( columnIndex ) {
		case 0:
			this.setStationId( "" + Double.valueOf( value ).intValue( ) );
			break;
		case 2:
			Double d = Double.valueOf( value );
			this.setSubscription( "" + d.longValue( ) );
			break;
		}
	}

}
