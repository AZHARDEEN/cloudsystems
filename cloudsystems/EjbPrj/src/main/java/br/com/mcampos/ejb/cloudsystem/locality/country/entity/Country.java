package br.com.mcampos.ejb.cloudsystem.locality.country.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.ejb.cloudsystem.locality.region.entity.Region;

@Entity
@NamedQueries( { @NamedQuery( name = Country.getAll, query = "select o from Country o" ),
		@NamedQuery( name = Country.getAllWithCities, query = "select o from Country o " ) } )
@Table( name = "country" )
public class Country implements Serializable
{
	public static final String getAll = "Country.findAll";
	public static final String getAllWithCities = "Country.findAllWithCities";
	@SuppressWarnings( "compatibility:-6565834169689137194" )
	private static final long serialVersionUID = 3346057474213180623L;

	@Column( name = "ctr_code3_ch" )
	private String code3;

	@Id
	@Column( name = "ctr_code_ch", nullable = false )
	private String id;

	@Column( name = "ctr_num_code_in" )
	private Integer numericCode;

	/*
	@OneToMany( mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY )
	private List<Region> regions;
	*/

	public Country( )
	{
		super( );
	}

	public String getCode3( )
	{
		return code3;
	}

	public void setCode3( String ctr_code3_ch )
	{
		code3 = ctr_code3_ch;
	}

	public String getId( )
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	public Integer getNumericCode( )
	{
		return numericCode;
	}

	public void setNumericCode( Integer ctr_num_code_in )
	{
		numericCode = ctr_num_code_in;
	}

	public void setRegions( List<Region> regions )
	{
		// this.regions = regions;
	}

	public List<Region> getRegions( )
	{
		// return regions;
		return null;
	}
}
