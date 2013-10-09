package br.com.mcampos.jpa.inep;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.jpa.BasicEntityRenderer;

/**
 * The persistent class for the inep_task database table.
 * 
 */
@Entity
@Table( name = "inep_task", schema = "inep" )
@NamedQueries( { @NamedQuery( name = InepTask.getAllEventTasks, query = "select o from InepTask o where o.event = ?1" ) } )
public class InepTask extends BaseInepEvent implements Serializable, Comparable<InepTask>, BasicEntityRenderer<InepTask>
{
	private static final long serialVersionUID = 1L;

	public static final String getAllEventTasks = "InepTask.getAllEventTasks";

	@EmbeddedId
	private InepTaskPK id;

	@Column( name = "tsk_description_ch" )
	private String description;

	public InepTask( )
	{
	}

	public InepTask( InepEvent event )
	{
		super( event );
	}

	@Override
	public InepTaskPK getId( )
	{
		if ( this.id == null ) {
			this.id = new InepTaskPK( );
		}
		return this.id;
	}

	public void setId( InepTaskPK id )
	{
		this.id = id;
	}

	public String getDescription( )
	{
		return this.description;
	}

	public void setDescription( String tskDescriptionCh )
	{
		this.description = tskDescriptionCh;
	}

	@Override
	public int compareTo( InepTask object, Integer field )
	{
		switch ( field ) {
		case 0:
			return this.getId( ).getId( ).compareTo( object.getId( ).getId( ) );
		case 1:
			return this.getDescription( ).compareTo( object.getDescription( ) );
		default:
			return 0;
		}
	}

	@Override
	public boolean equals( Object obj )
	{
		return this.getId( ).equals( ( (InepTask) obj ).getId( ) );
	}

	@Override
	public int compareTo( InepTask o )
	{
		return this.getId( ).compareTo( o.getId( ) );
	}

	@Override
	public String getField( Integer field )
	{
		switch ( field ) {
		case 0:
			return this.getId( ).getId( ).toString( );
		case 1:
			return this.getDescription( );
		default:
			return "";
		}
	}

	@Override
	public String toString( )
	{
		return this.getDescription( );
	}

}
package br.com.mcampos.jpa.inep;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.jpa.BasicEntityRenderer;

/**
 * The persistent class for the inep_task database table.
 * 
 */
@Entity
@Table( name = "inep_task", schema = "inep" )
@NamedQueries( { @NamedQuery( name = InepTask.getAllEventTasks, query = "select o from InepTask o where o.event = ?1" ) } )
public class InepTask extends BaseInepEvent implements Serializable, Comparable<InepTask>, BasicEntityRenderer<InepTask>
{
	private static final long serialVersionUID = 1L;

	public static final String getAllEventTasks = "InepTask.getAllEventTasks";

	@EmbeddedId
	private InepTaskPK id;

	@Column( name = "tsk_description_ch" )
	private String description;

	public InepTask( )
	{
	}

	public InepTask( InepEvent event )
	{
		this.getId( ).set( event.getId( ) );
	}

	@Override
	public InepTaskPK getId( )
	{
		if( this.id == null ) {
			this.id = new InepTaskPK( );
		}
		return this.id;
	}

	public void setId( InepTaskPK id )
	{
		this.id = id;
	}

	public String getDescription( )
	{
		return this.description;
	}

	public void setDescription( String tskDescriptionCh )
	{
		this.description = tskDescriptionCh;
	}

	@Override
	public int compareTo( InepTask object, Integer field )
	{
		switch( field ) {
		case 0:
			return this.getId( ).getId( ).compareTo( object.getId( ).getId( ) );
		case 1:
			return this.getDescription( ).compareTo( object.getDescription( ) );
		default:
			return 0;
		}
	}

	@Override
	public boolean equals( Object obj )
	{
		return this.getId( ).equals( ((InepTask) obj).getId( ) );
	}

	@Override
	public int compareTo( InepTask o )
	{
		return this.getId( ).compareTo( o.getId( ) );
	}

	@Override
	public String getField( Integer field )
	{
		switch( field ) {
		case 0:
			return this.getId( ).getId( ).toString( );
		case 1:
			return this.getDescription( );
		default:
			return "";
		}
	}

	@Override
	public String toString( )
	{
		return this.getDescription( );
	}

}