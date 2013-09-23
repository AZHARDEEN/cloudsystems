package br.com.mcampos.jpa.fdigital;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mcampos.jpa.BasicEntityRenderer;

/**
 * The persistent class for the anoto_pen database table.
 * 
 */
@Entity
@Table( name = "anoto_pen", schema = "public" )
public class AnotoPen implements BasicEntityRenderer<AnotoPen>, Comparable<AnotoPen>
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "pen_id_ch", unique = true, nullable = false, length = 16 )
	private String id;

	@Column( name = "pen_description_ch", nullable = false, length = 64 )
	private String description;

	@Temporal( TemporalType.DATE )
	@Column( name = "pen_insert_dt" )
	private Date insertDate;

	@Column( name = "pen_pin_ch" )
	private Integer pin;

	@Column( name = "pen_serial_ch", length = 32 )
	private String serial;

	public AnotoPen( )
	{
	}

	public String getId( )
	{
		return id;
	}

	public void setId( String penIdCh )
	{
		id = penIdCh;
	}

	public String getDescription( )
	{
		return description;
	}

	public void setDescription( String penDescriptionCh )
	{
		description = penDescriptionCh;
	}

	public Date getInsertDate( )
	{
		return insertDate;
	}

	public void setInsertDate( Date penInsertDt )
	{
		insertDate = penInsertDt;
	}

	public Integer getPin( )
	{
		return pin;
	}

	public void setPin( Integer penPinCh )
	{
		pin = penPinCh;
	}

	public String getSerial( )
	{
		return serial;
	}

	public void setSerial( String penSerialCh )
	{
		serial = penSerialCh;
	}

	@Override
	public String getField( Integer field )
	{
		switch ( field )
		{
		case 0:
			return getId( );
		case 1:
			return getDescription( );
		}
		return null;
	}

	@Override
	public int compareTo( AnotoPen object, Integer field )
	{
		switch ( field ) {
		case 0:
			return getId( ).compareTo( object.getId( ) );
		case 1:
			return getDescription( ).compareTo( object.getDescription( ) );
		}
		return 0;
	}

	@Override
	public int compareTo( AnotoPen o )
	{
		return getId( ).compareTo( o.getId( ) );
	}
}