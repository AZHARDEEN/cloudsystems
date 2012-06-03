package br.com.mcampos.ejb.inep.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.ejb.media.Media;

/**
 * The persistent class for the inep_test database table.
 * 
 */
@Entity
@Table( name = "inep_test", schema = "inep" )
@NamedQueries( {
		@NamedQuery(
				name = InepTest.getAllEventTests,
				query = "select o from InepTest o where o.subscription.event = ?1 order by o.id.companyId, o.id.eventId, o.id.subscriptionId, o.id.taskId" )
} )
public class InepTest implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static final String getAllEventTests = "InepTest.getAllEventTests";

	@EmbeddedId
	private InepTestPK id;

	@ManyToOne( fetch = FetchType.LAZY, optional = true )
	@JoinColumn( name = "med_id_in", referencedColumnName = "med_id_in", updatable = true, insertable = true, nullable = true )
	private Media media;

	@ManyToOne
	@JoinColumns( {
			@JoinColumn(
					name = "usr_id_in", referencedColumnName = "usr_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "pct_id_in", referencedColumnName = "pct_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "tsk_id_in", referencedColumnName = "tsk_id_in", updatable = false, insertable = false, nullable = false ) } )
	private InepTask task;

	@ManyToOne
	@JoinColumns( {
			@JoinColumn(
					name = "usr_id_in", referencedColumnName = "usr_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "pct_id_in", referencedColumnName = "pct_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "isc_id_ch", referencedColumnName = "isc_id_ch", updatable = false, insertable = false, nullable = false ) } )
	private InepSubscription subscription;

	public InepTest( )
	{
	}

	public InepTest( InepTask task )
	{
		setTask( task );
	}

	public InepTestPK getId( )
	{
		if ( this.id == null ) {
			this.id = new InepTestPK( );
		}
		return this.id;
	}

	public void setId( InepTestPK id )
	{
		this.id = id;
	}

	public Media getMedia( )
	{
		return this.media;
	}

	public void setMedia( Media media )
	{
		this.media = media;
	}

	public InepTask getTask( )
	{
		return this.task;
	}

	public void setTask( InepTask task )
	{
		this.task = task;
		if ( getTask( ) != null ) {
			this.getId( ).set( task );
		}
	}

	public InepSubscription getSubscription( )
	{
		return this.subscription;
	}

	public void setSubscription( InepSubscription subscription )
	{
		this.subscription = subscription;
		if ( getSubscription( ) != null ) {
			getId( ).set( subscription );
		}
	}
}