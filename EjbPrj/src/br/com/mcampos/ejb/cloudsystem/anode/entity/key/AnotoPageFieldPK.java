package br.com.mcampos.ejb.cloudsystem.anode.entity.key;

import java.io.Serializable;

public class AnotoPageFieldPK implements Serializable
{
    private String name;
    private String pageAddress;
    private Integer formId;
    private Integer padId;

    public AnotoPageFieldPK()
    {
    }

    public AnotoPageFieldPK( String apf_name_ch, String apg_id_ch, Integer frm_id_in, Integer pad_id_in )
    {
        this.name = apf_name_ch;
        this.pageAddress = apg_id_ch;
        this.formId = frm_id_in;
        this.padId = pad_id_in;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof AnotoPageFieldPK ) {
            final AnotoPageFieldPK otherAnotoPageFieldPK = ( AnotoPageFieldPK )other;
            final boolean areEqual =
                ( otherAnotoPageFieldPK.name.equals( name ) && otherAnotoPageFieldPK.pageAddress.equals( pageAddress ) &&
                  otherAnotoPageFieldPK.formId.equals( formId ) && otherAnotoPageFieldPK.padId.equals( padId ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    String getName()
    {
        return name;
    }

    void setName( String apf_name_ch )
    {
        this.name = apf_name_ch;
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

    Integer getPadId()
    {
        return padId;
    }

    void setPadId( Integer pad_id_in )
    {
        this.padId = pad_id_in;
    }
}
