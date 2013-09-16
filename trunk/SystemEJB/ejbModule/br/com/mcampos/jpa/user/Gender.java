package br.com.mcampos.jpa.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.jpa.SimpleTable;

@Entity
@NamedQueries( { @NamedQuery( name = "Gender.findAll", query = "select o from Gender o" ) } )
@Table( name = "gender", schema = "public" )
public class Gender extends SimpleTable<Gender>
{
	private static final long serialVersionUID = 3372468873867948006L;
	@Column( name = "gnd_description_ch", nullable = false )
	private String description;
	@Id
	@Column( name = "gnd_id_in", nullable = false )
	private Integer id;

	@Override
	public String getDescription( )
	{
		return description;
	}

	@Override
	public void setDescription( String gnd_description_ch )
	{
		description = gnd_description_ch;
	}

	@Override
	public Integer getId( )
	{
		return id;
	}

	@Override
	public void setId( Integer gnd_id_in )
	{
		id = gnd_id_in;
	}
}
