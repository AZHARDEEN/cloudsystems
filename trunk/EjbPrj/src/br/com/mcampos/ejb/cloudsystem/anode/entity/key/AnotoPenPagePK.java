package br.com.mcampos.ejb.cloudsystem.anode.entity.key;

import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPen;

import java.io.Serializable;

public class AnotoPenPagePK implements Serializable
{
    private String pageAddress;
    private Integer formId;
    private Integer padId;
    private String penId;

    public AnotoPenPagePK()
    {
    }

    public AnotoPenPagePK( AnotoPen pen, AnotoPage page )
    {
        setPen( pen );
        setPage( page );
    }

    public void setPen( AnotoPen pen )
    {
        setPenId( pen.getId() );
    }

    public void setPage( AnotoPage page )
    {
        setFormId( page.getFormId() );
        setPadId( page.getPadId() );
        setPageAddress( page.getPageAddress() );
    }

    public AnotoPenPagePK( String apg_id_ch, Integer frm_id_in, Integer pad_id_in, String pen_id_ch )
    {
        this.pageAddress = apg_id_ch;
        this.formId = frm_id_in;
        this.padId = pad_id_in;
        this.penId = pen_id_ch;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof AnotoPenPagePK ) {
            final AnotoPenPagePK otherAnotoPenPagePK = ( AnotoPenPagePK )other;
            final boolean areEqual =
                ( otherAnotoPenPagePK.pageAddress.equals( pageAddress ) && otherAnotoPenPagePK.formId.equals( formId ) &&
                  otherAnotoPenPagePK.padId.equals( padId ) && otherAnotoPenPagePK.penId.equals( penId ) );
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

    String getPenId()
    {
        return penId;
    }

    void setPenId( String pen_id_ch )
    {
        this.penId = pen_id_ch;
    }
}
