package br.com.mcampos.ejb.cloudsystem.user.title;


import br.com.mcampos.dto.user.attributes.TitleDTO;
import br.com.mcampos.ejb.entity.address.Country;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = Title.getAll, query = "select o from Title o" ),
				 @NamedQuery( name = Title.getNextId, query = "select max(o.id) from Title o where o.country = ?1" ) } )
@Table( name = "title" )
@IdClass( TitlePK.class )
public class Title implements Serializable, EntityCopyInterface<TitleDTO>
{
	public static final String getAll = "Title.findAll";
	public static final String getNextId = "Title.nextId";


	@Id
	@Column( name = "ctr_code_ch", nullable = false, insertable = false, updatable = false )
	private String countryId;

	@Column( name = "ttl_abrev_ch", length = 12 )
	private String abbreviation;

	@Column( name = "ttl_description_ch", nullable = false, length = 32 )
	private String description;

	@Id
	@Column( name = "ttl_id_in", nullable = false )
	private Integer id;

	@ManyToOne
	@JoinColumn( name = "ctr_code_ch", nullable = false )
	private Country country;


	public Title()
	{
	}

	public String getCountryId()
	{
		return countryId;
	}

	public void setCountryId( String ctr_code_ch )
	{
		this.countryId = ctr_code_ch;
	}

	public String getAbbreviation()
	{
		return abbreviation;
	}

	public void setAbbreviation( String ttl_abrev_ch )
	{
		this.abbreviation = ttl_abrev_ch;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription( String ttl_description_ch )
	{
		this.description = ttl_description_ch;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId( Integer ttl_id_in )
	{
		this.id = ttl_id_in;
	}

	public TitleDTO toDTO()
	{
		TitleDTO dto = new TitleDTO();

		dto.setAbbreviation( getAbbreviation() );
		dto.setCountryId( getCountryId() );
		dto.setDescription( getDescription() );
		return dto;
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
