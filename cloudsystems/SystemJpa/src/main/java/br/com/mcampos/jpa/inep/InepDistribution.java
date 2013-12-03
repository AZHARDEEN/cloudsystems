package br.com.mcampos.jpa.inep;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mcampos.jpa.BasicEntityRenderer;

/**
 * The persistent class for the inep_distribution database table.
 * 
 */
@Entity
@Table( name = "inep_distribution", schema = "inep" )
@NamedQueries( {
		@NamedQuery(
				name = InepDistribution.getAllFromRevisor,
				query = "select o from InepDistribution o where o.revisor = ?1 and o.status.id = ?2 and o.revisor.task = o.test.task order by o.priority, o.test.subscription" ),
		@NamedQuery(
				name = InepDistribution.getOtherDistribution,
				query = "select o from InepDistribution o where o.test = ?1 and o.status.id = 3 and (o.isGolden is null or o.isGolden = false)" ),
		@NamedQuery(
				name = InepDistribution.getAllFromTest,
				query = "select o from InepDistribution o where o.test = ?1 and (o.isGolden is null or o.isGolden = false) " ),
		@NamedQuery(
				name = InepDistribution.hasPendingDistribution,
				query = "select count(o) from InepDistribution o where o.test.subscription = ?1 and o.status.id not in ( 2, 4 ) " ),
		@NamedQuery(
				name = InepDistribution.getRevisorCounter,
				query = "select o.status.id, count(o) from InepDistribution o where o.revisor = ?1 " +
						"and ( ( o.status.id in ( 1, 2 ) ) or ( o.status.id in ( 3, 4 ) and o.nota is not null ) )" +
						"group by o.status.id" ),
		@NamedQuery(
				name = InepDistribution.getCoordCounter,
				query = "select o.status.id, count( distinct o.id.subscriptionId ) from InepDistribution o where " +
						"o.test.task = ?1 group by o.status.id" ),
		@NamedQuery(
				name = InepDistribution.getWorkStatus,
				query = "select o.id.taskId,  o.status.id, count(o) from InepDistribution o where o.test.subscription.event = ?1 " +
						"group by o.id.taskId,  o.status.id order by o.id.taskId,  o.status.id" ),
		@NamedQuery(
				name = InepDistribution.getAll,
				query = "select o from InepDistribution o where o.test.subscription.event =?1 and o.nota is not null " +
						"and o.revisor.coordenador = false " +
						"order by o.id.companyId, o.id.eventId, o.id.subscriptionId, o.id.taskId" ),
		@NamedQuery(
				name = InepDistribution.getAllVariance,
				query = "select o from InepDistribution o where o.test.subscription.event = ?1 and o.status.id = 4 " +
						"and o.nota is not null and o.revisor.coordenador = true " +
						"order by o.id.companyId, o.id.eventId, o.id.subscriptionId, o.id.taskId" ),
		@NamedQuery(
				name = InepDistribution.getAllSubscription,
				query = "select o from InepDistribution o where o.test.subscription = ?1 " +
						"order by o.id.companyId, o.id.eventId, o.id.subscriptionId, o.id.taskId" ),
		@NamedQuery(
				name = InepDistribution.getAllVarianceFromTest,
				query = "select o from InepDistribution o where o.test.subscription.event = ?1 and o.status.id in (3, 4) " +
						"and o.nota is not null and o.revisor.coordenador = true " +
						"order by o.id.companyId, o.id.eventId, o.id.subscriptionId, o.id.taskId" )
} )
public class InepDistribution implements Serializable, BasicEntityRenderer<InepDistribution>
{
	private static final long serialVersionUID = 1L;
	public static final String getAllFromRevisor = "InepDistribution.getAllFromRevisor";
	public static final String getAllFromTest = "InepDistribution.getAllFromTest";
	public static final String getOtherDistribution = "InepDistribution.getOtherDistribution";
	public static final String getRevisorCounter = "InepDistribution.getRevisorCounter";
	public static final String getCoordCounter = "InepDistribution.getCoordinatorCounter";
	public static final String getAll = "InepDistribution.getAll";
	public static final String getAllSubscription = "InepDistribution.getAllSubscription";
	public static final String getAllVariance = "InepDistribution.getAllVariance";
	public static final String getAllVarianceFromTest = "InepDistribution.getAllVarianceFromTest";
	public static final String getWorkStatus = "InepDistribution.getWorkStatus";

