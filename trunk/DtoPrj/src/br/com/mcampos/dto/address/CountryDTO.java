package br.com.mcampos.dto.address;

import br.com.mcampos.dto.core.DisplayNameDTO;

public class CountryDTO extends DisplayNameDTO implements Comparable<CountryDTO>
{
    /**
     *
     */
    private static final long serialVersionUID = 8118570583075603388L;
    private String code3;
    private String id;
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

    public void setId( String ctr_code_ch )
    {
        this.id = ctr_code_ch;
    }

    public String getId()
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
        return getId();
    }


    @Override
    public String toString()
    {
        return getDisplayName();
    }

    public int compareTo( CountryDTO o )
    {
        return getId().compareTo( o.getId() );
    }
}
