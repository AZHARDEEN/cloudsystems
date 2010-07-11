package br.com.mcampos.ejb.cloudsystem.anoto.penpage.user.entity;

import java.io.Serializable;

public class PenUserPK implements Serializable
{

    private String pageId;
    private Integer sequence;
    private Integer formId;
    private Integer padId;
    private Integer penPageSequence;

    public PenUserPK()
    {
    }

    public boolean equals( Object other )
    {
        if ( other instanceof PenUserPK ) {
            final PenUserPK otherPenUserPK = ( PenUserPK )other;
            final boolean areEqual = ( otherPenUserPK.pageId.equals( pageId ) && otherPenUserPK.sequence.equals( sequence ) && otherPenUserPK.formId.equals( formId ) && otherPenUserPK.padId.equals( padId ) && otherPenUserPK.penPageSequence.equals( penPageSequence ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    String getPageId()
    {
        return pageId;
    }

    void setPageId( String apg_id_ch )
    {
        this.pageId = apg_id_ch;
    }

    Integer getSequence()
    {
        return sequence;
    }

    void setSequence( Integer apu_seq_in )
    {
        this.sequence = apu_seq_in;
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

    Integer getPenPageSequence()
    {
        return penPageSequence;
    }

    void setPenPageSequence( Integer pageSequence )
    {
        this.penPageSequence = pageSequence;
    }
}
