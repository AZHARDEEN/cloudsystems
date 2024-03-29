package br.com.mcampos.dto.system;

import java.io.Serializable;

public class MediaDTO implements Comparable<MediaDTO>, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7581961457246342172L;
	protected Integer id;
	private String mimeType;
	private String name;
	private byte[ ] object;
	private Integer size;
	private String format;
	private String path;

	public MediaDTO( )
	{
		super( );
	}

	public MediaDTO( Integer id, String name )
	{
		super( );
		this.setId( id ).setName( name );
	}

	public MediaDTO setMimeType( String mimeType )
	{
		this.mimeType = mimeType;
		return this;
	}

	public String getMimeType( )
	{
		return this.mimeType;
	}

	public MediaDTO setName( String name )
	{
		this.name = name;
		return this;
	}

	public String getName( )
	{
		return this.name;
	}

	public MediaDTO setObject( byte[ ] object )
	{
		this.object = object.clone( );
		if( object != null ) {
			this.setSize( object.length );
		}
		return this;
	}

	public byte[ ] getObject( )
	{
		return this.object;
	}

	public MediaDTO setSize( Integer size )
	{
		this.size = size;
		return this;
	}

	public Integer getSize( )
	{
		return this.size;
	}

	public MediaDTO setFormat( String format )
	{
		this.format = format;
		return this;
	}

	public String getFormat( )
	{
		return this.format;
	}

	public MediaDTO setId( Integer id )
	{
		this.id = id;
		return this;
	}

	public Integer getId( )
	{
		if( this.id == null ) {
			this.id = 0;
		}
		return this.id;
	}

	@Override
	public int compareTo( MediaDTO o )
	{
		if( o == null ) {
			return 1;
		}
		return this.getId( ).compareTo( o.getId( ) );
	}

	@Override
	public boolean equals( Object obj )
	{
		if( obj == null || (obj instanceof MediaDTO) == false ) {
			return false;
		}
		return this.getId( ).equals( ((MediaDTO) obj).getId( ) );
	}

	@Override
	public String toString( )
	{
		return this.getName( );
	}

	public String getPath( )
	{
		return this.path;
	}

	public void setPath( String path )
	{
		this.path = path;
	}
}
