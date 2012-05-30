package br.com.mcampos.ejb.cloudsystem.locality.region.entity;

//~--- JDK imports ------------------------------------------------------------


import br.com.mcampos.ejb.cloudsystem.locality.country.entity.Country;
import br.com.mcampos.ejb.cloudsystem.locality.state.entity.State;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = Region.getAll, query = "select o from Region o where o.country = ?1" ),
                 @NamedQuery( name = Region.nextId, query = "select max ( o.id ) from Region o where o.country = ?1" ) } )
@Table( name = "region" )
@IdClass( RegionPK.class )
public class Region implements Serializable
{
    public static final String getAll = "Region.findAll";
    public static final String nextId = "Region.nextId";

    @Id
    @Column( name = "ctr_code_ch", nullable = false, insertable = false, updatable = false )
    private String countryId;

    @ManyToOne( optional = false )
    @JoinColumn( name = "ctr_code_ch", nullable = false, insertable = true, updatable = true )
    private Country country;

    @Id
    @Column( name = "reg_id_in", nullable = false )
    private Integer id;

    @Column( name = "reg_abbreviation_ch", nullable = false )
    private String abbreviation;

    @Column( name = "reg_flag_bin" )
    private String flag;

    @Column( name = "reg_name_ch", nullable = false )
    private String description;

    @OneToMany( mappedBy = "region" )
    private List<State> stateList;


    public Region()
    {
    }


    public Region( Country country, Integer id )
    {
        super();
        this.country = country;
        this.id = id;
    }

    public String getCountryId()
    {
        return countryId;
    }

    public void setCountryId( String countryId )
    {
        this.countryId = countryId;
    }

    public String getAbbreviation()
    {
        return abbreviation;
    }

    public void setAbbreviation( String reg_abbreviation_ch )
    {
        this.abbreviation = reg_abbreviation_ch;
    }

    public String getFlag()
    {
        return flag;
    }

    public void setFlag( String reg_flag_bin )
    {
        this.flag = reg_flag_bin;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer reg_id_in )
    {
        this.id = reg_id_in;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String reg_name_ch )
    {
        this.description = reg_name_ch;
    }

    public List<State> getStateList()
    {
        if ( stateList == null )
            stateList = new ArrayList<State>();
        return stateList;
    }

    public void setStateList( List<State> stateList )
    {
        this.stateList = stateList;
    }

    public State addState( State state )
    {
        if ( getStateList().contains( state ) == false )
            getStateList().add( state );
        state.setRegion( this );
        return state;
    }

    public State removeState( State state )
    {
        getStateList().remove( state );
        state.setRegion( null );
        return state;
    }

    public Country getCountry()
    {
        return country;
    }

    public void setCountry( Country ctr )
    {
        this.country = ctr;

        if ( ctr != null ) {
            setCountryId( ctr.getId() );
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
