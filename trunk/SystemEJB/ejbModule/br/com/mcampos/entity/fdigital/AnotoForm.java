package br.com.mcampos.entity.fdigital;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mcampos.ejb.core.SimpleTable;
import br.com.mcampos.sysutils.SysUtils;

/**
 * The persistent class for the anoto_form database table.
 * 
 */
@Entity
@Table( name = "anoto_form", schema = "public" )
public class AnotoForm extends SimpleTable<AnotoForm>
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "frm_id_in", unique = true, nullable = false )
	private Integer id;

	@Column( name = "frm_concat_pgc_bt" )
	private Boolean concatenate;

	@Column( name = "frm_description_ch", length = 64 )
	private String descriptions;

	@Column( name = "frm_icr_image_bt" )
	private Boolean icr;

	@Column( name = "frm_image_filepath_ch", length = 1024 )
	private String imagePath;

	@Temporal( TemporalType.TIMESTAMP )
	@Column( name = "frm_insert_dt", nullable = false )
	private Date insertDate;

	@Column( name = "frm_ip_ch", nullable = false, length = 16 )
	private String application;

	// bi-directional many-to-one association to Pad
	@OneToMany( mappedBy = "form" )
	private List<Pad> pads;

	// bi-directional many-to-one association to Pad
	@OneToMany( mappedBy = "form" )
	private List<AnotoFormUser> clients;

	// bi-directional many-to-one association to Pad
	@OneToMany( mappedBy = "form" )
	private List<FormMedia> medias;

	public AnotoForm( )
	{
	}

	@Override
	public Integer getId( )
	{
		return id;
	}

	@Override
	public void setId( Integer frmIdIn )
	{
		id = frmIdIn;
	}

	public Boolean getConcatenate( )
	{
		return concatenate;
	}

	public void setConcatenate( Boolean frmConcatPgcBt )
	{
		concatenate = frmConcatPgcBt;
	}

	@Override
	public String getDescription( )
	{
		return descriptions;
	}

	@Override
	public void setDescription( String frmDescriptionCh )
	{
		descriptions = frmDescriptionCh;
	}

	public Boolean getIcr( )
	{
		return icr;
	}

	public void setIcr( Boolean frmIcrImageBt )
	{
		icr = frmIcrImageBt;
	}

	public String getImagePath( )
	{
		return imagePath;
	}

	public void setImagePath( String frmImageFilepathCh )
	{
		imagePath = frmImageFilepathCh;
	}

	public Date getFrmInsertDt( )
	{
		return insertDate;
	}

	public void setFrmInsertDt( Date frmInsertDt )
	{
		insertDate = frmInsertDt;
	}

	public String getApplication( )
	{
		return application;
	}

	public void setApplication( String frmIpCh )
	{
		application = frmIpCh;
	}

	@Override
	public String getField( Integer field )
	{
		return super.getField( field );
	}

	public List<Pad> getPads( )
	{
		if ( pads == null ) {
			pads = new ArrayList<Pad>( );
		}
		return pads;
	}

	public void setPads( List<Pad> pads )
	{
		this.pads = pads;
	}

	public List<AnotoFormUser> getClients( )
	{
		if ( clients == null ) {
			clients = new ArrayList<AnotoFormUser>( );
		}
		return clients;
	}

	public void setClients( List<AnotoFormUser> clients )
	{
		this.clients = clients;
	}

	/**
	 * @return the medias
	 */
	public List<FormMedia> getMedias( )
	{
		if ( medias == null ) {
			medias = new ArrayList<FormMedia>( );
		}
		return medias;
	}

	/**
	 * @param medias
	 *            the medias to set
	 */
	public void setMedias( List<FormMedia> medias )
	{
		this.medias = medias;
	}

	public FormMedia add( FormMedia item )
	{
		if ( item != null ) {
			int nIndex = getMedias( ).indexOf( item );
			if ( nIndex < 0 ) {
				getMedias( ).add( item );
				item.setForm( this );
			}
		}
		return item;
	}

	public Pad add( Pad item )
	{
		if ( item != null ) {
			int nIndex = getMedias( ).indexOf( item );
			if ( nIndex < 0 ) {
				getPads( ).add( item );
				item.setForm( this );
			}
		}
		return item;
	}

	public AnotoFormUser add( AnotoFormUser item )
	{
		if ( item != null ) {
			int nIndex = getClients( ).indexOf( item );
			if ( nIndex < 0 ) {
				getClients( ).add( item );
				item.setForm( this );
			}
		}
		return item;
	}

	public AnotoFormUser remove( AnotoFormUser item )
	{
		SysUtils.remove( getClients( ), item );
		if ( item != null ) {
			item.setForm( null );
		}
		return item;
	}

	public Pad remove( Pad item )
	{
		SysUtils.remove( getClients( ), item );
		if ( item != null ) {
			item.setForm( null );
		}
		return item;
	}

	public FormMedia remove( FormMedia item )
	{
		SysUtils.remove( getClients( ), item );
		if ( item != null ) {
			item.setForm( null );
		}
		return item;
	}

}