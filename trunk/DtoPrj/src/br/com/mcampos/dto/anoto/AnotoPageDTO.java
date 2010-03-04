package br.com.mcampos.dto.anoto;


import br.com.mcampos.sysutils.SysUtils;

import java.io.Serializable;

import java.util.List;


public class AnotoPageDTO implements Comparable<AnotoPageDTO>, Serializable
{
    private Integer formId;
    private Integer padId;
    private String pageAddress;
    private String description;

    private List<AnotoPenPageDTO> penPages;

    public AnotoPageDTO()
    {
        super();
    }

    public AnotoPageDTO( PadDTO pad, String address )
    {
        setFormId( pad.getFormId() );
        setPadId( pad.getId() );
        setPageAddress( address );
    }


    public void setPageAddress( String pageAddress )
    {
        this.pageAddress = pageAddress;
    }

    public String getPageAddress()
    {
        return pageAddress;
    }

    public void setFormId( Integer formId )
    {
        this.formId = formId;
    }

    public Integer getFormId()
    {
        return formId;
    }

    public void setPadId( Integer padId )
    {
        this.padId = padId;
    }

    public Integer getPadId()
    {
        return padId;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public int compareTo( AnotoPageDTO o )
    {
        int nRet;
        nRet = getPageAddress().compareTo( o.getPageAddress() );
        if ( nRet != 0 )
            return nRet;
        nRet = getFormId().compareTo( o.getFormId() );
        if ( nRet != 0 )
            return nRet;
        return getPadId().compareTo( o.getPadId() );
    }

    @Override
    public boolean equals( Object obj )
    {
        AnotoPageDTO page = ( AnotoPageDTO )obj;

        return getPageAddress().equals( page.getPageAddress() ) && getFormId().equals( page.getFormId() ) && getPadId()
            .equals( page.getPadId() );
    }

    @Override
    public String toString()
    {
        if ( SysUtils.isEmpty( getDescription() ) )
            return getPageAddress();
        else
            return getPageAddress() + " - " + getDescription();
    }

    public void setPenPages( List<AnotoPenPageDTO> penPages )
    {
        this.penPages = penPages;
    }

    public List<AnotoPenPageDTO> getPenPages()
    {
        return penPages;
    }
}
