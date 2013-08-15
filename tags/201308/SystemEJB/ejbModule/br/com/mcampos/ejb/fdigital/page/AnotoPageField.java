package br.com.mcampos.ejb.fdigital.page;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.mcampos.ejb.fdigital.form.pad.page.AnotoPage;
import br.com.mcampos.ejb.system.fieldtype.FieldType;

/**
 * The persistent class for the anoto_page_field database table.
 * 
 */
@Entity
@Table( name = "anoto_page_field" )
public class AnotoPageField implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AnotoPageFieldPK id;

	@Column( name = "aft_icr_bt", nullable = false )
	private Boolean irc;

	@Column( name = "alf_alias_ch", length = 256 )
	private String alias;

	@Column( name = "alf_height_in" )
	private Integer height;

	@Column( name = "alf_search_bt" )
	private Boolean search;

	@Column( name = "alf_top_in" )
	private Integer top;

	@Column( name = "alf_width_in" )
	private Integer width;

	@Column( name = "apf_export_bt" )
	private Boolean export;

	@Column( name = "apf_left_in" )
	private Integer left;

	@Column( name = "apf_pk_bt" )
	private Boolean pk;

	@Column( name = "apf_sequence_in" )
	private Integer sequence;

	@ManyToOne
	@JoinColumn(
			name = "flt_id_in", referencedColumnName = "flt_id_in", nullable = false, insertable = true, updatable = true )
	private FieldType type;

	// bi-directional many-to-one association to AnotoPage
	@ManyToOne
	@JoinColumns( {
			@JoinColumn(
					name = "apg_id_ch", referencedColumnName = "apg_id_ch", nullable = false, insertable = false, updatable = false ),
			@JoinColumn(
					name = "frm_id_in", referencedColumnName = "frm_id_in", nullable = false, insertable = false, updatable = false ),
			@JoinColumn(
					name = "pad_id_in", referencedColumnName = "pad_id_in", nullable = false, insertable = false, updatable = false )
	} )
	private AnotoPage page;

	public AnotoPageField( )
	{
	}

	public AnotoPageFieldPK getId( )
	{
		if ( this.id == null ) {
			this.id = new AnotoPageFieldPK( );
		}
		return this.id;
	}

	public void setId( AnotoPageFieldPK id )
	{
		this.id = id;
	}

	public Boolean getIrc( )
	{
		return this.irc;
	}

	public void setIrc( Boolean aftIcrBt )
	{
		this.irc = aftIcrBt;
	}

	public String getAlias( )
	{
		return this.alias;
	}

	public void setAlias( String alfAliasCh )
	{
		this.alias = alfAliasCh;
	}

	public Integer getHeight( )
	{
		return this.height;
	}

	public void setHeight( Integer alfHeightIn )
	{
		this.height = alfHeightIn;
	}

	public Boolean getSearch( )
	{
		return this.search;
	}

	public void setSearch( Boolean alfSearchBt )
	{
		this.search = alfSearchBt;
	}

	public Integer getTop( )
	{
		return this.top;
	}

	public void setTop( Integer alfTopIn )
	{
		this.top = alfTopIn;
	}

	public Integer getWidth( )
	{
		return this.width;
	}

	public void setWidth( Integer alfWidthIn )
	{
		this.width = alfWidthIn;
	}

	public Boolean getExport( )
	{
		return this.export;
	}

	public void setExport( Boolean apfExportBt )
	{
		this.export = apfExportBt;
	}

	public Integer getLeft( )
	{
		return this.left;
	}

	public void setLeft( Integer apfLeftIn )
	{
		this.left = apfLeftIn;
	}

	public Boolean getPk( )
	{
		return this.pk;
	}

	public void setPk( Boolean apfPkBt )
	{
		this.pk = apfPkBt;
	}

	public Integer getSequence( )
	{
		return this.sequence;
	}

	public void setSequence( Integer apfSequenceIn )
	{
		this.sequence = apfSequenceIn;
	}

	public AnotoPage getPage( )
	{
		return this.page;
	}

	public void setPage( AnotoPage anotoPage )
	{
		this.page = anotoPage;
		getId( ).set( anotoPage );
	}

	public FieldType getType( )
	{
		return this.type;
	}

	public void setType( FieldType type )
	{
		this.type = type;
	}

}