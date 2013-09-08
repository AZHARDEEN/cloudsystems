package br.com.mcampos.entity.fdigital;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.mcampos.entity.system.FieldType;

/**
 * The persistent class for the anoto_page_field database table.
 * 
 */
@Entity
@Table( name = "anoto_page_field", schema = "public" )
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
		if ( id == null ) {
			id = new AnotoPageFieldPK( );
		}
		return id;
	}

	public void setId( AnotoPageFieldPK id )
	{
		this.id = id;
	}

	public Boolean getIrc( )
	{
		return irc;
	}

	public void setIrc( Boolean aftIcrBt )
	{
		irc = aftIcrBt;
	}

	public String getAlias( )
	{
		return alias;
	}

	public void setAlias( String alfAliasCh )
	{
		alias = alfAliasCh;
	}

	public Integer getHeight( )
	{
		return height;
	}

	public void setHeight( Integer alfHeightIn )
	{
		height = alfHeightIn;
	}

	public Boolean getSearch( )
	{
		return search;
	}

	public void setSearch( Boolean alfSearchBt )
	{
		search = alfSearchBt;
	}

	public Integer getTop( )
	{
		return top;
	}

	public void setTop( Integer alfTopIn )
	{
		top = alfTopIn;
	}

	public Integer getWidth( )
	{
		return width;
	}

	public void setWidth( Integer alfWidthIn )
	{
		width = alfWidthIn;
	}

	public Boolean getExport( )
	{
		return export;
	}

	public void setExport( Boolean apfExportBt )
	{
		export = apfExportBt;
	}

	public Integer getLeft( )
	{
		return left;
	}

	public void setLeft( Integer apfLeftIn )
	{
		left = apfLeftIn;
	}

	public Boolean getPk( )
	{
		return pk;
	}

	public void setPk( Boolean apfPkBt )
	{
		pk = apfPkBt;
	}

	public Integer getSequence( )
	{
		return sequence;
	}

	public void setSequence( Integer apfSequenceIn )
	{
		sequence = apfSequenceIn;
	}

	public AnotoPage getPage( )
	{
		return page;
	}

	public void setPage( AnotoPage anotoPage )
	{
		page = anotoPage;
		getId( ).set( anotoPage );
	}

	public FieldType getType( )
	{
		return type;
	}

	public void setType( FieldType type )
	{
		this.type = type;
	}

}