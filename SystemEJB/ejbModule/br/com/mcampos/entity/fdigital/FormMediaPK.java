package br.com.mcampos.entity.fdigital;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the form_media database table.
 * 
 */
@Embeddable
public class FormMediaPK implements Serializable, Comparable<FormMediaPK>
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "frm_id_in", unique = true, nullable = false )
	private Integer formId;

	@Column( name = "med_id_in", unique = true, nullable = false )
	private Integer mediaId;

	public FormMediaPK( )
	{
	}

	public Integer getFormId( )
	{
		return this.formId;
	}

	public void setFormId( Integer frmIdIn )
	{
		this.formId = frmIdIn;
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
		if ( !(other instanceof FormMediaPK) ) {
			return false;
		}
		FormMediaPK castOther = (FormMediaPK) other;
		return this.formId.equals( castOther.formId )
				&& this.mediaId.equals( castOther.mediaId );

	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.formId.hashCode( );
		hash = hash * prime + this.mediaId.hashCode( );

		return hash;
	}

	@Override
	public int compareTo( FormMediaPK o )
	{
		int nRet = getFormId( ).compareTo( o.getFormId( ) );
		if ( nRet == 0 )
			return getMediaId( ).compareTo( o.getMediaId( ) );
		return nRet;
	}
}