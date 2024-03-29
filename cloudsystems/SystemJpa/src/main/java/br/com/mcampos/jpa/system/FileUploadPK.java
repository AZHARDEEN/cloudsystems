package br.com.mcampos.jpa.system;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.com.mcampos.jpa.user.CollaboratorPK;

/**
 * The primary key class for the file_upload database table.
 * 
 */
@Embeddable
public class FileUploadPK implements Serializable, Comparable<FileUploadPK>
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "usr_id_in" )
	private Integer companyId;

	@Column( name = "med_id_in" )
	private Integer media;

	public FileUploadPK( )
	{
	}

	public void set( CollaboratorPK key )
	{
		if ( key != null )
		{
			this.setCompanyId( key.getCompanyId( ) );
		}
		else {
			this.setCompanyId( null );
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

	public Integer getMedia( )
	{
		return this.media;
	}

	public void setMedia( Integer medIdIn )
	{
		this.media = medIdIn;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof FileUploadPK ) ) {
			return false;
		}
		FileUploadPK castOther = (FileUploadPK) other;
		return this.companyId.equals( castOther.companyId )
				&& this.media.equals( castOther.media );

	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.companyId.hashCode( );
		hash = hash * prime + this.media.hashCode( );

		return hash;
	}

	@Override
	public int compareTo( FileUploadPK other )
	{
		if ( this == other ) {
			return 0;
		}
		if ( !( other instanceof FileUploadPK ) ) {
			return -1;
		}
		int nRet = this.getCompanyId( ).compareTo( other.getCompanyId( ) );
		if ( nRet == 0 ) {
			nRet = this.getMedia( ).compareTo( other.getMedia( ) );
		}
		return 0;
	}
}