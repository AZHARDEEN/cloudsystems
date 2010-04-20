package br.com.mcampos.dto.anoto;

public class AnotoPageFieldDTO
{
    private Boolean icr;
    private AnotoPageDTO page;
    private String name;

    public AnotoPageFieldDTO()
    {
        super();
    }


    public AnotoPageFieldDTO( AnotoPageDTO page, String name )
    {
        super();
        setPage( page );
        setName( name );
    }


    public void setIcr( Boolean icr )
    {
        this.icr = icr;
    }

    public Boolean getIcr()
    {
        return icr;
    }

    public void setPage( AnotoPageDTO page )
    {
        this.page = page;
    }

    public AnotoPageDTO getPage()
    {
        return page;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
