package br.com.mcampos.ejb.params;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.BasicEntityRenderer;


@Entity
@NamedQueries( { @NamedQuery( name = "SystemParameters.findAll", query = "select o from SystemParameters o" ) } )
@Table( name = "\"system_parameters\"" )
public class SystemParameters implements BasicEntityRenderer<SystemParameters>, Comparable<SystemParameters>
{
	private static final long serialVersionUID = 4036198021227614088L;
	public static final String passwordValidDays = "PasswordValidDays";
	public static final String maxLoginTryCount = "MaxLoginTryCount";
	public static final String encprytPassword = "encprytPassword";

	@Id
	@Column( name = "spr_id_ch", nullable = false )
	private String id;

	@Column( name = "spr_description_ch", nullable = false )
	private String description;

	@Column( name = "spr_value_tx", nullable = false )
	private String value;

	public SystemParameters()
	{
	}


	public String getDescription()
	{
		return this.description;
	}

	public void setDescription( String spr_description_ch )
	{
		this.description = spr_description_ch;
	}

	public String getId()
	{
		return this.id;
	}

	public void setId( String spr_id_ch )
	{
		this.id = spr_id_ch;
	}

	public String getValue()
	{
		return this.value;
	}

	public void setValue( String spr_value_tx )
	{
		this.value = spr_value_tx;
	}

	@Override
	public String getField( Integer field )
	{
		switch ( field ) {
		case 0:
			return getId();
		case 1:
			return ( getDescription() );
		case 2:
			return ( getValue() );
		default:
			return "";
		}
	}

	@Override
	public int compareTo( SystemParameters object, Integer field )
	{
		switch ( field ) {
		case 0:
			return compareTo( object );
		case 1:
			return object.getDescription().compareTo( getDescription() );
		case 2:
			return object.getValue().compareTo( getValue() );
		default:
			return 0;
		}
	}

	@Override
	public int compareTo( SystemParameters o )
	{
		return o.getId().compareTo( getId() );
	}
}
