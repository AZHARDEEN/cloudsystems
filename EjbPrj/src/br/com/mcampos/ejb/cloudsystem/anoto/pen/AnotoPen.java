package br.com.mcampos.ejb.cloudsystem.anoto.pen;


import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;
import br.com.mcampos.sysutils.SysUtils;

import java.io.Serializable;

import java.security.InvalidParameterException;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@NamedQueries( { @NamedQuery( name = "Pen.findAll", query = "select o from AnotoPen o" ),
                 @NamedQuery( name = AnotoPen.penCount, query = "select count(o) from AnotoPen o" ) } )
@NamedNativeQueries( { @NamedNativeQuery( name = AnotoPen.formAvailablePens,
                                          query = "SELECT * FROM anoto_pen WHERE PEN_ID_CH NOT IN ( SELECT PEN_ID_CH FROM ANOTO_PEN_PAGE WHERE FRM_ID_IN = ?1 and PDP_TO_DT IS NULL )",
                                          resultClass = AnotoPen.class ) } )

@Table( name = "anoto_pen" )
public class AnotoPen implements Serializable, EntityCopyInterface<PenDTO>
{
    public static final String formAvailablePens = "formAvailablePens";

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


    public AnotoPen()
    {
    }

    public AnotoPen( String pen_id_in )
    {
        setId( pen_id_in );
    }

    public String getId()
    {
        return id;
    }

    public void setId( String pen_id_in )
    {
        if ( SysUtils.isEmpty( pen_id_in ) )
            throw new InvalidParameterException( "Pen identification could not be null or empty." );
        this.id = pen_id_in.trim();
    }

    public PenDTO toDTO()
    {
        return AnotoPenUtil.copy( this );
    }

    public void setInsertDate( Date insertDate )
    {
        this.insertDate = insertDate;
    }

    public Date getInsertDate()
    {
        return insertDate;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setSerial( String serial )
    {
        this.serial = serial;
    }

    public String getSerial()
    {
        return serial;
    }

    public void setPin( Integer pin )
    {
        this.pin = pin;
    }

    public Integer getPin()
    {
        return pin;
    }
}
