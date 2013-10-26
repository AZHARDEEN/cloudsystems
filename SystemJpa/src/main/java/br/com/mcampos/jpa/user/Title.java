package br.com.mcampos.jpa.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.jpa.SimpleTable;

@Entity
@NamedQueries( {
		@NamedQuery( name = "Title.findAll", query = "select o from Title o" ),
		@NamedQuery( name = "Title.getAllFromGender", query = "select o from Title o where o.gender = ?1 or o.gender is null" )
} )
@Table( name = "title", schema = "public" )
public class Title extends SimpleTable<Title>
{
	private static final long serialVersionUID = -5257827831148175822L;

	public static final String getAllFromGender = "Title.getAllFromGender";

	@Column( name = "ttl_abrev_ch" )
	private String abreviation;

	@Column( name = "ttl_description_ch", nullable = false )
	private String description;

	@Id
	@Column( name = "ttl_id_in", nullable = false, unique = true )
	private Integer id;

	@ManyToOne( optional = true )
	@JoinColumn( name = "gnd_id_in", referencedColumnName = "gnd_id_in", insertable = true, updatable = true, nullable = true )
	private Gender gender;

	public String getAbreviation( )
	{
		return abreviation;
	}

	public void setAbreviation( String ttl_abrev_ch )
	{
		abreviation = ttl_abrev_ch;
	}

	@Override
	public String getDescription( )
	{
		return description;
	}

	@Override
	public void setDescription( String ttl_description_ch )
	{
		description = ttl_description_ch;
	}

	@Override
	public Integer getId( )
	{
		return id;
	}

	@Override
	public void setId( Integer ttl_id_in )
	{
		id = ttl_id_in;
	}

	@Override
	public String toString( )
	{
		return getDescription( );
	}

	@Override
	public String getField( Integer field )
	{
		if ( field.equals( 2 ) ) {
			return getAbreviation( );
		}
		else {
			return super.getField( field );
		}
	}

	public Gender getGender( )
	{
		return gender;
	}

	public void setGender( Gender gender )
	{
		this.gender = gender;
	}
}
