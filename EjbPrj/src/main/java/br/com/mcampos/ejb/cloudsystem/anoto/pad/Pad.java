package br.com.mcampos.ejb.cloudsystem.anoto.pad;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.ejb.cloudsystem.EntityCopyInterface;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;

@Entity
@NamedQueries( { @NamedQuery( name = Pad.formPadsNamedQuery, query = "select o from Pad o" ),
		@NamedQuery( name = Pad.padFindAllNamedQuery, query = "select o from Pad o where o.form.application = ?1" ) } )
@Table( name = "pad" )
@IdClass( PadPK.class )
public class Pad implements Serializable, EntityCopyInterface<PadDTO>, Comparable<Pad>
{
	public static final String formPadsNamedQuery = "Pad.findFormPads";
	public static final String padFindAllNamedQuery = "Pad.findAll";
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "frm_id_in", nullable = false )
	private Integer formId;

	@Id
	@Column( name = "pad_id_in", nullable = false )
	private Integer id;

	@Column( name = "pad_insert_dt", nullable = false )
	@Temporal( TemporalType.DATE )
	private Date insertDate;

	@Column( name = "pad_unique_bt", nullable = true )
	private Boolean unique;

	@ManyToOne( optional = false )
	@JoinColumn( name = "frm_id_in", columnDefinition = "Integer", insertable = false, updatable = false )
	private AnotoForm form;

	@ManyToOne( optional = false )
	@JoinColumn( name = "pad_id_in", insertable = false, updatable = false )
	private Media media;

	public Pad( )
	{
	}

	public Pad( Integer formId, Integer mediaId, Date insertDate )
	{
		this.formId = formId;
		this.id = mediaId;
		this.insertDate = insertDate;
	}

	public Pad( AnotoForm form, Media media )
	{
		this.setForm( form );
		this.setMedia( media );
	}

	public Integer getFormId( )
	{
		return this.formId;
	}

	public void setFormId( Integer frm_id_in )
	{
		this.formId = frm_id_in;
	}

	public Integer getId( )
	{
		return this.id;
	}

	public void setId( Integer pad_id_in )
	{
		this.id = pad_id_in;
	}

	public Date getInsertDate( )
	{
		return this.insertDate;
	}

	public void setInsertDate( Date pad_insert_dt )
	{
		this.insertDate = pad_insert_dt;
	}

	public void setForm( AnotoForm form )
	{
		this.form = form;
		if( form != null ) {
			this.setFormId( form.getId( ) );
		}
	}

	public AnotoForm getForm( )
	{
		return this.form;
	}

	public void setMedia( Media media )
	{
		this.media = media;
		if( media != null ) {
			this.setId( media.getId( ) );
		}
	}

	public Media getMedia( )
	{
		return this.media;
	}

	@Override
	public PadDTO toDTO( )
	{
		PadDTO dto = new PadDTO( this.getForm( ).toDTO( ), this.getMedia( ).toDTO( ) );
		return dto;
	}

	@Override
	public int compareTo( Pad o )
	{
		int nRet;

		nRet = this.getId( ).compareTo( o.getId( ) );
		if( nRet != 0 ) {
			return nRet;
		}
		return this.getForm( ).compareTo( o.getForm( ) );
	}

	public void setUnique( Boolean unique )
	{
		this.unique = unique;
	}

	public Boolean getUnique( )
	{
		if( this.unique == null ) {
			this.unique = false;
		}
		return this.unique;
	}
}
