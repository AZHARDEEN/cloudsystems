package br.com.mcampos.ejb.cloudsystem.anode.entity;


import br.com.mcampos.ejb.cloudsystem.anode.entity.key.AnotoPageFieldPK;
import br.com.mcampos.ejb.cloudsystem.system.entity.FieldType;

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
@NamedQueries( { @NamedQuery( name = "AnotoPageField.findAll", query = "select o from AnotoPageField o" ) } )
@Table( name = "anoto_page_field" )
@IdClass( AnotoPageFieldPK.class )
public class AnotoPageField implements Serializable, Comparable<AnotoPageField>
{

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


    @ManyToOne
    @JoinColumn( name = "flt_id_in", referencedColumnName = "flt_id_in" )
    private FieldType type;

    @ManyToOne
    @JoinColumns( { @JoinColumn( name = "frm_id_in", referencedColumnName = "frm_id_in" ),
                    @JoinColumn( name = "pad_id_in", referencedColumnName = "pad_id_in" ),
                    @JoinColumn( name = "apg_id_ch", referencedColumnName = "apg_id_ch" ) } )
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
}
