package br.com.mcampos.ejb.fdigital.form.pad.page;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the anoto_page database table.
 * 
 */
@Embeddable
public class AnotoPagePK implements Serializable, Comparable<AnotoPagePK>
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "frm_id_in", unique = true, nullable = false )
	private Integer formId;

	@Column( name = "apg_id_ch", unique = true, nullable = false, length = 16 )
	private String id;

	@Column( name = "pad_id_in", unique = true, nullable = false )
	private Integer padId;

	public AnotoPagePK( )
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

	public String getId( )
	{
		return this.id;
	}

	public void setId( String apgIdCh )
	{
		this.id = apgIdCh;
	}

	public Integer getPadId( )
	{
		return this.padId;
	}

	public void setPadId( Integer padIdIn )
	{
		this.padId = padIdIn;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof AnotoPagePK ) ) {
			return false;
		}
		AnotoPagePK castOther = (AnotoPagePK) other;
		return this.formId.equals( castOther.formId )
				&& this.id.equals( castOther.id )
				&& this.padId.equals( castOther.padId );

	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.formId.hashCode( );
		hash = hash * prime + this.id.hashCode( );
		hash = hash * prime + this.padId.hashCode( );

		return hash;
	}

	@Override
	public int compareTo( AnotoPagePK o )
	{
		int nRet = getFormId( ).compareTo( o.getFormId( ) );
		if ( nRet == 0 ) {
			nRet = getPadId( ).compareTo( o.getPadId( ) );
			if ( nRet == 0 ) {
				nRet = getId( ).compareTo( o.getId( ) );
			}
		}
		return nRet;
	}
}