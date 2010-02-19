package br.com.mcampos.ejb.cloudsystem.anode.entity;

import java.io.Serializable;

public class FormPenPK implements Serializable
{
    private Integer form_id;
    private Integer pen_id;

    public FormPenPK()
    {
    }

    public FormPenPK( Integer frm_id_in, Integer pen_id_in )
    {
        this.form_id = frm_id_in;
        this.pen_id = pen_id_in;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof FormPenPK ) {
            final FormPenPK otherFormPenPK = ( FormPenPK )other;
            final boolean areEqual = ( otherFormPenPK.form_id.equals( form_id ) && otherFormPenPK.pen_id.equals( pen_id ) );
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

    Integer getPen_id()
    {
        return pen_id;
    }

    void setPen_id( Integer pen_id_in )
    {
        this.pen_id = pen_id_in;
    }
}
