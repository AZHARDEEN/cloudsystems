package br.com.mcampos.jpa.inep;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the inep_test database table.
 * 
 */
@Entity
@Table( name = "inep_test", schema = "inep" )
@NamedQueries( {
		@NamedQuery(
				name = InepTest.getAllEventTests,
				query = "select o from InepTest o where o.subscription.event = ?1 order by o.id.companyId, o.id.eventId, o.id.subscriptionId, o.id.taskId" ),
		@NamedQuery(
				name = InepTest.getAllSubscription,
				query = "select o from InepTest o where o.subscription = ?1 order by o.id.companyId, o.id.eventId, o.id.subscriptionId, o.id.taskId" ),
		@NamedQuery(
				name = InepTest.getAllEventTasks,
				query = "select o from InepTest o where o.task = ?1 and not exists ( select t from InepDistribution t where t.test = o ) "
						+ "order by o.id.companyId, o.id.eventId, o.id.subscriptionId, o.id.taskId" ),
		@NamedQuery(
				name = InepTest.getAllTestsWithVariance,
				query = "select o from InepTest o where o.subscription.event = ?1 " +
						"and o.subscription in ( select t.test.subscription from InepDistribution t where t.status.id in ( 3, 4 ) ) " +
						"order by o.id.companyId, o.id.eventId, o.id.subscriptionId, o.id.taskId" )
} )
public class InepTest implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static final String getAllEventTests = "InepTest.getAllEventTests";
	public static final String getAllEventTasks = "InepTest.getAllEventTask";
	public static final String getAllTestsWithVariance = "InepTest.getAllTestsWithVariance";
	public static final String getAllSubscription = "InepTest.getAllSubscription";

	@EmbeddedId
	private InepTestPK id;

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

	@Column( name = "itt_grade_nm" )
	private BigDecimal grade;

	public InepTest( )
	{
	}

	public InepTest( InepTask task )
	{
		this.setTask( task );
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

	public InepTask getTask( )
	{
		return this.task;
	}

	public void setTask( InepTask task )
	{
		this.task = task;
		if ( this.getTask( ) != null ) {
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
		if ( this.getSubscription( ) != null ) {
			this.getId( ).set( subscription );
		}
	}

	public BigDecimal getGrade( )
	{
		if ( this.grade == null ) {
			this.grade = new BigDecimal( 0 );
		}
		return this.grade;
	}

	public void setGrade( BigDecimal grade )
	{
		this.grade = grade;
	}

}