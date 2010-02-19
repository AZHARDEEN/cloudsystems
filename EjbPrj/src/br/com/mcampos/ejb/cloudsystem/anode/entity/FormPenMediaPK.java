package br.com.mcampos.ejb.cloudsystem.anode.entity;

import java.io.Serializable;

public class FormPenMediaPK implements Serializable
{
    private Integer form_id;
    private Integer media_id;
    private Integer pen_id;

    public FormPenMediaPK()
    {
    }

    public FormPenMediaPK( Integer frm_id_in, Integer med_id_in, Integer pen_id_in )
    {
        this.form_id = frm_id_in;
        this.media_id = med_id_in;
        this.pen_id = pen_id_in;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof FormPenMediaPK ) {
            final FormPenMediaPK otherFormPenMediaPK = ( FormPenMediaPK )other;
            final boolean areEqual =
                ( otherFormPenMediaPK.form_id.equals( form_id ) && otherFormPenMediaPK.media_id.equals( media_id ) &&
                  otherFormPenMediaPK.pen_id.equals( pen_id ) );
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

    Integer getPen_id()
    {
        return pen_id;
    }

    void setPen_id( Integer pen_id_in )
    {
        this.pen_id = pen_id_in;
    }
}
