package br.com.mcampos.ejb.media;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the media database table.
 * 
 */
@Entity
@Table( name = "media" )
@NamedQueries( {
		@NamedQuery( name = Media.getByName, query = "from Media o where o.name = ?1" ),
} )
public class Media implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static final String getByName = "Media.getMediaByName";

	@Id
	@SequenceGenerator( name = "mediaIdGenerator", sequenceName = "seq_media", allocationSize = 1 )
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "mediaIdGenerator" )
	@Column( name = "med_id_in", unique = true, nullable = false )
	private Integer id;

	@Column( name = "med_format_ch", length = 32 )
	private String format;

	@Column( name = "med_inset_dt" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date medInsetDt;

	@Column( name = "med_mime_ch", length = 128 )
	private String mimeType;

	@Column( name = "med_name_ch", nullable = false, length = 128 )
	private String name;

	@Basic( fetch = FetchType.LAZY )
	@Column( name = "med_object_bin", columnDefinition = "bytea" )
	private byte[ ] object;

	@Column( name = "med_size_in", nullable = false )
	private Integer size;

	public Media( )
	{
	}

	public Media( Integer id )
	{
		setId( id );
	}

	public Integer getId( )
	{
		return this.id;
	}

	public void setId( Integer medIdIn )
	{
		this.id = medIdIn;
	}

	public String getFormat( )
	{
		return this.format;
	}

	public void setFormat( String medFormatCh )
	{
		this.format = medFormatCh;
	}

	public Date getMedInsetDt( )
	{
		return this.medInsetDt;
	}

	public void setMedInsetDt( Date medInsetDt )
	{
		this.medInsetDt = medInsetDt;
	}

	public String getMimeType( )
	{
		return this.mimeType;
	}

	public void setMimeType( String medMimeCh )
	{
		this.mimeType = medMimeCh;
	}

	public String getName( )
	{
		return this.name;
	}

	public void setName( String medNameCh )
	{
		this.name = medNameCh;
	}

	public byte[ ] getObject( )
	{
		return this.object;
	}

	public void setObject( byte[ ] medObjectBin )
	{
		this.object = medObjectBin;
		setSize( getObject( ) != null ? getObject( ).length : 0 );
	}

	public Integer getSize( )
	{
		return this.size;
	}

	public void setSize( Integer medSizeIn )
	{
		this.size = medSizeIn;
	}

}