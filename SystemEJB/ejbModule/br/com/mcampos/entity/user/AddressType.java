package br.com.mcampos.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleTable;

@Entity
@NamedQueries( { @NamedQuery( name = "AddressType.findAll", query = "select o from AddressType o" ) } )
@Table( name = "address_type", schema = "public" )
public class AddressType extends SimpleTable<AddressType>
{
	private static final long serialVersionUID = 1596629990429494444L;

	@Column( name = "adt_description_ch", nullable = false )
	private String description;

	@Id
	@Column( name = "adt_id_in", nullable = false )
	private Integer id;

	public AddressType( )
	{
	}

	@Override
	public String getDescription( )
	{
		return description;
	}

	@Override
	public void setDescription( String adt_description_ch )
	{
		description = adt_description_ch;
	}

	@Override
	public Integer getId( )
	{
		return id;
	}

	@Override
	public void setId( Integer adt_id_in )
	{
		id = adt_id_in;
	}
}
