package br.com.mcampos.jpa.locale;

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

import br.com.mcampos.jpa.SimpleTable;

@Entity
@NamedQueries( { @NamedQuery(
		name = State.getCountryStates, query = "select o from State o where o.countryId = ?1 order by o.abbreviation" ) } )
@Table( name = "state", schema = "public" )
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
		return countryId;
	}

	public void setCountryId( String code )
	{
		countryId = code;
	}

	public Integer getRegionId( )
	{
		return regionId;
	}

	public void setRegionId( Integer region )
	{
		regionId = region;
	}

	public String getAbbreviation( )
	{
		return abbreviation;
	}

	public void setAbbreviation( String value )
	{
		abbreviation = value;
	}

	public Byte[ ] getFlag( )
	{
		return flag;
	}

	public void setFlag( Byte[ ] bin )
	{
		flag = bin.clone( );
	}

	@Override
	public Integer getId( )
	{
		return id;
	}

	@Override
	public void setId( Integer state )
	{
		id = state;
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

	public Region getRegion( )
	{
		return region;
	}

	public void setRegion( Region region )
	{
		this.region = region;
		if ( region != null ) {
			regionId = region.getId( );
			countryId = region.getCountryId( );
		}
	}

	@Override
	public String toString( )
	{
		return getAbbreviation( ) + "-" + getDescription( );
	}

}
