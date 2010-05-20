package br.com.mcampos.ejb.cloudsystem.anoto.form.media;


import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;

import java.io.Serializable;

public class FormMediaPK implements Serializable
{
    private Integer formId;
    private Integer mediaId;

    public FormMediaPK()
    {
    }

    public FormMediaPK( Integer frm_id_in, Integer med_id_in )
    {
        init( frm_id_in, med_id_in );
    }

    public FormMediaPK( AnotoForm form, Media media )
    {
        init( form.getId(), media.getId() );
    }

    protected void init( Integer frm_id_in, Integer med_id_in )
    {
        this.formId = frm_id_in;
        this.mediaId = med_id_in;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof FormMediaPK ) {
            final FormMediaPK otherFormMediaPK = ( FormMediaPK )other;
            final boolean areEqual = ( otherFormMediaPK.formId.equals( formId ) && otherFormMediaPK.mediaId.equals( mediaId ) );
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
}
