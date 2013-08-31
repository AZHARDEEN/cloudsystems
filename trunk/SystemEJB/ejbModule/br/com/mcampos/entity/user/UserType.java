package br.com.mcampos.entity.user;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleTable;


@Entity
@Table( name = "user_type" )
public class UserType extends SimpleTable<UserType>
{
	private static final long serialVersionUID = 3293779834666426061L;

	public static final String typePerson = "1";
	public static final String typeCompany = "2";
	@Column( name = "ust_description_ch", nullable = false )
	private String description;

	@Id
	@Column( name = "ust_id_in", nullable = false )
	private String id;


	@Override
	public String getDescription()
	{
		return this.description;
	}

	@Override
	public void setDescription( String ust_description_ch )
	{
		this.description = ust_description_ch;
	}

	@Override
	public Integer getId()
	{
		return Integer.parseInt( this.id );
	}

	@Override
	public void setId( Integer id )
	{
		this.id = id.toString();
	}

	@Override
	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append( getClass().getName() + "@" + Integer.toHexString( hashCode() ) );
		buffer.append( '[' );
		buffer.append( "ust_description_ch=" );
		buffer.append( getDescription() );
		buffer.append( ',' );
		buffer.append( "ust_id_in=" );
		buffer.append( getId() );
		buffer.append( ']' );
		return buffer.toString();
	}

}
