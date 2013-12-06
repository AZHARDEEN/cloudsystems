package br.com.mcampos.jpa.inep;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.jpa.BasicEntityRenderer;
import br.com.mcampos.jpa.user.Collaborator;

/**
 * The persistent class for the inep_revisor database table.
 * 
 */
@Entity
@Table( name = "inep_revisor", schema = "inep" )
@NamedQueries( {
		@NamedQuery( name = InepRevisor.getTeamByTask, query = "select o from InepRevisor o " ),
		@NamedQuery( name = InepRevisor.getAllTeamByEventAndTask, query = "select o from InepRevisor o where o.task = ?1 " ),
		@NamedQuery( name = InepRevisor.getAllRevisorByEventAndTask, query = "select o from InepRevisor o where o.task = ?1 and o.coordenador = false "
				+ "and coalesce (o.ignoreOnDistribute, false) = false" ),
		@NamedQuery( name = InepRevisor.getAllTeamByEvent, query = "select o from InepRevisor o where o.id.companyId = ?1 and o.id.eventId = ?2" ),
		@NamedQuery( name = InepRevisor.getAllCoordinatorsToTask, query = "select o from InepRevisor o where o.task = ?1 and o.coordenador = true" ),
		@NamedQuery( name = InepRevisor.getAllTeam, query = "select o from InepRevisor o where o.coordenador = false" ),
		@NamedQuery(
				name = InepRevisor.getAllOralTeamByEvent,
				query = "select o from InepRevisor o where o.id.companyId = ?1 and o.id.eventId = ?2 and o.coordenador = false and o.taskId is null order by o.collaborator.person.name" ),
		@NamedQuery(
				name = InepRevisor.getOralCoordinatorByEvent,
				query = "select o from InepRevisor o where o.event = ?1 and o.coordenador = true and o.taskId is null order by o.collaborator.person.name" )

} )
public class InepRevisor implements Serializable, Comparable<InepRevisor>, BasicEntityRenderer<InepRevisor>
{
	private static final long serialVersionUID = 1L;

	public static final String getTeamByTask = "InepRevisor.getTeamByTask";
	public static final String getAllTeam = "InepRevisor.getAllTeam";
	public static final String getAllTeamByEvent = "InepRevisor.getAllTeamByEvent";
	public static final String getAllTeamByEventAndTask = "InepRevisor.getAllTeamByEventAndTask";
	public static final String getAllRevisorByEventAndTask = "InepRevisor.getAllRevisorByEventAndTask";
	public static final String getAllCoordinatorsToTask = "InepRevisor.getAllCoordinatorsToTask";

	public static final String getAllOralTeamByEvent = "InepRevisor.getAllOralTeamByEvent";
	public static final String getOralCoordinatorByEvent = "InepRevisor.getOralCoordinatorByEvent";

	@EmbeddedId
	private InepRevisorPK id;

