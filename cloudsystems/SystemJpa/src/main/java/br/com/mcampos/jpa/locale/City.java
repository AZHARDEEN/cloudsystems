package br.com.mcampos.jpa.locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.jpa.SimpleTable;

@Entity
@NamedQueries( { @NamedQuery( name = City.getAllByState, query = "select o from City o where o.state = ?1" ) } )
@Table( name = "city", schema = "public" )
public class City extends SimpleTable<City>
{
	private static final long serialVersionUID = 7598005142654813274L;

	public static final String getAllByState = "City.getAllByState";

	@Id
	@Column( name = "cit_id_in", nullable = false )
	private Integer id;

	@Column( name = "cit_contry_capital_bt" )
	private Boolean isCountryCapital;

	@Column( name = "cit_name_ch", nullable = false )
	private String description;

	@Column( name = "cit_state_capital_bt" )
	private Boolean isStateCapital;

	@ManyToOne
	@JoinColumns( { @JoinColumn( name = "ctr_code_ch", referencedColumnName = "ctr_code_ch" ),
			@JoinColumn( name = "reg_id_in", referencedColumnName = "reg_id_in" ),
			@JoinColumn( name = "sta_id_in", referencedColumnName = "sta_id_in" ) } )
	private State state;

	public City( )
	{
	}

	public Boolean getIsCountryCapital( )
	{
		return isCountryCapital;
	}

	public void setIsCountryCapital( Boolean capital )
	{
		isCountryCapital = capital;
	}

	@Override
	public Integer getId( )
	{
		return id;
	}

	@Override
	public void setId( Integer id )
	{
		this.id = id;
	}

	@Override
	public String getDescription( )
	{
		return description;
	}

	@Override
	public void setDescription( String value )
	{
		description = value;
	}

	public Boolean getIsStateCapital( )
	{
		return isStateCapital;
	}

	public void setIsStateCapital( Boolean capital )
	{
		isStateCapital = capital;
	}

	public State getState( )
	{
		return state;
	}

	public void setState( State state2 )
	{
		state = state2;
	}

	@Override
	public String toString( )
	{
		return getDescription( );
	}
}
