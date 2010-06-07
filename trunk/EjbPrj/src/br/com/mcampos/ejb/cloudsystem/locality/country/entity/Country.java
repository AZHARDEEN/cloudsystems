package br.com.mcampos.ejb.cloudsystem.locality.country.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


@Table( name = "country" )
public class Country implements Serializable
{
    public static final String getAll = "Country.findAll";

    @Column( name = "ctr_code3_ch" )
    private String code3;

    @Id
    @Column( name = "ctr_code_ch", nullable = false )
    private String id;

    @Column( name = "ctr_num_code_in" )
    private Integer numericCode;


    public Country()
    {
        super();
    }


    public String getCode3()
    {
        return code3;
    }

    public void setCode3( String ctr_code3_ch )
    {
        this.code3 = ctr_code3_ch;
    }

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public Integer getNumericCode()
    {
        return numericCode;
    }

    public void setNumericCode( Integer ctr_num_code_in )
    {
        this.numericCode = ctr_num_code_in;
    }
}
