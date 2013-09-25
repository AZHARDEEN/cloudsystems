package br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.image;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPage;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;

@Entity
@NamedQueries( {
		@NamedQuery( name = "PgcProcessedImage.findAll", query = "select o from PgcProcessedImage o" ),
		@NamedQuery( name = PgcProcessedImage.findPgcPageImages, query = "select o from PgcProcessedImage o where o.pgcPage = ?1" )
} )
@Table( name = "pgc_processed_image" )
@IdClass( PgcProcessedImagePK.class )
public class PgcProcessedImage implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2428087116645388827L;

	public static final String findPgcPageImages = "PgcProcessedImage.findPgcPageImages";

	@Id
	@Column( name = "med_id_in", nullable = false )
	private Integer mediaId;

	@Id
	@Column( name = "pgc_id_in", nullable = false )
	private Integer pgcId;

	@Id
	@Column( name = "ppg_book_id", nullable = false )
	private Integer bookId;

	@Id
	@Column( name = "ppg_page_id", nullable = false )
	private Integer pageId;

	@ManyToOne
	@JoinColumns( {
			@JoinColumn( name = "pgc_id_in", referencedColumnName = "pgc_id_in", insertable = false, updatable = false ),
			@JoinColumn( name = "ppg_book_id", referencedColumnName = "ppg_book_id", insertable = false, updatable = false ),
			@JoinColumn( name = "ppg_page_id", referencedColumnName = "ppg_page_id", insertable = false, updatable = false ) } )
	private PgcPage pgcPage;

	@ManyToOne
	@JoinColumn( name = "med_id_in", referencedColumnName = "med_id_in", insertable = false, updatable = false )
	private Media media;

	public PgcProcessedImage( )
	{
	}

	public PgcProcessedImage( PgcPage pgc, Media media, Integer ppi_book_id, Integer ppi_page_id )
	{
		this.setPgc( pgc );
		this.setMedia( media );
		this.setBookId( ppi_book_id );
		this.setPageId( ppi_page_id );
	}

	public Integer getMediaId( )
	{
		return this.mediaId;
	}

	public void setMediaId( Integer med_id_in )
	{
		this.mediaId = med_id_in;
	}

	public Integer getPgcId( )
	{
		return this.pgcId;
	}

	public void setPgcId( Integer pgc_id_in )
	{
		this.pgcId = pgc_id_in;
	}

	public Integer getBookId( )
	{
		return this.bookId;
	}

	public void setBookId( Integer ppi_book_id )
	{
		this.bookId = ppi_book_id;
	}

	public Integer getPageId( )
	{
		return this.pageId;
	}

	public void setPageId( Integer ppi_page_id )
	{
		this.pageId = ppi_page_id;
	}

	public void setPgc( PgcPage pgc )
	{
		this.pgcPage = pgc;
		if( this.pgcPage != null ) {
			this.setBookId( this.pgcPage.getBookId( ) );
			this.setPageId( this.pgcPage.getPageId( ) );
			this.setPgcId( this.pgcPage.getPgcId( ) );
		}
	}

	public PgcPage getPgc( )
	{
		return this.pgcPage;
	}

	public void setMedia( Media media )
	{
		this.media = media;
		if( media != null ) {
			this.setMediaId( media.getId( ) );
		}
	}

	public Media getMedia( )
	{
		return this.media;
	}
}
