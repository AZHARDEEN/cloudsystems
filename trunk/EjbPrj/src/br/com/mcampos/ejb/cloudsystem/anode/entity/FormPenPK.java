package br.com.mcampos.ejb.cloudsystem.anode.entity;

import java.io.Serializable;

public class FormPenPK implements Serializable
{
    private Integer formId;
    private String penId;

    public FormPenPK()
    {
    }

    public FormPenPK( Integer frm_id_in, String pen_id_in )
    {
        this.formId = frm_id_in;
        this.penId = pen_id_in;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof FormPenPK ) {
            final FormPenPK otherFormPenPK = ( FormPenPK )other;
            final boolean areEqual = ( otherFormPenPK.formId.equals( formId ) && otherFormPenPK.penId.equals( penId ) );
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

    String getPenId()
    {
        return penId;
    }

    void setPenId( String pen_id_in )
    {
        this.penId = pen_id_in;
    }
}
