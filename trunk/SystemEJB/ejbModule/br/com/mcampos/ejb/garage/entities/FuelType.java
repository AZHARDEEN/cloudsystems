package br.com.mcampos.ejb.garage.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleTable;

/**
 * The persistent class for the fuel_type database table.
 * 
 */
@Entity
@Table( name = "fuel_type", schema = "garage" )
public class FuelType extends SimpleTable<FuelType> implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "ftp_id_in" )
	private Integer id;

	@Column( name = "ftp_description_ch" )
	private String description;

	public FuelType( )
	{
	}

	@Override
	public Integer getId( )
	{
		return id;
	}

	@Override
	public void setId( Integer ftpIdIn )
	{
		id = ftpIdIn;
	}

	@Override
	public String getDescription( )
	{
		return description;
	}

	@Override
	public void setDescription( String ftpDescriptionCh )
	{
		description = ftpDescriptionCh;
	}
}