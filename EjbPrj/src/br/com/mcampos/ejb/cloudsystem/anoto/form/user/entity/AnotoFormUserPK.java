package br.com.mcampos.ejb.cloudsystem.anoto.form.user.entity;

import java.io.Serializable;

public class AnotoFormUserPK implements Serializable
{
    private Integer sequence;
    private Integer formId;

    public AnotoFormUserPK()
    {
    }

    public AnotoFormUserPK( Integer afu_seq_in, Integer frm_id_in )
    {
        setSequence( afu_seq_in );
        setFormId( frm_id_in );
    }

    public boolean equals( Object other )
    {
        if ( other instanceof AnotoFormUserPK ) {
            final AnotoFormUserPK otherAnotoFormUserPK = ( AnotoFormUserPK )other;
            final boolean areEqual =
                ( otherAnotoFormUserPK.sequence.equals( sequence ) && otherAnotoFormUserPK.formId.equals( formId ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getSequence()
    {
        return sequence;
    }

    void setSequence( Integer afu_seq_in )
    {
        this.sequence = afu_seq_in;
    }

    Integer getFormId()
    {
        return formId;
    }

    void setFormId( Integer frm_id_in )
    {
        this.formId = frm_id_in;
    }
}
