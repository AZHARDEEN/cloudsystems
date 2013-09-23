package br.com.mcampos.ejb.cloudsystem.anoto.pen;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.ejb.cloudsystem.EntityCopyInterface;
import br.com.mcampos.sysutils.SysUtils;

@Entity
@NamedQueries( { @NamedQuery( name = "Pen.findAll", query = "select o from AnotoPen o" ),
		@NamedQuery( name = AnotoPen.penCount, query = "select count(o) from AnotoPen o" ),
		@NamedQuery( name = AnotoPen.formAvailablePens, query = "select o from AnotoPen o where o.id not in ( select t.pen.id from AnotoPenPage t "
				+ " where t.formId = ?1 and ( t.toDate is null or t.toDate <= CURRENT_TIMESTAMP ) ) " )
} )
@Table( name = "anoto_pen" )
public class AnotoPen implements Serializable, EntityCopyInterface<PenDTO>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7065480147688860835L;

	public static final String formAvailablePens = "AnotoPen.formAvailablePens";

	public static final String penCount = "count";

	@Id
	@Column( name = "pen_id_ch", nullable = false )
	private String id;

	@Column( name = "pen_insert_dt", nullable = false )
	@Temporal( TemporalType.DATE )
	private Date insertDate;

	@Column( name = "pen_description_ch", nullable = false )
	private String description;

	@Column( name = "pen_serial_ch", nullable = false )
	private String serial;

	@Column( name = "pen_pin_ch", nullable = false )
	private Integer pin;

	public AnotoPen( )
	{
	}

	public AnotoPen( String pen_id_in )
	{
		setId( pen_id_in );
	}

	public String getId( )
	{
		return id;
	}

	public void setId( String pen_id_in )
	{
		if ( SysUtils.isEmpty( pen_id_in ) )
			throw new InvalidParameterException( "Pen identification could not be null or empty." );
		id = pen_id_in.trim( );
	}

	@Override
	public PenDTO toDTO( )
	{
		return AnotoPenUtil.copy( this );
	}

	public void setInsertDate( Date insertDate )
	{
		this.insertDate = insertDate;
	}

	public Date getInsertDate( )
	{
		return insertDate;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

	public String getDescription( )
	{
		return description;
	}

	public void setSerial( String serial )
	{
		this.serial = serial;
	}

	public String getSerial( )
	{
		return serial;
	}

	public void setPin( Integer pin )
	{
		this.pin = pin;
	}

	public Integer getPin( )
	{
		return pin;
	}
}
