package br.com.mcampos.ejb.fdigital.form.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the anoto_form_user database table.
 * 
 */
@Embeddable
public class AnotoFormUserPK implements Serializable, Comparable<AnotoFormUserPK>
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "frm_id_in", unique = true, nullable = false )
	private Integer formId;

	@Column( name = "afu_seq_in", unique = true, nullable = false )
	private Integer sequence;

	public AnotoFormUserPK( )
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

	public Integer getSequence( )
	{
		return this.sequence;
	}

	public void setSequence( Integer afuSeqIn )
	{
		this.sequence = afuSeqIn;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof AnotoFormUserPK ) ) {
			return false;
		}
		AnotoFormUserPK castOther = (AnotoFormUserPK) other;
		return this.formId.equals( castOther.formId )
				&& this.sequence.equals( castOther.sequence );

	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.formId.hashCode( );
		hash = hash * prime + this.sequence.hashCode( );

		return hash;
	}

	@Override
	public int compareTo( AnotoFormUserPK o )
	{
		int nRet = getFormId( ).compareTo( o.getFormId( ) );
		if ( nRet == 0 ) {
			nRet = getSequence( ).compareTo( o.getSequence( ) );
		}
		return nRet;
	}
}