package br.com.mcampos.entity.inep;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.BasicEntityRenderer;

/**
 * The persistent class for the inep_task database table.
 * 
 */
@Entity
@Table( name = "inep_task", schema = "inep" )
@NamedQueries( { @NamedQuery( name = InepTask.getAllEventTasks, query = "select o from InepTask o where o.event = ?1" ) } )
public class InepTask implements Serializable, Comparable<InepTask>, BasicEntityRenderer<InepTask>
{
	private static final long serialVersionUID = 1L;

	public static final String getAllEventTasks = "InepTask.getAllEventTasks";

	@EmbeddedId
	private InepTaskPK id;

	@Column( name = "tsk_description_ch" )
	private String description;

	@ManyToOne
	@JoinColumns( {
			@JoinColumn(
					name = "usr_id_in", referencedColumnName = "usr_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "pct_id_in", referencedColumnName = "pct_id_in", updatable = false, insertable = false, nullable = false ) } )
	private InepEvent event;

	public InepTask( )
	{
	}

	public InepTask( InepEvent event )
	{
		getId( ).set( event.getId( ) );
	}

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
			return getId( ).getId( ).compareTo( object.getId( ).getId( ) );
		case 1:
			return getDescription( ).compareTo( object.getDescription( ) );
		default:
			return 0;
		}
	}

	@Override
	public boolean equals( Object obj )
	{
		return getId( ).equals( ( (InepTask) obj ).getId( ) );
	}

	@Override
	public int compareTo( InepTask o )
	{
		return getId( ).compareTo( o.getId( ) );
	}

	@Override
	public String getField( Integer field )
	{
		switch ( field ) {
		case 0:
			return getId( ).getId( ).toString( );
		case 1:
			return getDescription( );
		default:
			return "";
		}
	}

	public InepEvent getEvent( )
	{
		return this.event;
	}

	public void setEvent( InepEvent event )
	{
		this.event = event;
		if ( getEvent( ) != null ) {
			getId( ).set( event.getId( ) );
		}
	}

	@Override
	public String toString( )
	{
		return getDescription( );
	}

}