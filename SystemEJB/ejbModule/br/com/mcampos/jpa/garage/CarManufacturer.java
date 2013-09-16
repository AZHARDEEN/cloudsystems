package br.com.mcampos.jpa.garage;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.jpa.SimpleTable;

/**
 * The persistent class for the car_manufacturer database table.
 * 
 */
@Entity
@Table( name = "car_manufacturer", schema = "garage" )
public class CarManufacturer extends SimpleTable<CarManufacturer> implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "crm_id_in" )
	private Integer id;

	@Column( name = "crm_description_ch" )
	private String description;

	public CarManufacturer( )
	{
	}

	@Override
	public Integer getId( )
	{
		return id;
	}

	@Override
	public void setId( Integer crmIdIn )
	{
		id = crmIdIn;
	}

	@Override
	public String getDescription( )
	{
		return description;
	}

	@Override
	public void setDescription( String crmDescriptionCh )
	{
		description = crmDescriptionCh;
	}

}