	@Column( name = "rvs_coordinator_bt" )
	private Boolean coordenador;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumns( {
			@JoinColumn(
					name = "usr_id_in", referencedColumnName = "usr_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "pct_id_in", referencedColumnName = "pct_id_in", updatable = false, insertable = false, nullable = false ) } )
	private InepEvent event;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumns( {
			@JoinColumn(
					name = "usr_id_in", referencedColumnName = "usr_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "col_seq_in", referencedColumnName = "col_seq_in", updatable = false, insertable = false, nullable = false ) } )
	private Collaborator collaborator;

	@ManyToOne( fetch = FetchType.EAGER, optional = true )
	@JoinColumns( {
			@JoinColumn(
					name = "usr_id_in", referencedColumnName = "usr_id_in", updatable = false, insertable = false, nullable = true ),
			@JoinColumn(
					name = "pct_id_in", referencedColumnName = "pct_id_in", updatable = false, insertable = false, nullable = true ),
			@JoinColumn(
					name = "tsk_id_in", referencedColumnName = "tsk_id_in", updatable = false, insertable = false, nullable = true ) } )
	private InepTask task;

	@Column( name = "tsk_id_in", nullable = true, updatable = true, insertable = true )
	private Integer taskId;

	@Column( name = "rvs_ignore_bt", nullable = true, updatable = true, insertable = true )
	private Boolean ignoreOnDistribute;

	@ManyToOne( optional = false )
	@JoinColumn( name = "irt_id_in", referencedColumnName = "irt_id_in", updatable = true, insertable = true, nullable = false )
	private RevisorType type;

	public InepRevisor( )
	{
	}

	public InepRevisor( Collaborator c, InepEvent e )
	{
		this.getId( ).set( c );
		this.getId( ).set( e );
	}

	@Override
	public InepRevisorPK getId( )
	{
		if ( this.id == null ) {
			this.id = new InepRevisorPK( );
		}
		return this.id;
	}

	public void setId( InepRevisorPK id )
	{
		this.id = id;
	}

	public Boolean isCoordenador( )
	{
		if ( this.coordenador == null ) {
			return false;
		}
		return this.coordenador;
	}

	public void setCoordenador( Boolean rvsCoordinatorBt )
	{
		this.coordenador = rvsCoordinatorBt;
	}

	public Collaborator getCollaborator( )
	{
		return this.collaborator;
	}

	public void setCollaborator( Collaborator collaborator )
	{
		this.collaborator = collaborator;
		if ( this.getCollaborator( ) != null ) {
			this.getId( ).set( this.getCollaborator( ) );
		}
	}

	@Override
	public int compareTo( InepRevisor object, Integer field )
	{
		switch ( field ) {
		case 0:
			return this.getId( ).compareTo( object.getId( ) );
		case 1:
			return this.getCollaborator( ).getPerson( ).getName( ).compareTo( object.getCollaborator( ).getPerson( ).getName( ) );
		case 2:
			return this.getTask( ).getDescription( ).compareTo( object.getTask( ).getDescription( ) );
		case 3:
			return this.isCoordenador( ).compareTo( object.isCoordenador( ) );
		default:
			return 0;
		}
	}

	@Override
	public boolean equals( Object obj )
	{
		return this.getId( ).equals( ( (InepRevisor) obj ).getId( ) );
	}

	@Override
	public int compareTo( InepRevisor o )
	{
		return this.getId( ).compareTo( o.getId( ) );
	}

	@Override
	public String getField( Integer field )
	{
		switch ( field ) {
		case 0:
			return this.getId( ).getSequence( ).toString( );
		case 1:
			return this.getCollaborator( ).getPerson( ).getName( );
		case 2:
			return this.getTask( ).getDescription( );
		case 3:
			return this.isCoordenador( ) ? "SIM" : "";
		default:
			return "";
		}
	}

	public InepTask getTask( )
	{
		return this.task;
	}

	public void setTask( InepTask task )
	{
		this.task = task;
		if ( task != null ) {
			this.getId( ).set( task );
			this.setTaskId( task.getId( ).getId( ) );
		}
	}

	@Override
	public String toString( )
	{
		return this.getCollaborator( ).getPerson( ).getName( );
	}

	public Integer getTaskId( )
	{
		return this.taskId;
	}

	public void setTaskId( Integer taskId )
	{
		this.taskId = taskId;
	}

	public RevisorType getType( )
	{
		return this.type;
	}

	public void setType( RevisorType type )
	{
		this.type = type;
	}

	public InepEvent getEvent( )
	{
		return this.event;
	}

	public void setEvent( InepEvent event )
	{
		this.event = event;
	}

	public Boolean getIgnoreOnDistribute( )
	{
		return this.ignoreOnDistribute;
	}

	public void setIgnoreOnDistribute( Boolean ignoreOnDistribute )
	{
		this.ignoreOnDistribute = ignoreOnDistribute;
	}

}