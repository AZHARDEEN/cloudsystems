package br.com.mcampos.ejb.inep.entity;

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

import br.com.mcampos.ejb.core.BasicEntityRenderer;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

/**
 * The persistent class for the inep_revisor database table.
 * 
 */
@Entity
@Table( name = "inep_revisor", schema = "inep" )
@NamedQueries( {
		@NamedQuery( name = InepRevisor.getTeamByTask, query = "select o from InepRevisor o " ),
		@NamedQuery( name = InepRevisor.getAllTeamByEventAndTask, query = "select o from InepRevisor o where o.task = ?1 " ),
		@NamedQuery( name = InepRevisor.getAllRevisorByEventAndTask, query = "select o from InepRevisor o where o.task = ?1 and o.coordenador = false " ),
		@NamedQuery( name = InepRevisor.getAllTeamByEvent, query = "select o from InepRevisor o where o.task.event = ?1" ),
		@NamedQuery( name = InepRevisor.getAllCoordinatorsToTask, query = "select o from InepRevisor o where o.task = ?1 and o.coordenador = true" ),
		@NamedQuery( name = InepRevisor.getAllTeam, query = "select o from InepRevisor o where o.coordenador = false" ), } )
public class InepRevisor implements Serializable, Comparable<InepRevisor>, BasicEntityRenderer<InepRevisor>
{
	private static final long serialVersionUID = 1L;

	public static final String getTeamByTask = "InepRevisor.getTeamByTask";
	public static final String getAllTeam = "InepRevisor.getAllTeam";
	public static final String getAllTeamByEvent = "InepRevisor.getAllTeamByEvent";
	public static final String getAllTeamByEventAndTask = "InepRevisor.getAllTeamByEventAndTask";
	public static final String getAllRevisorByEventAndTask = "InepRevisor.getAllRevisorByEventAndTask";
	public static final String getAllCoordinatorsToTask = "InepRevisor.getAllCoordinatorsToTask";

	@EmbeddedId
	private InepRevisorPK id;

	@Column( name = "rvs_coordinator_bt" )
	private Boolean coordenador;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumns( {
			@JoinColumn(
					name = "usr_id_in", referencedColumnName = "usr_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "col_seq_in", referencedColumnName = "col_seq_in", updatable = false, insertable = false, nullable = false ) } )
	private Collaborator collaborator;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumns( {
			@JoinColumn(
					name = "usr_id_in", referencedColumnName = "usr_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "pct_id_in", referencedColumnName = "pct_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "tsk_id_in", referencedColumnName = "tsk_id_in", updatable = false, insertable = false, nullable = false ) } )
	private InepTask task;

	@Column( name = "tsk_id_in", nullable = false, updatable = true, insertable = true )
	private Integer taskId;

	public InepRevisor( )
	{
	}

	public InepRevisor( Collaborator c, InepPackage e )
	{
		getId( ).set( c );
		getId( ).set( e );
	}

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
		if ( getCollaborator( ) != null ) {
			getId( ).set( getCollaborator( ) );
		}
	}

	@Override
	public int compareTo( InepRevisor object, Integer field )
	{
		switch ( field ) {
		case 0:
			return getId( ).compareTo( object.getId( ) );
		case 1:
			return getCollaborator( ).getPerson( ).getName( ).compareTo( object.getCollaborator( ).getPerson( ).getName( ) );
		case 2:
			return getTask( ).getDescription( ).compareTo( object.getTask( ).getDescription( ) );
		case 3:
			return isCoordenador( ).compareTo( object.isCoordenador( ) );
		default:
			return 0;
		}
	}

	@Override
	public boolean equals( Object obj )
	{
		return getId( ).equals( ( (InepRevisor) obj ).getId( ) );
	}

	@Override
	public int compareTo( InepRevisor o )
	{
		return getId( ).compareTo( o.getId( ) );
	}

	@Override
	public String getField( Integer field )
	{
		switch ( field ) {
		case 0:
			return getId( ).getSequence( ).toString( );
		case 1:
			return getCollaborator( ).getPerson( ).getName( );
		case 2:
			return getTask( ).getDescription( );
		case 3:
			return isCoordenador( ) ? "SIM" : "";
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
			getId( ).set( task );
			setTaskId( task.getId( ).getId( ) );
		}
	}

	@Override
	public String toString( )
	{
		return getCollaborator( ).getPerson( ).getName( );
	}

	public Integer getTaskId( )
	{
		return this.taskId;
	}

	public void setTaskId( Integer taskId )
	{
		this.taskId = taskId;
	}

}