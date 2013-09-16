package br.com.mcampos.jpa.fdigital;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.mcampos.jpa.system.Media;

/**
 * The persistent class for the form_media database table.
 * 
 */
@Entity
@Table( name = "form_media", schema = "public" )
public class FormMedia implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FormMediaPK id;

	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "frm_id_in", nullable = false, insertable = false, updatable = false )
	private AnotoForm form;

	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "med_id_in", nullable = false, insertable = false, updatable = false )
	private Media media;

	public FormMedia( )
	{
	}

	public FormMediaPK getId( )
	{
		if ( id == null )
			id = new FormMediaPK( );
		return id;
	}

	public void setId( FormMediaPK id )
	{
		this.id = id;
	}

	/**
	 * @return the form
	 */
	public AnotoForm getForm( )
	{
		return form;
	}

	/**
	 * @param form
	 *            the form to set
	 */
	public void setForm( AnotoForm form )
	{
		this.form = form;
		getId( ).setFormId( getForm( ) != null ? getForm( ).getId( ) : null );
	}

	/**
	 * @return the media
	 */
	public Media getMedia( )
	{
		return media;
	}

	/**
	 * @param media
	 *            the media to set
	 */
	public void setMedia( Media media )
	{
		this.media = media;
		getId( ).setMediaId( getMedia( ) != null ? getMedia( ).getId( ) : null );
	}
}