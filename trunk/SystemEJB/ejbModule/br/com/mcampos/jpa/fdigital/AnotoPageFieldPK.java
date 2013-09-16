package br.com.mcampos.jpa.fdigital;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the anoto_page_field database table.
 * 
 */
@Embeddable
public class AnotoPageFieldPK implements Serializable
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "frm_id_in", unique = true, nullable = false )
	private Integer formId;

	@Column( name = "apg_id_ch", unique = true, nullable = false, length = 16 )
	private String ip;

	@Column( name = "pad_id_in", unique = true, nullable = false )
	private Integer padId;

	@Column( name = "apf_name_ch", unique = true, nullable = false, length = 128 )
	private String id;

	public AnotoPageFieldPK( )
	{

	}

	public void set( AnotoPage page )
	{
		if ( page != null ) {
			setFormId( page.getId( ).getFormId( ) );
			setPadId( page.getId( ).getPadId( ) );
			setIp( page.getId( ).getId( ) );
		}
		else {
			setFormId( null );
			setPadId( null );
			setIp( null );
		}
	}

	public Integer getFormId( )
	{
		return this.formId;
	}

	public void setFormId( Integer frmIdIn )
	{
		this.formId = frmIdIn;
	}

	public String getIp( )
	{
		return this.ip;
	}

	public void setIp( String apgIdCh )
	{
		this.ip = apgIdCh;
	}

	public Integer getPadId( )
	{
		return this.padId;
	}

	public void setPadId( Integer padIdIn )
	{
		this.padId = padIdIn;
	}

	public String getId( )
	{
		return this.id;
	}

	public void setId( String apfNameCh )
	{
		this.id = apfNameCh;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof AnotoPageFieldPK ) ) {
			return false;
		}
		AnotoPageFieldPK castOther = (AnotoPageFieldPK) other;
		return this.formId.equals( castOther.formId )
				&& this.ip.equals( castOther.ip )
				&& this.padId.equals( castOther.padId )
				&& this.id.equals( castOther.id );

	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.formId.hashCode( );
		hash = hash * prime + this.ip.hashCode( );
		hash = hash * prime + this.padId.hashCode( );
		hash = hash * prime + this.id.hashCode( );

		return hash;
	}
}