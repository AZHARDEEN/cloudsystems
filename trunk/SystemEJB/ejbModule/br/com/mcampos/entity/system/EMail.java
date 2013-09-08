package br.com.mcampos.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleTable;

/**
 * The persistent class for the e_mail database table.
 * 
 */
@Entity
@Table( name = "e_mail", schema = "public" )
public class EMail extends SimpleTable<EMail>
{
	private static final long serialVersionUID = 1L;

	public static final Integer templateValidationEmail = 1;
	public static final Integer templateEmailValidated = 2;
	public static final Integer templateNewPassword = 3;
	public static final Integer templatePasswordChanged = 4;

	@Id
	@Column( name = "eml_id_in" )
	private Integer id;

	@Column( name = "eml_description_ch" )
	private String description;

	public EMail( )
	{
	}

	@Override
	public Integer getId( )
	{
		return id;
	}

	@Override
	public void setId( Integer emlIdIn )
	{
		id = emlIdIn;
	}

	@Override
	public String getDescription( )
	{
		return description;
	}

	@Override
	public void setDescription( String emlDescriptionCh )
	{
		description = emlDescriptionCh;
	}

}