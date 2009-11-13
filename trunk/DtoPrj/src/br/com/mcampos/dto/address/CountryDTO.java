package br.com.mcampos.dto.address;

import br.com.mcampos.dto.core.DisplayNameDTO;

import java.io.Serializable;

public class CountryDTO extends DisplayNameDTO 
{
    private String code3;
    private String code;
    private Integer id;
    private Integer numericCode;
    
    
    public CountryDTO()
    {
        super();
    }
    
    public void setCode3( String ctr_code3_ch )
    {
        this.code3 = ctr_code3_ch;
    }

    public String getCode3()
    {
        return code3;
    }

    public void setCode( String ctr_code_ch )
    {
        this.code = ctr_code_ch;
    }

    public String getCode()
    {
        return code;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setNumericCode( Integer ctr_num_code_in )
    {
        this.numericCode = ctr_num_code_in;
    }

    public Integer getNumericCode()
    {
        return numericCode;
    }


    public String getDisplayName()
    {
        return getCode3();
    }
}