	public static final String hasPendingDistribution = "InepDistribution.hasPendingDistribution";

	@EmbeddedId
	private InepDistributionPK id;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "ids_id_in", referencedColumnName = "ids_id_in", updatable = true, insertable = true, nullable = false )
	private DistributionStatus status;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumns( {
			@JoinColumn(
					name = "usr_id_in", referencedColumnName = "usr_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "pct_id_in", referencedColumnName = "pct_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "col_seq_in", referencedColumnName = "col_seq_in", updatable = false, insertable = false,
					nullable = false )
	} )
	private InepRevisor revisor;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumns( {
			@JoinColumn(
					name = "usr_id_in", referencedColumnName = "usr_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "pct_id_in", referencedColumnName = "pct_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "tsk_id_in", referencedColumnName = "tsk_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "isc_id_ch", referencedColumnName = "isc_id_ch", updatable = false, insertable = false, nullable = false ) } )
	private InepTest test;

	@Column( name = "dis_grade_in" )
	private Integer nota;

	@Column( name = "dis_obs_tx" )
	private String obs;

	@Column( name = "dis_insert_dt" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date insertDate;

	@Column( name = "dis_update_dt" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date updateDate;

	@Column( name = "dis_start_dt" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date startDate;

	@Column( name = "dis_priority_in" )
	private Integer priority;

	@Column( name = "dis_golden_bt", nullable = true, columnDefinition = "boolean", insertable = true, updatable = true, unique = false )
	private Boolean isGolden;

	public InepDistribution( )
	{
	}

	public InepDistribution( InepRevisor r, InepTest t )
	{
		this.getId( ).set( r, t );
	}

	@Override
	public InepDistributionPK getId( )
	{
		if ( this.id == null ) {
			this.id = new InepDistributionPK( );
		}
		return this.id;
	}

	public void setId( InepDistributionPK id )
	{
		this.id = id;
	}

	public DistributionStatus getStatus( )
	{
		return this.status;
	}

	public void setStatus( DistributionStatus status )
	{
		this.status = status;
	}

	public Integer getNota( )
	{
		return this.nota;
	}

	public void setNota( Integer nota )
	{
		this.nota = nota;
	}

	public InepRevisor getRevisor( )
	{
		return this.revisor;
	}

	public void setRevisor( InepRevisor revisor )
	{
		this.revisor = revisor;
		if ( this.getRevisor( ) != null ) {
			this.getId( ).set( revisor );
		}
	}

	public InepTest getTest( )
	{
		return this.test;
	}

	public void setTest( InepTest test )
	{
		this.test = test;
		if ( this.getTest( ) != null ) {
			this.getId( ).set( test );
		}
	}

	public String getObs( )
	{
		return this.obs;
	}

	public void setObs( String obs )
	{
		this.obs = obs;
	}

	public Date getInsertDate( )
	{
		if ( this.insertDate == null ) {
			this.insertDate = new Date( );
		}
		return this.insertDate;
	}

	public void setInsertDate( Date insertDate )
	{
		this.insertDate = insertDate;
	}

	public Date getUpdateDate( )
	{
		return this.updateDate;
	}

	public void setUpdateDate( Date updateDate )
	{
		this.updateDate = updateDate;
	}

	@Override
	public String getField( Integer field )
	{
		switch ( field ) {
		case 0:
			return this.getId( ).toString( );
		default:
			return "";
		}
	}

	@Override
	public int compareTo( InepDistribution object, Integer field )
	{
		switch ( field ) {
		case 0:
			return this.getId( ).compareTo( object.getId( ) );
		default:
			return 0;
		}
	}

	public Integer getPriority( )
	{
		return this.priority;
	}

	public void setPriority( Integer priority )
	{
		this.priority = priority;
	}

	public Date getStartDate( )
	{
		return this.startDate;
	}

	public void setStartDate( Date startDate )
	{
		this.startDate = startDate;
	}

	public Boolean getIsGolden( )
	{
		if ( this.isGolden == null ) {
			this.isGolden = false;
		}
		return this.isGolden;
	}

	public void setIsGolden( Boolean isGolden )
	{
		this.isGolden = isGolden;
	}

}