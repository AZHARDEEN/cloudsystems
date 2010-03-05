package br.com.mcampos.dto.anoto;

import java.io.Serializable;

import java.util.List;


public class AnotoPenPageDTO implements Comparable<AnotoPenPageDTO>, Serializable
{
    private String pageAddress;
    private Integer formId;
    private Integer padId;
    private String penId;

    private PenDTO pen;
    private AnotoPageDTO page;

    private List<PgcPenPageDTO> pgcPenPages;


    public AnotoPenPageDTO()
    {
        super();
    }

    public AnotoPenPageDTO( PenDTO pen, AnotoPageDTO page )
    {
        setPen( pen );
        setPage( page );
    }

    public int compareTo( AnotoPenPageDTO o )
    {
        int nRet = getPen().compareTo( o.getPen() );
        if ( nRet == 0 )
            return getPage().compareTo( o.getPage() );
        return nRet;
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

    public void setPenId( String penId )
    {
        this.penId = penId;
    }

    public String getPenId()
    {
        return penId;
    }

    public void setPen( PenDTO pen )
    {
        this.pen = pen;
        if ( pen != null )
            setPenId( pen.getId() );
    }

    public PenDTO getPen()
    {
        return pen;
    }

    public void setPage( AnotoPageDTO page )
    {
        this.page = page;
        if ( page != null ) {
            setPadId( page.getPadId() );
            setFormId( page.getFormId() );
            setPageAddress( page.getPageAddress() );
        }
    }

    public AnotoPageDTO getPage()
    {
        return page;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj == null || ( obj instanceof AnotoPenPageDTO ) == false )
            return false;
        return getPen().equals( ( ( AnotoPenPageDTO )obj ).getPen() ) && getPage().equals( ( ( AnotoPenPageDTO )obj ).getPage() );
    }

    public void setPgcPenPages( List<PgcPenPageDTO> pgcPenPages )
    {
        this.pgcPenPages = pgcPenPages;
    }

    public List<PgcPenPageDTO> getPgcPenPages()
    {
        return pgcPenPages;
    }
}
