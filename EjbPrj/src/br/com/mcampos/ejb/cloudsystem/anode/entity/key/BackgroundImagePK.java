package br.com.mcampos.ejb.cloudsystem.anode.entity.key;


import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;

import java.io.Serializable;

public class BackgroundImagePK implements Serializable
{
    private String pageAddress;
    private Integer formId;
    private Integer mediaId;
    private Integer padId;

    public BackgroundImagePK()
    {
    }

    public BackgroundImagePK( AnotoPage page, Media image )
    {
        init( page.getPageAddress(), page.getPadId(), page.getFormId(), image.getId() );
    }

    protected void init( String apg_id_ch, Integer frm_id_in, Integer med_id_in, Integer pad_id_in )
    {
        setPageAddress( apg_id_ch );
        setPadId( pad_id_in );
        setFormId( frm_id_in );
        setMediaId( med_id_in );
    }


    public BackgroundImagePK( String apg_id_ch, Integer frm_id_in, Integer med_id_in, Integer pad_id_in )
    {
        init( apg_id_ch, frm_id_in, med_id_in, pad_id_in );
    }

    public boolean equals( Object other )
    {
        if ( other instanceof BackgroundImagePK ) {
            final BackgroundImagePK otherBackgroundImagePK = ( BackgroundImagePK )other;
            final boolean areEqual = ( otherBackgroundImagePK.pageAddress.equals( pageAddress ) && otherBackgroundImagePK.formId.equals( formId ) && otherBackgroundImagePK.mediaId.equals( mediaId ) && otherBackgroundImagePK.padId.equals( padId ) );
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

    Integer getMediaId()
    {
        return mediaId;
    }

    void setMediaId( Integer med_id_in )
    {
        this.mediaId = med_id_in;
    }

    Integer getPadId()
    {
        return padId;
    }

    void setPadId( Integer pad_id_in )
    {
        this.padId = pad_id_in;
    }
}
