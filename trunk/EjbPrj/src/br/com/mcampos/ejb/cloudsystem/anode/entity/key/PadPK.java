package br.com.mcampos.ejb.cloudsystem.anode.entity.key;


import java.io.Serializable;

public class PadPK implements Serializable
{
    private Integer formId;
    private Integer id;

    public PadPK()
    {
    }

    public PadPK( Integer frm_id_in, Integer pad_id_in )
    {
        this.formId = frm_id_in;
        this.id = pad_id_in;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof PadPK ) {
            final PadPK otherPadPK = ( PadPK )other;
            final boolean areEqual = ( otherPadPK.formId.equals( formId ) && otherPadPK.id.equals( id ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getFormId()
    {
        return formId;
    }

    void setFormId( Integer frm_id_in )
    {
        this.formId = frm_id_in;
    }

    Integer getId()
    {
        return id;
    }

    void setId( Integer pad_id_in )
    {
        this.id = pad_id_in;
    }
}
