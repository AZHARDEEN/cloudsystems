package br.com.mcampos.entity.fdigital;

import java.io.Serializable;
import java.util.ArrayList;
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

import br.com.mcampos.entity.system.Media;
import br.com.mcampos.sysutils.SysUtils;

/**
 * The persistent class for the pad database table.
 * 
 */
@Entity
@Table( name = "pad", schema = "public" )
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
	private List<AnotoPage> pages;

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
		if ( id == null ) {
			id = new PadPK( );
		}
		return id;
	}

	public void setId( PadPK id )
	{
		this.id = id;
	}

	public Date getInsertDate( )
	{
		return insertDate;
	}

	public void setInsertDate( Date padInsertDt )
	{
		insertDate = padInsertDt;
	}

	public Boolean getUnique( )
	{
		return unique;
	}

	public void setUnique( Boolean padUniqueBt )
	{
		unique = padUniqueBt;
	}

	public List<AnotoPage> getPages( )
	{
		if ( pages == null ) {
			pages = new ArrayList<AnotoPage>( );
		}
		return pages;
	}

	public void setPages( List<AnotoPage> anotoPages )
	{
		pages = anotoPages;
	}

	public AnotoForm getForm( )
	{
		return form;
	}

	public void setForm( AnotoForm anotoForm )
	{
		form = anotoForm;
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
		if ( getMedia( ) != null ) {
			getId( ).setId( getMedia( ).getId( ) );
		}
	}

	public AnotoPage remove( AnotoPage item )
	{
		SysUtils.remove( getPages( ), item );
		if ( item != null ) {
			item.setPad( null );
		}
		return item;
	}

	public AnotoPage add( AnotoPage item )
	{
		if ( item != null ) {
			int nIndex = getPages( ).indexOf( item );
			if ( nIndex < 0 ) {
				getPages( ).add( item );
				item.setPad( this );
			}
		}
		return item;
	}

}