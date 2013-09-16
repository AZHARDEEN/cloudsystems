package br.com.mcampos.ejb.user.person.gender;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleTable;


@Entity
@NamedQueries( { @NamedQuery( name = "Gender.findAll", query = "select o from Gender o" ) } )
@Table( name = "\"gender\"" )
public class Gender extends SimpleTable<Gender>
{
	private static final long serialVersionUID = 3372468873867948006L;
	@Column( name = "gnd_description_ch", nullable = false )
	private String description;
	@Id
	@Column( name = "gnd_id_in", nullable = false )
	private Integer id;


	@Override
	public String getDescription()
	{
		return this.description;
	}

	@Override
	public void setDescription( String gnd_description_ch )
	{
		this.description = gnd_description_ch;
	}

	@Override
	public Integer getId()
	{
		return this.id;
	}

	@Override
	public void setId( Integer gnd_id_in )
	{
		this.id = gnd_id_in;
	}
}