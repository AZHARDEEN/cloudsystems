package br.com.mcampos.ejb.cloudsystem.locality.city.entity;


import br.com.mcampos.ejb.cloudsystem.locality.state.entity.State;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = City.getAllByState,
                              query = "select o from City o where o.state = ?1 order by o.description " ) } )
@Table( name = "city" )
public class City implements Serializable
{

    public static final String getAllByState = "City.findByState";
    @Id
    @Column( name = "cit_id_in", nullable = false )
    private Integer id;
    @Column( name = "cit_name_ch", nullable = false )
    private String description;
    @Column( name = "cit_contry_capital_bt" )
    private Boolean countryCapital;
    @Column( name = "cit_state_capital_bt" )
    private Boolean stateCapital;
    @ManyToOne
    @JoinColumns( { @JoinColumn( name = "sta_id_in", referencedColumnName = "sta_id_in" ),
                    @JoinColumn( name = "reg_id_in", referencedColumnName = "reg_id_in" ),
                    @JoinColumn( name = "ctr_code_ch", referencedColumnName = "ctr_code_ch" ) } )
    private State state;

    public City()
    {
        super();
    }

    public Boolean isCountryCapital()
    {
        return countryCapital;
    }

    public void setCountryCapital( Boolean isCapital )
    {
        this.countryCapital = isCapital;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer cit_id_in )
    {
        this.id = cit_id_in;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String cit_name_ch )
    {
        this.description = cit_name_ch;
    }

    public Boolean isStateCapital()
    {
        return stateCapital;
    }

    public void setStateCapital( Boolean isCapital )
    {
        this.stateCapital = isCapital;
    }


    public State getState()
    {
        return state;
    }

    public void setState( State state )
    {
        this.state = state;
    }
}
