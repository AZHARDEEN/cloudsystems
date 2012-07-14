package br.com.mcampos.ejb.fdigital;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mcampos.ejb.fdigital.form.AnotoForm;
import br.com.mcampos.ejb.media.Media;

/**
 * The persistent class for the pad database table.
 * 
 */
@Entity
@Table( name = "pad" )
public class Pad implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PadPK id;

	@Temporal( TemporalType.TIMESTAMP )
	@Column( name = "pad_insert_dt" )
	private Date insertDate;

	@Column( name = "pad_unique_bt" )
	private Boolean unique;

	// bi-directional many-to-one association to AnotoPage
	@OneToMany( mappedBy = "pad" )
	private List<AnotoPage> anotoPages;

	// bi-directional many-to-one association to AnotoForm
	@ManyToOne
	@JoinColumn( name = "frm_id_in", nullable = false, insertable = false, updatable = false )
	private AnotoForm form;

	@OneToOne
	@JoinColumn( name = "pad_id_in", referencedColumnName = "med_id_in", nullable = false, insertable = false, updatable = false )
	private Media media;

	public Pad( )
	{
	}

	public PadPK getId( )
	{
		if ( getId( ) == null )
			id = new PadPK( );
		return this.id;
	}

	public void setId( PadPK id )
	{
		this.id = id;
	}

	public Date getInsertDate( )
	{
		return this.insertDate;
	}

	public void setInsertDate( Date padInsertDt )
	{
		this.insertDate = padInsertDt;
	}

	public Boolean getUnique( )
	{
		return this.unique;
	}

	public void setUnique( Boolean padUniqueBt )
	{
		this.unique = padUniqueBt;
	}

	public List<AnotoPage> getAnotoPages( )
	{
		return this.anotoPages;
	}

	public void setAnotoPages( List<AnotoPage> anotoPages )
	{
		this.anotoPages = anotoPages;
	}

	public AnotoForm getForm( )
	{
		return this.form;
	}

	public void setForm( AnotoForm anotoForm )
	{
		this.form = anotoForm;
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
		if ( getMedia( ) != null ) {
			getId( ).setId( getMedia( ).getId( ) );
		}
	}

}