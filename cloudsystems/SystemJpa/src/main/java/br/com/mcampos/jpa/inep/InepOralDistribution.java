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

/**
 * The persistent class for the inep_oral_distribution database table.
 * 
 */
@Entity
@Table( name = "inep_oral_distribution", schema = "inep" )
@NamedQueries( {
		@NamedQuery(
				name = InepOralDistribution.getRevisorOralTests,
				query = "select o from InepOralDistribution o WHERE o.revisor = ?1 and o.status.id = 1 order by o.id.subscriptionId " ),
		@NamedQuery(
				name = InepOralDistribution.getAllByTest,
				query = "select o from InepOralDistribution o WHERE o.test = ?1  " ),
		@NamedQuery(
				name = InepOralDistribution.getOther,
				query = "select o from InepOralDistribution o WHERE o.test = ?1 and o.id.collaboratorId <> ?2 " )
} )
public class InepOralDistribution implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final String getRevisorOralTests = "InepOralDistribution.getRevisorOralTests";
	public static final String getOther = "InepOralDistribution.getOther";
	public static final String getAllByTest = "InepOralDistribution.getAllByTest";

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

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumns( {
			@JoinColumn(
					name = "usr_id_in", referencedColumnName = "usr_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "pct_id_in", referencedColumnName = "pct_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "col_seq_in", referencedColumnName = "col_seq_in", updatable = false, insertable = false, nullable = false )
	} )
	private InepRevisor revisor;

	@EmbeddedId
	private InepOralDistributionPK id;

	public InepOralDistribution( )
	{
	}

	public InepOralDistribution( InepOralTest test, InepRevisor revisor )
	{
		this.set( test );
		this.set( revisor );
	}

	public InepOralDistribution( InepOralTest test, InepRevisor revisor, DistributionStatus status )
	{
		this.set( test );
		this.set( revisor );
		this.setStatus( status );
	}

	public void set( InepOralTest test )
	{
		this.getId( ).setCompanyId( test.getId( ).getCompanyId( ) );
		this.getId( ).setEventId( test.getId( ).getEventId( ) );
		this.getId( ).setSubscriptionId( test.getId( ).getSubscriptionId( ) );
	}

	public void set( InepRevisor revisor )
	{
		this.getId( ).setCompanyId( revisor.getId( ).getCompanyId( ) );
		this.getId( ).setEventId( revisor.getId( ).getEventId( ) );
		this.getId( ).setCollaboratorId( revisor.getId( ).getSequence( ) );

	}

	public InepOralDistributionPK getId( )
	{
		if ( this.id == null ) {
			this.id = new InepOralDistributionPK( );
		}
		return this.id;
	}

	public void setId( InepOralDistributionPK id )
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

	public InepOralTest getTest( )
	{
		return this.test;
	}

	public void setTest( InepOralTest test )
	{
		this.test = test;
	}

	public InepRevisor getRevisor( )
	{
		return this.revisor;
	}

	public void setRevisor( InepRevisor revisor )
	{
		this.revisor = revisor;
	}

}