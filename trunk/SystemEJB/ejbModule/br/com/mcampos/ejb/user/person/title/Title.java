package br.com.mcampos.ejb.user.person.title;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleTable;


@Entity
@NamedQueries( { @NamedQuery( name = "Title.findAll", query = "select o from Title o" ) } )
@Table( name = "\"title\"" )
public class Title extends SimpleTable<Title>
{
	private static final long serialVersionUID = -5257827831148175822L;

	@Column( name = "ttl_abrev_ch" )
	private String abreviation;

	@Column( name = "ttl_description_ch", nullable = false )
	private String description;

	@Id
	@Column( name = "ttl_id_in", nullable = false, unique = true )
	private Integer id;


	public String getAbreviation()
	{
		return this.abreviation;
	}

	public void setAbreviation( String ttl_abrev_ch )
	{
		this.abreviation = ttl_abrev_ch;
	}

	@Override
	public String getDescription()
	{
		return this.description;
	}

	@Override
	public void setDescription( String ttl_description_ch )
	{
		this.description = ttl_description_ch;
	}

	@Override
	public Integer getId()
	{
		return this.id;
	}

	@Override
	public void setId( Integer ttl_id_in )
	{
		this.id = ttl_id_in;
	}

	@Override
	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append( getClass().getName() + "@" + Integer.toHexString( hashCode() ) );
		buffer.append( '[' );
		buffer.append( "ttl_abrev_ch=" );
		buffer.append( getAbreviation() );
		buffer.append( ',' );
		buffer.append( "ttl_description_ch=" );
		buffer.append( getDescription() );
		buffer.append( ',' );
		buffer.append( "ttl_id_in=" );
		buffer.append( getId() );
		buffer.append( ']' );
		return buffer.toString();
	}

	@Override
	public String getField( Integer field )
	{
		if ( field.equals( 2 ) ) {
			return getAbreviation();
		}
		else {
			return super.getField( field );
		}
	}
}
