package br.com.mcampos.ejb.entity.address;

//~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

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
@NamedQueries( { @NamedQuery( name = "Region.findAll", query = "select o from Region o" ),
                 @NamedQuery( name = "Region.findByCountry", query = "select o from Region o where o.countryId = :countryId" ) } )
@Table( name = "\"region\"" )
@IdClass( RegionPK.class )
public class Region implements Serializable
{
    private String abbreviation;
    private Country country;
    private Integer countryId;
    private String flag;
    private Integer id;
    private String name;
    private List<State> stateList;

    public Region()
    {
    }

    public Region( Country country, String reg_abbreviation_ch, String reg_flag_bin, Integer reg_id_in, String reg_name_ch )
    {
        this.country = country;
        this.abbreviation = reg_abbreviation_ch;
        this.flag = reg_flag_bin;
        this.id = reg_id_in;
        this.name = reg_name_ch;
    }

    public Region( Country country, Integer id )
    {
        super();
        this.country = country;
        this.id = id;
    }

    @Id
    @Column( name = "ctr_id_in", nullable = false, insertable = false, updatable = false )
    public Integer getCountryId()
    {
        return countryId;
    }

    public void setCountryId( Integer ctr_id_in )
    {
        this.countryId = ctr_id_in;
    }

    @Column( name = "reg_abbreviation_ch", nullable = false )
    public String getAbbreviation()
    {
        return abbreviation;
    }

    public void setAbbreviation( String reg_abbreviation_ch )
    {
        this.abbreviation = reg_abbreviation_ch;
    }

    @Column( name = "reg_flag_bin" )
    public String getFlag()
    {
        return flag;
    }

    public void setFlag( String reg_flag_bin )
    {
        this.flag = reg_flag_bin;
    }

    @Id
    @Column( name = "reg_id_in", nullable = false )
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer reg_id_in )
    {
        this.id = reg_id_in;
    }

    @Column( name = "reg_name_ch", nullable = false )
    public String getName()
    {
        return name;
    }

    public void setName( String reg_name_ch )
    {
        this.name = reg_name_ch;
    }

    @OneToMany( mappedBy = "region" )
    public List<State> getStateList()
    {
        return stateList;
    }

    public void setStateList( List<State> stateList )
    {
        this.stateList = stateList;
    }

    public State addState( State state )
    {
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

    @ManyToOne
    @JoinColumn( name = "ctr_id_in" )
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
