package br.com.mcampos.ejb.entity.address;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = "State.findAll", query = "select o from State o" ),
                 @NamedQuery( name = "State.findByCountry", query = "select o from State o where o.countryId = :countryId" ) } )
@Table( name = "state" )
@IdClass( StatePK.class )
public class State implements Serializable
{
    @Id
    @Column( name = "ctr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer countryId;
    @Id
    @Column( name = "reg_id_in", nullable = false, insertable = false, updatable = false )
    private Integer regionId;
    @Column( name = "sta_abbreviation_ch", nullable = false, length = 32 )
    private String abbreviation;
    @Id
    @Column( name = "sta_id_in", nullable = false )
    private Integer id;
    @Column( name = "sta_name_ch", nullable = false, length = 128 )
    private String name;
    @ManyToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumns( { @JoinColumn( name = "reg_id_in", referencedColumnName = "reg_id_in" ),
                    @JoinColumn( name = "ctr_id_in", referencedColumnName = "ctr_id_in" ) } )
    private Region region;

    public State()
    {
    }

    public State( Region region, Integer id, String abbreviation, String name )
    {
        this.region = region;
        this.abbreviation = abbreviation;
        this.id = id;
        this.name = name;
    }

    protected void init( Integer CountrId, Integer regionId, Integer id, String name, String abbreviation )
    {
        this.countryId = CountrId;
        this.regionId = regionId;
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public Integer getCountryId()
    {
        return countryId;
    }

    public void setCountryId( Integer ctr_id_in )
    {
        this.countryId = ctr_id_in;
    }

    public Integer getRegionId()
    {
        return regionId;
    }

    public void setRegionId( Integer reg_id_in )
    {
        this.regionId = reg_id_in;
    }

    public String getAbbreviation()
    {
        return abbreviation;
    }

    public void setAbbreviation( String sta_abbreviation_ch )
    {
        this.abbreviation = sta_abbreviation_ch;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer sta_id_in )
    {
        this.id = sta_id_in;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String sta_name_ch )
    {
        this.name = sta_name_ch;
    }

    public Region getRegion()
    {
        return region;
    }

    public void setRegion( Region region )
    {
        this.region = region;
        if ( region != null ) {
            this.countryId = region.getCountryId();
            this.regionId = region.getId();
        }
    }
}
