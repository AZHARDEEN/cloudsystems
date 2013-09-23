package br.com.mcampos.ejb.cloudsystem.anoto.page;


import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.ejb.cloudsystem.EntityCopyInterface;
import br.com.mcampos.ejb.cloudsystem.anoto.pad.Pad;

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
    public static final String anotoPagesGetAllNamedQuery = "AnotoPage.findAll";
    public static final String padPagesGetAllNamedQuery = "AnotoPage.padFindAll";
    public static final String formPagesGetAllNamedQuery = "AnotoPage.formFindAll";
    public static final String pagesGetAddressesNamedQuery = "AnotoPage.pagesGetAddresses";

    @Id
    @Column( name = "frm_id_in", nullable = false, insertable = false, updatable = false )
    private Integer formId;

    @Id
    @Column( name = "pad_id_in", nullable = false, insertable = false, updatable = false )
    private Integer padId;

    @Id
    @Column( name = "apg_id_ch", nullable = false )
    private String pageAddress;


    @ManyToOne( optional = false )
    @JoinColumns( { @JoinColumn( name = "frm_id_in", referencedColumnName = "frm_id_in", columnDefinition = "Integer" ),
                    @JoinColumn( name = "pad_id_in", referencedColumnName = "pad_id_in", columnDefinition = "Integer" ) } )
    private Pad pad;

    @Column( name = "apg_description_ch", nullable = true )
    private String description;

    @Column( name = "apg_icr_template_ch", nullable = true )
    private String icrTemplate;


    public AnotoPage()
    {
    }

    public AnotoPage( Pad pad, String address )
    {
        setPad( pad );
        setPageAddress( address );
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

    public void setPad( Pad pad )
    {
        this.pad = pad;
        if ( pad != null ) {
            setFormId( pad.getFormId() );
            setPadId( pad.getId() );
        }
    }

    public Pad getPad()
    {
        return pad;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public AnotoPageDTO toDTO()
    {
        AnotoPageDTO dto = new AnotoPageDTO( getPad().toDTO(), getPageAddress() );
        dto.setDescription( getDescription() );
        dto.setIcrTemplate( getIcrTemplate() );
        return dto;
    }

    public int compareTo( AnotoPage o )
    {
        int nRet;

        nRet = getPageAddress().compareTo( o.getPageAddress() );
        if ( nRet != 0 )
            return nRet;
        return getPad().compareTo( o.getPad() );
    }

    public void setIcrTemplate( String icrTemplate )
    {
        this.icrTemplate = icrTemplate;
    }

    public String getIcrTemplate()
    {
        return icrTemplate;
    }
}
