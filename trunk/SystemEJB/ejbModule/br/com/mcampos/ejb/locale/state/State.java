package br.com.mcampos.ejb.locale.state;

import javax.persistence.Basic;
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

import br.com.mcampos.ejb.core.SimpleTable;
import br.com.mcampos.ejb.locale.Region;

@Entity
@NamedQueries( { @NamedQuery(
		name = State.getCountryStates, query = "select o from State o where o.countryId = ?1 order by o.abbreviation" ) } )
@Table( name = "state" )
@IdClass( StatePK.class )
public class State extends SimpleTable<State>
{
	private static final long serialVersionUID = 1790623716239240147L;

	public static final String getCountryStates = "State.getCountryState";

	@Id
	@Column( name = "ctr_code_ch", nullable = false, insertable = false, updatable = false )
	private String countryId;

	@Id
	@Column( name = "reg_id_in", nullable = false, insertable = false, updatable = false )
	private Integer regionId;

	@Id
	@Column( name = "sta_id_in", nullable = false )
	private Integer id;

	@Column( name = "sta_abbreviation_ch" )
	private String abbreviation;

	@Column( name = "sta_flag_bin" )
	@Basic( fetch = FetchType.LAZY )
	private Byte[ ] flag;

	@Column( name = "sta_name_ch", nullable = false )
	private String description;

	@ManyToOne
	@JoinColumns( { @JoinColumn( name = "ctr_code_ch", referencedColumnName = "ctr_code_ch" ),
			@JoinColumn( name = "reg_id_in", referencedColumnName = "reg_id_in" ) } )
	private Region region;

	public State( )
	{
	}

	public String getCountryId( )
	{
		return this.countryId;
	}

	public void setCountryId( String ctr_code_ch )
	{
		this.countryId = ctr_code_ch;
	}

	public Integer getRegionId( )
	{
		return this.regionId;
	}

	public void setRegionId( Integer reg_id_in )
	{
		this.regionId = reg_id_in;
	}

	public String getAbbreviation( )
	{
		return this.abbreviation;
	}

	public void setAbbreviation( String sta_abbreviation_ch )
	{
		this.abbreviation = sta_abbreviation_ch;
	}

	public Byte[ ] getFlag( )
	{
		return this.flag;
	}

	public void setFlag( Byte[ ] sta_flag_bin )
	{
		this.flag = sta_flag_bin;
	}

	@Override
	public Integer getId( )
	{
		return this.id;
	}

	@Override
	public void setId( Integer sta_id_in )
	{
		this.id = sta_id_in;
	}

	@Override
	public String getDescription( )
	{
		return this.description;
	}

	@Override
	public void setDescription( String sta_name_ch )
	{
		this.description = sta_name_ch;
	}

	public Region getRegion( )
	{
		return this.region;
	}

	public void setRegion( Region region )
	{
		this.region = region;
		if ( region != null ) {
			this.regionId = region.getId( );
			this.countryId = region.getCountryId( );
		}
	}

	@Override
	public String toString( )
	{
		return getAbbreviation( ) + "-" + getDescription( );
	}

}
