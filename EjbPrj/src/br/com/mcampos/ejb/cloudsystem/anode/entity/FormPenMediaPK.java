package br.com.mcampos.ejb.cloudsystem.anode.entity;

import java.io.Serializable;

public class FormPenMediaPK implements Serializable
{
    private Integer formId;
    private Integer mediaId;
    private String penId;

    public FormPenMediaPK()
    {
    }

    public FormPenMediaPK( Integer frm_id_in, Integer med_id_in, String pen_id_in )
    {
        this.formId = frm_id_in;
        this.mediaId = med_id_in;
        this.penId = pen_id_in;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof FormPenMediaPK ) {
            final FormPenMediaPK otherFormPenMediaPK = ( FormPenMediaPK )other;
            final boolean areEqual = ( otherFormPenMediaPK.formId.equals( formId ) && otherFormPenMediaPK.mediaId.equals( mediaId ) && otherFormPenMediaPK.penId.equals( penId ) );
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

    Integer getMediaId()
    {
        return mediaId;
    }

    void setMediaId( Integer med_id_in )
    {
        this.mediaId = med_id_in;
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
