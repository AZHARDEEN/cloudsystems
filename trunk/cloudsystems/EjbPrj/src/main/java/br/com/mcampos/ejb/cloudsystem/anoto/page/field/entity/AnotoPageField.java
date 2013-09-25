package br.com.mcampos.ejb.cloudsystem.anoto.page.field.entity;

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

import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
import br.com.mcampos.ejb.cloudsystem.EntityCopyInterface;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.system.fieldtype.entity.FieldType;

@Entity
@NamedQueries( { @NamedQuery( name = AnotoPageField.getAll, query = "select o from AnotoPageField o" ),
		@NamedQuery( name = AnotoPageField.getAllFromPage,
				query = "select o from AnotoPageField o where o.anotoPage = ?1 order by o.sequence" ),
		@NamedQuery( name = AnotoPageField.getExport,
				query = "select o from AnotoPageField o where o.export = true order by o.sequence" ),
		@NamedQuery( name = AnotoPageField.getAllFromFormSearchable,
				query = "select o from AnotoPageField o where o.formId = ?1 and o.searchable = true order by o.pageAddress, o.sequence" ),
		@NamedQuery( name = AnotoPageField.getAllFromFormPK,
				query = "select o from AnotoPageField o where o.formId = ?1 and o.pk = true order by o.pageAddress, o.sequence" ),
		@NamedQuery( name = AnotoPageField.getAllFromForm,
				query = "select o from AnotoPageField o where o.formId = ?1 order by o.pageAddress, o.sequence" ) } )
@Table( name = "anoto_page_field" )
@IdClass( AnotoPageFieldPK.class )
public class AnotoPageField implements Serializable, Comparable<AnotoPageField>, EntityCopyInterface<AnotoPageFieldDTO>
{
	public static final String getAll = "AnotoPageField.findAll";
	public static final String getAllFromPage = "AnotoPageField.findAllFromPage";
	public static final String getAllFromForm = "AnotoPageField.findAllFromForm";
	public static final String getAllFromFormSearchable = "AnotoPageField.getSearchable";
	public static final String getAllFromFormPK = "AnotoPageField.getPKFields";
	public static final String getExport = "AnotoPageField.findExport";

	@Column( name = "aft_icr_bt", nullable = false )
	private Boolean icr;

	@Id
	@Column( name = "apf_name_ch", nullable = false )
	private String name;

	@Id
	@Column( name = "apg_id_ch", nullable = false )
	private String pageAddress;

	@Id
	@Column( name = "frm_id_in", nullable = false )
	private Integer formId;

	@Id
	@Column( name = "pad_id_in", nullable = false )
	private Integer padId;

	@Column( name = "apf_left_in" )
	private Integer left;

	@Column( name = "alf_top_in" )
	private Integer top;

	@Column( name = "alf_width_in" )
	private Integer width;

	@Column( name = "alf_height_in" )
	private Integer height;

	@Column( name = "apf_export_bt", nullable = true )
	private Boolean export;

	@Column( name = "alf_search_bt", nullable = true )
	private Boolean searchable;

	@Column( name = "apf_pk_bt", nullable = true )
	private Boolean pk;

	@Column( name = "apf_sequence_in", nullable = true )
	private Integer sequence;

	@ManyToOne( optional = false )
	@JoinColumn( name = "flt_id_in", referencedColumnName = "flt_id_in", columnDefinition = "Integer" )
	private FieldType type;

	@ManyToOne( optional = false )
	@JoinColumns( {
			@JoinColumn( name = "frm_id_in", referencedColumnName = "frm_id_in", columnDefinition = "Integer", insertable = false, updatable = false ),
			@JoinColumn( name = "pad_id_in", referencedColumnName = "pad_id_in", columnDefinition = "Integer", insertable = false, updatable = false ),
			@JoinColumn( name = "apg_id_ch", referencedColumnName = "apg_id_ch", columnDefinition = "Integer", insertable = false, updatable = false )
	} )
	private AnotoPage anotoPage;

	public AnotoPageField( )
	{
	}

	public AnotoPageField( AnotoPage page, String name, FieldType type )
	{
		this.setAnotoPage( page );
		this.setName( name );
		this.setType( type );
	}

	public Boolean hasIcr( )
	{
		return this.icr;
	}

	public void setIcr( Boolean hasIcr )
	{
		this.icr = hasIcr;
	}

	public String getName( )
	{
		return this.name;
	}

	public void setName( String apf_name_ch )
	{
		this.name = apf_name_ch;
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

	public void setType( FieldType type )
	{
		this.type = type;
	}

	public FieldType getType( )
	{
		return this.type;
	}

	public void setAnotoPage( AnotoPage anotoPage )
	{
		this.anotoPage = anotoPage;
		if( anotoPage != null ) {
			this.setPadId( anotoPage.getPadId( ) );
			this.setFormId( anotoPage.getFormId( ) );
			this.setPageAddress( anotoPage.getPageAddress( ) );
		}
	}

	public AnotoPage getAnotoPage( )
	{
		return this.anotoPage;
	}

	@Override
	public int compareTo( AnotoPageField o )
	{
		int nRet;

		nRet = this.getAnotoPage( ).compareTo( o.getAnotoPage( ) );
		if( nRet != 0 ) {
			return nRet;
		}
		return this.getName( ).compareTo( o.getName( ) );
	}

	public void setLeft( Integer left )
	{
		this.left = left;
	}

	public Integer getLeft( )
	{
		return this.left;
	}

	public void setTop( Integer top )
	{
		this.top = top;
	}

	public Integer getTop( )
	{
		return this.top;
	}

	public void setWidth( Integer width )
	{
		this.width = width;
	}

	public Integer getWidth( )
	{
		return this.width;
	}

	public void setHeight( Integer height )
	{
		this.height = height;
	}

	public Integer getHeight( )
	{
		return this.height;
	}

	@Override
	public AnotoPageFieldDTO toDTO( )
	{
		AnotoPageFieldDTO dto = new AnotoPageFieldDTO( this.getAnotoPage( ).toDTO( ), this.getName( ), this.getType( ).toDTO( ) );
		dto.setHeight( this.getHeight( ) );
		dto.setIcr( this.hasIcr( ) );
		dto.setLeft( this.getLeft( ) );
		dto.setTop( this.getTop( ) );
		dto.setWidth( this.getWidth( ) );
		dto.setExport( this.getExport( ) );
		dto.setSearchable( this.getSearchable( ) );
		dto.setSequence( this.getSequence( ) );
		dto.setPk( this.getPk( ) );
		return dto;
	}

	public void setExport( Boolean export )
	{
		this.export = export;
	}

	public Boolean getExport( )
	{
		return this.export;
	}

	public void setSearchable( Boolean searchable )
	{
		this.searchable = searchable;
	}

	public Boolean getSearchable( )
	{
		return this.searchable;
	}

	public void setSequence( Integer sequence )
	{
		this.sequence = sequence;
	}

	public Integer getSequence( )
	{
		return this.sequence;
	}

	public void setPk( Boolean pk )
	{
		this.pk = pk;
	}

	public Boolean getPk( )
	{
		return this.pk;
	}
}
