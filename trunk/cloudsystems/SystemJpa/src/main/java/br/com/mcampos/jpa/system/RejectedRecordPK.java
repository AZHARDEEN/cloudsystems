package br.com.mcampos.jpa.system;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the rejected_records database table.
 * 
 */
@Embeddable
public class RejectedRecordPK implements Serializable
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "usr_id_in" )
	private Integer companyId;

	@Column( name = "med_id_in" )
	private Integer mediaId;

	@Column( name = "rrd_id_in" )
	private Integer id;

	public RejectedRecordPK( )
	{
	}

	public void set( FileUpload fileUpload )
	{
		if ( fileUpload == null )
		{
			this.setCompanyId( null );
			this.setMediaId( null );
		}
		else {
			this.setCompanyId( fileUpload.getId( ).getCompanyId( ) );
			this.setMediaId( fileUpload.getId( ).getMedia( ) );
		}
	}

	public Integer getCompanyId( )
	{
		return this.companyId;
	}

	public void setCompanyId( Integer usrIdIn )
	{
		this.companyId = usrIdIn;
	}

	public Integer getMediaId( )
	{
		return this.mediaId;
	}

	public void setMediaId( Integer medIdIn )
	{
		this.mediaId = medIdIn;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof RejectedRecordPK ) ) {
			return false;
		}
		RejectedRecordPK castOther = (RejectedRecordPK) other;
		return this.companyId.equals( castOther.companyId )
				&& this.mediaId.equals( castOther.mediaId )
				&& this.id.equals( castOther.id );

	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.companyId.hashCode( );
		hash = hash * prime + this.mediaId.hashCode( );
		hash = hash * prime + this.id.hashCode( );

		return hash;
	}

	public Integer getId( )
	{
		return this.id;
	}

	public void setId( Integer id )
	{
		this.id = id;
	}
}