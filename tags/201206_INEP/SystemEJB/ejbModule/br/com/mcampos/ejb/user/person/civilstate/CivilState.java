package br.com.mcampos.ejb.user.person.civilstate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleTable;

@Entity
@NamedQueries( { @NamedQuery( name = "CivilState.findAll", query = "select o from CivilState o" ) } )
@Table( name = "\"civil_state\"" )
public class CivilState extends SimpleTable<CivilState>
{
	private static final long serialVersionUID = -448962307975236161L;

	@Column( name = "cst_description_ch", nullable = false )
	private String description;

	@Id
	@Column( name = "cst_id_in", nullable = false, unique = true )
	private Integer id;

	@Override
	public String getDescription( )
	{
		return this.description;
	}

	@Override
	public void setDescription( String cst_description_ch )
	{
		this.description = cst_description_ch;
	}

	@Override
	public Integer getId( )
	{
		return this.id;
	}

	@Override
	public void setId( Integer cst_id_in )
	{
		this.id = cst_id_in;
	}

	@Override
	public String toString( )
	{
		return getDescription( );
	}
}
