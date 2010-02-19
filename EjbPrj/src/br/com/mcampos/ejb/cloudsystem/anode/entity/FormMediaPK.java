package br.com.mcampos.ejb.cloudsystem.anode.entity;

import java.io.Serializable;

public class FormMediaPK implements Serializable
{
    private Integer form_id;
    private Integer media_id;

    public FormMediaPK()
    {
    }

    public FormMediaPK( Integer frm_id_in, Integer med_id_in )
    {
        this.form_id = frm_id_in;
        this.media_id = med_id_in;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof FormMediaPK ) {
            final FormMediaPK otherFormMediaPK = ( FormMediaPK )other;
            final boolean areEqual =
                ( otherFormMediaPK.form_id.equals( form_id ) && otherFormMediaPK.media_id.equals( media_id ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getForm_id()
    {
        return form_id;
    }

    void setForm_id( Integer frm_id_in )
    {
        this.form_id = frm_id_in;
    }

    Integer getMedia_id()
    {
        return media_id;
    }

    void setMedia_id( Integer med_id_in )
    {
        this.media_id = med_id_in;
    }
}
