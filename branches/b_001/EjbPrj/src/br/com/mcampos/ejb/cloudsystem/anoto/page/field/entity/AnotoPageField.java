package br.com.mcampos.ejb.cloudsystem.anoto.page.field.entity;


import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.system.fieldtype.entity.FieldType;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;

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
    @Column( name = "apg_id_ch", nullable = false, insertable = false, updatable = false )
    private String pageAddress;

    @Id
    @Column( name = "frm_id_in", nullable = false, insertable = false, updatable = false )
    private Integer formId;

    @Id
    @Column( name = "pad_id_in", nullable = false, insertable = false, updatable = false )
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
    @JoinColumns( { @JoinColumn( name = "frm_id_in", referencedColumnName = "frm_id_in", columnDefinition = "Integer" ),
                    @JoinColumn( name = "pad_id_in", referencedColumnName = "pad_id_in", columnDefinition = "Integer" ),
                    @JoinColumn( name = "apg_id_ch", referencedColumnName = "apg_id_ch", columnDefinition = "Integer" ) } )
    private AnotoPage anotoPage;


    public AnotoPageField()
    {
    }


    public AnotoPageField( AnotoPage page, String name, FieldType type )
    {
        setAnotoPage( page );
        setName( name );
        setType( type );
    }


    public Boolean hasIcr()
    {
        return icr;
    }

    public void setIcr( Boolean hasIcr )
    {
        this.icr = hasIcr;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String apf_name_ch )
    {
        this.name = apf_name_ch;
    }

    public String getPageAddress()
    {
        return pageAddress;
    }

    public void setPageAddress( String apg_id_ch )
    {
        this.pageAddress = apg_id_ch;
    }

    public Integer getFormId()
    {
        return formId;
    }

    public void setFormId( Integer frm_id_in )
    {
        this.formId = frm_id_in;
    }

    public Integer getPadId()
    {
        return padId;
    }

    public void setPadId( Integer pad_id_in )
    {
        this.padId = pad_id_in;
    }

    public void setType( FieldType type )
    {
        this.type = type;
    }

    public FieldType getType()
    {
        return type;
    }

    public void setAnotoPage( AnotoPage anotoPage )
    {
        this.anotoPage = anotoPage;
        if ( anotoPage != null ) {
            setPadId( anotoPage.getPadId() );
            setFormId( anotoPage.getFormId() );
            setPageAddress( anotoPage.getPageAddress() );
        }
    }

    public AnotoPage getAnotoPage()
    {
        return anotoPage;
    }

    public int compareTo( AnotoPageField o )
    {
        int nRet;

        nRet = getAnotoPage().compareTo( o.getAnotoPage() );
        if ( nRet != 0 )
            return nRet;
        return getName().compareTo( o.getName() );
    }

    public void setLeft( Integer left )
    {
        this.left = left;
    }

    public Integer getLeft()
    {
        return left;
    }

    public void setTop( Integer top )
    {
        this.top = top;
    }

    public Integer getTop()
    {
        return top;
    }

    public void setWidth( Integer width )
    {
        this.width = width;
    }

    public Integer getWidth()
    {
        return width;
    }

    public void setHeight( Integer height )
    {
        this.height = height;
    }

    public Integer getHeight()
    {
        return height;
    }

    public AnotoPageFieldDTO toDTO()
    {
        AnotoPageFieldDTO dto = new AnotoPageFieldDTO( getAnotoPage().toDTO(), getName(), getType().toDTO() );
        dto.setHeight( getHeight() );
        dto.setIcr( hasIcr() );
        dto.setLeft( getLeft() );
        dto.setTop( getTop() );
        dto.setWidth( getWidth() );
        dto.setExport( getExport() );
        dto.setSearchable( getSearchable() );
        dto.setSequence( getSequence() );
        dto.setPk( getPk() );
        return dto;
    }

    public void setExport( Boolean export )
    {
        this.export = export;
    }

    public Boolean getExport()
    {
        return export;
    }

    public void setSearchable( Boolean searchable )
    {
        this.searchable = searchable;
    }

    public Boolean getSearchable()
    {
        return searchable;
    }

    public void setSequence( Integer sequence )
    {
        this.sequence = sequence;
    }

    public Integer getSequence()
    {
        return sequence;
    }

    public void setPk( Boolean pk )
    {
        this.pk = pk;
    }

    public Boolean getPk()
    {
        return pk;
    }
}
