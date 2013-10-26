package br.com.mcampos.ejb.cloudsystem.anoto.form.media;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.dto.anoto.FormMediaDTO;
import br.com.mcampos.ejb.cloudsystem.EntityCopyInterface;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;

@Entity
@NamedQueries( { @NamedQuery( name = "FormMedia.findAll", query = "select o from FormMedia o" ),
		@NamedQuery( name = FormMedia.formGetFiles, query = "select o from FormMedia o where o.form = ?1" ),
		@NamedQuery( name = FormMedia.formPDFTemplate,
				query = "select o from FormMedia o where o.form = ?1 and o.media.name like '%template.pdf' and o.media.format = 'pdf'" ) } )
@Table( name = "form_media" )
@IdClass( FormMediaPK.class )
public class FormMedia implements Serializable, EntityCopyInterface<FormMediaDTO>
{
	public static final String formGetFiles = "FormMedia.formGetFiles";
	public static final String formPDFTemplate = "FormMedia.getPDFTemplate";

	@Id
	@Column( name = "frm_id_in", nullable = false )
	private Integer formId;
	@Id
	@Column( name = "med_id_in", nullable = false )
	private Integer mediaId;

	@ManyToOne( optional = false )
	@JoinColumn( name = "frm_id_in", columnDefinition = "Integer", insertable = false, updatable = false )
	private AnotoForm form;

	@ManyToOne( optional = false )
	@JoinColumn( name = "med_id_in", columnDefinition = "Integer", insertable = false, updatable = false )
	private Media media;

	public FormMedia( )
	{
	}

	public FormMedia( AnotoForm form, Media media )
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

	public Integer getMediaId( )
	{
		return this.mediaId;
	}

	public void setMediaId( Integer med_id_in )
	{
		this.mediaId = med_id_in;
	}

	public void setForm( AnotoForm form )
	{
		this.form = form;
		this.setFormId( form != null ? form.getId( ) : 0 );
	}

	public AnotoForm getForm( )
	{
		return this.form;
	}

	public void setMedia( Media media )
	{
		this.media = media;
		this.setMediaId( media != null ? media.getId( ) : 0 );
	}

	public Media getMedia( )
	{
		return this.media;
	}

	@Override
	public FormMediaDTO toDTO( )
	{
		return new FormMediaDTO( this.getForm( ).toDTO( ), this.getMedia( ).toDTO( ) );
	}
}
