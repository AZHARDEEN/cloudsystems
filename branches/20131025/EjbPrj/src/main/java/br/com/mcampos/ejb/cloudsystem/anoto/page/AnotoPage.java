package br.com.mcampos.ejb.cloudsystem.anoto.page;

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

import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.ejb.cloudsystem.EntityCopyInterface;
import br.com.mcampos.ejb.cloudsystem.anoto.pad.Pad;

@Entity
@NamedQueries( { @NamedQuery( name = AnotoPage.anotoPagesGetAllNamedQuery,
		query = "select o from AnotoPage o order by o.pageAddress " ),
		@NamedQuery( name = AnotoPage.padPagesGetAllNamedQuery,
				query = "select o from AnotoPage o where o.pad = ?1 order by o.pageAddress" ),
		@NamedQuery( name = AnotoPage.formPagesGetAllNamedQuery,
				query = "select o from AnotoPage o where o.pad.form = ?1 order by o.pageAddress" ),
		@NamedQuery( name = AnotoPage.pagesGetAddressesNamedQuery,
				query = "select o from AnotoPage o where o.pageAddress = ?1" ) } )
@Table( name = "anoto_page" )
@IdClass( AnotoPagePK.class )
public class AnotoPage implements Serializable, EntityCopyInterface<AnotoPageDTO>, Comparable<AnotoPage>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4292215327572547942L;
	public static final String anotoPagesGetAllNamedQuery = "AnotoPage.findAll";
	public static final String padPagesGetAllNamedQuery = "AnotoPage.padFindAll";
	public static final String formPagesGetAllNamedQuery = "AnotoPage.formFindAll";
	public static final String pagesGetAddressesNamedQuery = "AnotoPage.pagesGetAddresses";

	@Id
	@Column( name = "frm_id_in", nullable = false )
	private Integer formId;

	@Id
	@Column( name = "pad_id_in", nullable = false )
	private Integer padId;

	@Id
	@Column( name = "apg_id_ch", nullable = false )
	private String pageAddress;

	@ManyToOne( optional = false )
	@JoinColumns( {
			@JoinColumn( name = "frm_id_in", referencedColumnName = "frm_id_in", columnDefinition = "Integer", insertable = false, updatable = false ),
			@JoinColumn( name = "pad_id_in", referencedColumnName = "pad_id_in", columnDefinition = "Integer", insertable = false, updatable = false )
	} )
	private Pad pad;

	@Column( name = "apg_description_ch", nullable = true )
	private String description;

	@Column( name = "apg_icr_template_ch", nullable = true )
	private String icrTemplate;

	public AnotoPage( )
	{
	}

	public AnotoPage( Pad pad, String address )
	{
		this.setPad( pad );
		this.setPageAddress( address );
	}

	public String getPageAddress( )
	{
		return this.pageAddress;
	}

	public void setPageAddress( String apg_id_ch )
	{
		this.pageAddress = apg_id_ch;
	}

	public Integer getFormId( )
	{
		return this.formId;
	}

	public void setFormId( Integer frm_id_in )
	{
		this.formId = frm_id_in;
	}

	public Integer getPadId( )
	{
		return this.padId;
	}

	public void setPadId( Integer pad_id_in )
	{
		this.padId = pad_id_in;
	}

	public void setPad( Pad pad )
	{
		this.pad = pad;
		if( pad != null ) {
			this.setFormId( pad.getFormId( ) );
			this.setPadId( pad.getId( ) );
		}
	}

	public Pad getPad( )
	{
		return this.pad;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

	public String getDescription( )
	{
		return this.description;
	}

	@Override
	public AnotoPageDTO toDTO( )
	{
		AnotoPageDTO dto = new AnotoPageDTO( this.getPad( ).toDTO( ), this.getPageAddress( ) );
		dto.setDescription( this.getDescription( ) );
		dto.setIcrTemplate( this.getIcrTemplate( ) );
		return dto;
	}

	@Override
	public int compareTo( AnotoPage o )
	{
		int nRet;

		nRet = this.getPageAddress( ).compareTo( o.getPageAddress( ) );
		if( nRet != 0 ) {
			return nRet;
		}
		return this.getPad( ).compareTo( o.getPad( ) );
	}

	public void setIcrTemplate( String icrTemplate )
	{
		this.icrTemplate = icrTemplate;
	}

	public String getIcrTemplate( )
	{
		return this.icrTemplate;
	}
}
