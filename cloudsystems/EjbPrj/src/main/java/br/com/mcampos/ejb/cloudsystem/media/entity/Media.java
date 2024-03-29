package br.com.mcampos.ejb.cloudsystem.media.entity;

import java.io.Serializable;
import java.security.InvalidParameterException;

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

import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.EntityCopyInterface;
import br.com.mcampos.sysutils.SysUtils;

@Entity
@NamedQueries( { @NamedQuery( name = "Media.findAll", query = "select o from Media o" ) } )
@Table( name = "media" )
public class Media implements Serializable, EntityCopyInterface<MediaDTO>, Comparable<Media>
{
	@SuppressWarnings( "compatibility:5269327754381544397" )
	private static final long serialVersionUID = -6128152754016719171L;
	@Id
	@Column( name = "med_id_in", nullable = false )
	@SequenceGenerator( name = "mediaIdGenerator", sequenceName = "seq_media", allocationSize = 1 )
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "mediaIdGenerator" )
	private Integer id;

	@Column( name = "med_mime_ch", length = 64 )
	private String mimeType;

	@Column( name = "med_name_ch", nullable = false, length = 128 )
	private String name;

	@Basic( fetch = FetchType.LAZY )
	@Column( name = "med_object_bin", nullable = false )
	private byte[ ] object;

	@Column( name = "med_size_in" )
	private Integer size;

	@Column( name = "med_format_ch" )
	private String format;

	public Media( )
	{
	}

	public Media( Integer id )
	{
		this.setId( id );
	}

	public Integer getId( )
	{
		return this.id;
	}

	public Media setId( Integer id )
	{
		this.id = id;
		return this;
	}

	public String getMimeType( )
	{
		return this.mimeType;
	}

	public Media setMimeType( String mime )
	{
		this.mimeType = mime;
		return this;
	}

	public String getName( )
	{
		return this.name;
	}

	public Media setName( String name )
	{
		if ( SysUtils.isEmpty( name ) ) {
			throw new InvalidParameterException( "Media name could not be null" );
		}
		this.name = name;
		return this;
	}

	public byte[ ] getObject( )
	{
		return this.object;
	}

	public Media setObject( byte[ ] object )
	{
		this.setSize( object == null ? 0 : object.length );
		if ( this.getSize( ) > 0 ) {
			this.object = object.clone( );
		}
		return this;
	}

	@Override
	public MediaDTO toDTO( )
	{
		MediaDTO target = new MediaDTO( this.getId( ), this.getName( ) );

		/*
		 * Não será retornado o objeto nesta cópia.
		 */
		target.setMimeType( this.getMimeType( ) );
		target.setName( this.getName( ) );
		target.setFormat( this.getFormat( ) );
		target.setSize( this.getSize( ) );
		return target;
	}

	protected Media setSize( Integer size )
	{
		this.size = size;
		return this;
	}

	public Integer getSize( )
	{
		return this.size;
	}

	public Media setFormat( String format )
	{
		this.format = format;
		return this;
	}

	public String getFormat( )
	{
		return this.format;
	}

	@Override
	public int compareTo( Media o )
	{
		if ( o == null ) {
			return -1;
		}
		if ( this.getId( ) == null ) {
			return 1;
		}
		return this.getId( ).compareTo( o.getId( ) );
	}
}
