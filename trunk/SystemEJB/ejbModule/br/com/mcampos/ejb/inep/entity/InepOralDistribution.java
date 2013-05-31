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

/**
 * The persistent class for the inep_oral_distribution database table.
 * 
 */
@Entity
@Table( name = "inep_oral_distribution", schema = "inep" )
@NamedQueries( {
		@NamedQuery(
				name = InepOralDistribution.getRevisorOralTests,
				query = "from InepOralDistribution o WHERE o.status.id = 1 and o.id.companyId = ?1 and o.id.eventId = ?2 and o.id.collaboratorId = ?3 order by o.id.subscriptionId " ),
		@NamedQuery(
				name = InepOralDistribution.getOther,
				query = "from InepOralDistribution o WHERE o.test = ?1 and o.id.collaboratorId <> ?2 " )
} )
public class InepOralDistribution implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final String getRevisorOralTests = "InepOralDistribution.getRevisorOralTests";
	public static final String getOther = "InepOralDistribution.getOther";

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "ids_id_in", referencedColumnName = "ids_id_in", updatable = true, insertable = true, nullable = false )
	private DistributionStatus status;

	@Column( name = "iod_grade_in", nullable = true )
	private Integer nota;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumns( {
			@JoinColumn(
					name = "usr_id_in", referencedColumnName = "usr_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "pct_id_in", referencedColumnName = "pct_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "isc_id_ch", referencedColumnName = "isc_id_ch", updatable = false, insertable = false, nullable = false ) } )
	private InepOralTest test;

	@EmbeddedId
	private InepOralDistributionPK id;

	public InepOralDistribution( )
	{
	}

	public InepOralDistribution( InepOralTest test, InepRevisor revisor )
	{
		set( test );
		set( revisor );
	}

	public InepOralDistribution( InepOralTest test, InepRevisor revisor, DistributionStatus status )
	{
		set( test );
		set( revisor );
		setStatus( status );
	}

	public void set( InepOralTest test )
	{
		getId( ).setCompanyId( test.getId( ).getUserId( ) );
		getId( ).setEventId( test.getId( ).getEventId( ) );
		getId( ).setSubscriptionId( test.getId( ).getSubscriptionId( ) );
	}

	public void set( InepRevisor revisor )
	{
		getId( ).setCompanyId( revisor.getId( ).getCompanyId( ) );
		getId( ).setEventId( revisor.getId( ).getEventId( ) );
		getId( ).setCollaboratorId( revisor.getId( ).getSequence( ) );

	}

	public InepOralDistributionPK getId( )
	{
		if ( id == null )
			id = new InepOralDistributionPK( );
		return id;
	}

	public void setId( InepOralDistributionPK id )
	{
		this.id = id;
	}

	public DistributionStatus getStatus( )
	{
		return status;
	}

	public void setStatus( DistributionStatus status )
	{
		this.status = status;
	}

	public Integer getNota( )
	{
		return nota;
	}

	public void setNota( Integer nota )
	{
		this.nota = nota;
	}

	public InepOralTest getTest( )
	{
		return test;
	}

	public void setTest( InepOralTest test )
	{
		this.test = test;
	}

}