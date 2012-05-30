package br.com.mcampos.ejb.locale;

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
@NamedQueries( { @NamedQuery( name = "City.findAll", query = "select o from City o" ) } )
@Table( name = "\"city\"" )
public class City implements Serializable
{
	private static final long serialVersionUID = 7598005142654813274L;

	@Id
	@Column( name = "cit_id_in", nullable = false )
	private Integer id;


	@Column( name = "cit_contry_capital_bt" )
	private Boolean isCountryCapital;


	@Column( name = "cit_name_ch", nullable = false )
	private String name;

	@Column( name = "cit_state_capital_bt" )
	private Boolean isStateCapital;

	@ManyToOne
	@JoinColumns( { @JoinColumn( name = "ctr_code_ch", referencedColumnName = "ctr_code_ch" ),
		@JoinColumn( name = "reg_id_in", referencedColumnName = "reg_id_in" ),
		@JoinColumn( name = "sta_id_in", referencedColumnName = "sta_id_in" ) } )
	private State state;

	public City()
	{
	}

	public Boolean getIsCountryCapital()
	{
		return this.isCountryCapital;
	}

	public void setIsCountryCapital( Boolean cit_contry_capital_bt )
	{
		this.isCountryCapital = cit_contry_capital_bt;
	}

	public Integer getId()
	{
		return this.id;
	}

	public void setId( Integer cit_id_in )
	{
		this.id = cit_id_in;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName( String cit_name_ch )
	{
		this.name = cit_name_ch;
	}

	public Boolean getIsStateCapital()
	{
		return this.isStateCapital;
	}

	public void setIsStateCapital( Boolean cit_state_capital_bt )
	{
		this.isStateCapital = cit_state_capital_bt;
	}


	public State getState()
	{
		return this.state;
	}

	public void setState( State state2 )
	{
		this.state = state2;
	}
}
