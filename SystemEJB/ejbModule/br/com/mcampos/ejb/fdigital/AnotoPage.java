package br.com.mcampos.ejb.fdigital;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the anoto_page database table.
 * 
 */
@Entity
@Table( name = "anoto_page" )
public class AnotoPage implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AnotoPagePK id;

	@Column( name = "apg_description_ch", length = 64 )
	private String description;

	@Column( name = "apg_icr_template_ch", length = 512 )
	private String templateIcr;

	// bi-directional many-to-one association to Pad
	@ManyToOne
	@JoinColumns( {
			@JoinColumn(
					name = "frm_id_in", referencedColumnName = "frm_id_in", nullable = false, insertable = false, updatable = false ),
			@JoinColumn(
					name = "pad_id_in", referencedColumnName = "pad_id_in", nullable = false, insertable = false, updatable = false )
	} )
	private Pad pad;

	// bi-directional many-to-one association to AnotoPageField
	@OneToMany( mappedBy = "page" )
	private List<AnotoPageField> fields;

	// bi-directional many-to-one association to AnotoPenPage
	@OneToMany( mappedBy = "page" )
	private List<AnotoPenPage> anotoPenPages;

	public AnotoPage( )
	{
	}

	public AnotoPagePK getId( )
	{
		return this.id;
	}

	public void setId( AnotoPagePK id )
	{
		this.id = id;
	}

	public String getDescription( )
	{
		return this.description;
	}

	public void setDescription( String apgDescriptionCh )
	{
		this.description = apgDescriptionCh;
	}

	public String getTemplateIcr( )
	{
		return this.templateIcr;
	}

	public void setTemplateIcr( String apgIcrTemplateCh )
	{
		this.templateIcr = apgIcrTemplateCh;
	}

	public Pad getPad( )
	{
		return this.pad;
	}

	public void setPad( Pad pad )
	{
		this.pad = pad;
	}

	public List<AnotoPageField> getFields( )
	{
		return this.fields;
	}

	public void setFields( List<AnotoPageField> anotoPageFields )
	{
		this.fields = anotoPageFields;
	}

	public List<AnotoPenPage> getAnotoPenPages( )
	{
		return this.anotoPenPages;
	}

	public void setAnotoPenPages( List<AnotoPenPage> anotoPenPages )
	{
		this.anotoPenPages = anotoPenPages;
	}
}