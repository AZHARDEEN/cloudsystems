package br.com.mcampos.jpa.inep;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the inep_element database table.
 * 
 */
@Entity
@Table( name = "inep_element", schema = "inep" )
@NamedQuery( name = "InepElement.findAll", query = "SELECT i FROM InepElement i" )
public class InepElement extends BaseInepSubscription implements Serializable, Comparable<InepElement>
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InepElementPK id;

	public InepElement( )
	{
	}

	public InepElement( InepSubscription sub )
	{
		super( sub );
	}

	public InepElement( InepSubscription sub, int id )
	{
		super( sub );
		this.getId( ).setId( id );
	}

	@Override
	public InepElementPK getId( )
	{
		if ( this.id == null ) {
			this.id = new InepElementPK( );
		}
		return this.id;
	}

	public void setId( InepElementPK id )
	{
		this.id = id;
	}

	@Override
	public int compareTo( InepElement arg0 )
	{
		return this.getId( ).compareTo( arg0.getId( ) );
	}

	@Override
	public boolean equals( Object obj )
	{
		return this.getId( ).equals( obj ) && this.getId( ).getId( ).equals( ( (InepElement) obj ).getId( ).getId( ) );
	}

}