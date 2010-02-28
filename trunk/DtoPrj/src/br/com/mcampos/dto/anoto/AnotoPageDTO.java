package br.com.mcampos.dto.anoto;

public class AnotoPageDTO
{
    private Integer formId;
    private Integer padId;
    private String pageAddress;
    private String description;

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
}
