package br.com.mcampos.ejb.cloudsystem.anoto.pgcpenpage;

import java.io.Serializable;

public class PgcPenPagePK implements Serializable
{
    private String pageAddress;
    private Integer formId;
    private Integer padId;
    private Integer sequence;
    private Integer pgcId;

    public PgcPenPagePK()
    {
    }

    public PgcPenPagePK( String apg_id_ch, Integer frm_id_in, Integer pad_id_in, Integer sequence, Integer pgc_id_in )
    {
        this.pageAddress = apg_id_ch;
        this.formId = frm_id_in;
        this.padId = pad_id_in;
        this.sequence = sequence;
        this.pgcId = pgc_id_in;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof PgcPenPagePK ) {
            final PgcPenPagePK otherPgcPenPagePK = ( PgcPenPagePK )other;
            final boolean areEqual = ( otherPgcPenPagePK.pageAddress.equals( pageAddress ) && otherPgcPenPagePK.formId.equals( formId ) && otherPgcPenPagePK.padId.equals( padId ) && otherPgcPenPagePK.sequence.equals( sequence ) && otherPgcPenPagePK.pgcId.equals( pgcId ) );
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

    void setSequence( Integer sequence )
    {
        this.sequence = sequence;
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
