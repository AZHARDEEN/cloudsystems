package br.com.mcampos.ejb.cloudsystem.user.gender;


import br.com.mcampos.dto.user.attributes.GenderDTO;
import br.com.mcampos.ejb.cloudsystem.user.title.Title;
import br.com.mcampos.ejb.entity.address.Country;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;

import java.io.Serializable;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = Gender.getAll, query = "select o from Gender o" ),
				 @NamedQuery( name = Gender.getNextId, query = "select max(o.id) from Gender o" ) } )
@Table( name = "gender" )
@IdClass( GenderPK.class )
public class Gender implements Serializable, EntityCopyInterface<GenderDTO>
{
	public static final String getAll = "Gender.findAll";
	public static final String getNextId = "Gender.getNextId";

	@Id
	@Column( name = "gnd_id_in", nullable = false )
	protected Integer id;

	@Id
	@Column( name = "ctr_code_ch", nullable = false, insertable = false, updatable = false )
	private String countryId;

	@Column( name = "gnd_description_ch", nullable = false, length = 32 )
	protected String description;

	@ManyToMany( fetch = FetchType.EAGER )
	@JoinTable( name = "gender_title",
				joinColumns = { @JoinColumn( name = "gnd_id_in", referencedColumnName = "gnd_id_in", nullable = false ),
								@JoinColumn( name = "ctr_code_ch", referencedColumnName = "ctr_code_ch", nullable = false ) },
				inverseJoinColumns = { @JoinColumn( name = "ttl_id_in", referencedColumnName = "ttl_id_in", nullable = false ),
									   @JoinColumn( name = "ctr_code_ch", referencedColumnName = "ctr_code_ch",
													nullable = false ) } )
	protected List<Title> titles;

	@ManyToOne
	@JoinColumn( name = "ctr_code_ch", nullable = false )
	private Country country;


	public Gender()
	{
		super();
	}

	public Gender( Integer id )
	{
		super();
		setId( id );
	}


	protected void init( Integer id, String description )
	{
		setId( id );
		setDescription( description );
	}

	public Gender( Integer id, String description )
	{
		init( id, description );
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId( Integer id )
	{
		this.id = id;
	}


	public List<Title> getTitles()
	{
		return titles;
	}

	public void setTitles( List<Title> titles )
	{
		this.titles = titles;
	}

	public GenderDTO toDTO()
	{
		GenderDTO dto = new GenderDTO( getId(), getDescription() );
		return dto;
	}

	public void setCountryId( String countryId )
	{
		this.countryId = countryId;
	}

	public String getCountryId()
	{
		return countryId;
	}

	public void setCountry( Country country )
	{
		this.country = country;
	}

	public Country getCountry()
	{
		return country;
	}
}
