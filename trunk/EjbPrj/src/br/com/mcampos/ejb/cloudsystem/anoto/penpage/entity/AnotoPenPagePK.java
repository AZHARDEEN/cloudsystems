package br.com.mcampos.ejb.cloudsystem.anoto.penpage.entity;


import br.com.mcampos.dto.anoto.AnotoPenPageDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPage;

import java.io.Serializable;

public class AnotoPenPagePK implements Serializable
{
    private String pageAddress;
    private Integer formId;
    private Integer padId;
    private Integer sequence;

    public AnotoPenPagePK()
    {
    }

    public AnotoPenPagePK( AnotoPage page )
    {
        setPage( page );
    }

    public AnotoPenPagePK( AnotoPenPageDTO dto )
    {
        setPageAddress( dto.getPageAddress() );
        setFormId( dto.getFormId() );
        setPadId( dto.getPadId() );
        setSequence( dto.getSequence() );
    }


    public void setPage( AnotoPage page )
    {
        setFormId( page.getFormId() );
        setPadId( page.getPadId() );
        setPageAddress( page.getPageAddress() );
    }

    public AnotoPenPagePK( String apg_id_ch, Integer frm_id_in, Integer pad_id_in, Integer seq )
    {
        this.pageAddress = apg_id_ch;
        this.formId = frm_id_in;
        this.padId = pad_id_in;
        this.sequence = seq;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof AnotoPenPagePK ) {
            final AnotoPenPagePK otherAnotoPenPagePK = ( AnotoPenPagePK )other;
            final boolean areEqual = ( otherAnotoPenPagePK.pageAddress.equals( pageAddress ) && otherAnotoPenPagePK.formId.equals( formId ) && otherAnotoPenPagePK.padId.equals( padId ) && otherAnotoPenPagePK.sequence.equals( sequence ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    String getPageAddress()
    {
        return pageAddress;
    }

    void setPageAddress( String apg_id_ch )
    {
        this.pageAddress = apg_id_ch;
    }

    Integer getFormId()
    {
        return formId;
    }

    void setFormId( Integer frm_id_in )
    {
        this.formId = frm_id_in;
    }

    Integer getPadId()
    {
        return padId;
    }

    void setPadId( Integer pad_id_in )
    {
        this.padId = pad_id_in;
    }

    Integer getSequence()
    {
        return sequence;
    }

    void setSequence( Integer seq )
    {
        this.sequence = seq;
    }
}
