package br.com.mcampos.dto.anoto;


import br.com.mcampos.dto.system.MediaDTO;

import java.io.Serializable;

import java.util.List;


public class PgcPenPageDTO implements Comparable<PgcPenPageDTO>, Serializable
{
    private String pageAddress;
    private Integer formId;
    private Integer padId;
    private String penId;
    private Integer pgcId;


    private AnotoPenPageDTO penPage;
    private PGCDTO pgc;

    private List<MediaDTO> backgroundImages;

    public PgcPenPageDTO()
    {
        super();
    }

    public PgcPenPageDTO( AnotoPenPageDTO penPage, PGCDTO pgc )
    {
        setPenPage( penPage );
        setPgc( pgc );
    }

    public int compareTo( PgcPenPageDTO o )
    {
        int nRet = pgc.compareTo( o.getPgc() );
        if ( nRet == 0 )
            nRet = penPage.compareTo( o.getPenPage() );
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

    public void setPgcId( Integer pgcId )
    {
        this.pgcId = pgcId;
    }

    public Integer getPgcId()
    {
        return pgcId;
    }

    public void setPenPage( AnotoPenPageDTO penPage )
    {
        this.penPage = penPage;
        if ( penPage != null ) {
            setFormId( penPage.getFormId() );
            setPadId( penPage.getPadId() );
            setPageAddress( penPage.getPageAddress() );
            setPenId( penPage.getPenId() );
        }
    }

    public AnotoPenPageDTO getPenPage()
    {
        return penPage;
    }

    public void setPgc( PGCDTO pgc )
    {
        this.pgc = pgc;
        if ( pgc != null )
            setPgcId( pgc.getId() );
    }

    public PGCDTO getPgc()
    {
        return pgc;
    }

    @Override
    public boolean equals( Object obj )
    {
        return getPgc().equals( ( ( PgcPenPageDTO )obj ).getPgc() ) &&
            getPenPage().equals( ( ( PgcPenPageDTO )obj ).getPenPage() );
    }

    public void setBackgroundImages( List<MediaDTO> backgroundImages )
    {
        this.backgroundImages = backgroundImages;
    }

    public List<MediaDTO> getBackgroundImages()
    {
        return backgroundImages;
    }

    public FormDTO getForm ()
    {
        return getPenPage().getPage().getPad().getForm();
    }
}
