package br.com.mcampos.ejb.cloudsystem.anoto.pgcpenpage;

import java.io.Serializable;

public class PgcPenPagePK implements Serializable
{
    private String pageAddress;
    private Integer formId;
    private Integer padId;
    private String penId;
    private Integer pgcId;

    public PgcPenPagePK()
    {
    }

    public PgcPenPagePK( String apg_id_ch, Integer frm_id_in, Integer pad_id_in, String pen_id_ch, Integer pgc_id_in )
    {
        this.pageAddress = apg_id_ch;
        this.formId = frm_id_in;
        this.padId = pad_id_in;
        this.penId = pen_id_ch;
        this.pgcId = pgc_id_in;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof PgcPenPagePK ) {
            final PgcPenPagePK otherPgcPenPagePK = ( PgcPenPagePK )other;
            final boolean areEqual =
                ( otherPgcPenPagePK.pageAddress.equals( pageAddress ) && otherPgcPenPagePK.formId.equals( formId ) &&
                  otherPgcPenPagePK.padId.equals( padId ) && otherPgcPenPagePK.penId.equals( penId ) &&
                  otherPgcPenPagePK.pgcId.equals( pgcId ) );
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

    Integer getPgcId()
    {
        return pgcId;
    }

    void setPgcId( Integer pgc_id_in )
    {
        this.pgcId = pgc_id_in;
    }
}
