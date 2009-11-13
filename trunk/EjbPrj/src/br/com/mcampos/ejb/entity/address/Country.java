package br.com.mcampos.ejb.entity.address;

import java.io.Serializable;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "Country.findAll", query = "select o from Country o")
})
@Table( name = "\"country\"" )
public class Country implements Serializable
{
    private String code3;
    private String code;
    private Integer id;
    private Integer numericCode;
    private List<Region> regionList;

    public Country()
    {
        super ();
    }

    public Country( Integer id, String code3, String code, Integer numericCode )
    {
        super ();
        init ( id, code3, code, numericCode );
    }

    public Country( Integer id )
    {
        super();
        this.id = id;
    }


    protected void init ( Integer id, String code3, String code, Integer numericCode )
    {
        this.code3 = code3;
        this.code = code;
        this.id = id;
        this.numericCode = numericCode;
    }

    @Column( name="ctr_code3_ch" )
    public String getCode3()
    {
        return code3;
    }

    public void setCode3( String ctr_code3_ch )
    {
        this.code3 = ctr_code3_ch;
    }

    @Column( name="ctr_code_ch", nullable = false )
    public String getCode()
    {
        return code;
    }

    public void setCode( String ctr_code_ch )
    {
        this.code = ctr_code_ch;
    }

    @Id
    @Column( name="ctr_id_in", nullable = false )
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer ctr_id_in )
    {
        this.id = ctr_id_in;
    }

    @Column( name="ctr_num_code_in" )
    public Integer getNumericCode()
    {
        return numericCode;
    }

    public void setNumericCode( Integer ctr_num_code_in )
    {
        this.numericCode = ctr_num_code_in;
    }

    @OneToMany( mappedBy = "country" )
    public List<Region> getRegionList()
    {
        return regionList;
    }

    public void setRegionList( List<Region> regionList )
    {
        this.regionList = regionList;
    }

    public Region addRegion( Region region )
    {
        getRegionList().add(region);
        region.setCountry(this);
        return region;
    }

    public Region removeRegion( Region region )
    {
        getRegionList().remove(region);
        region.setCountry(null);
        return region;
    }
}
