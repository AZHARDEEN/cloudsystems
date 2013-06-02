package br.com.mcampos.ejb.inep.entity;

import java.io.Serializable;
import java.math.BigDecimal;

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
 * The persistent class for the inep_oral_test database table.
 * 
 */
@Entity
@Table( name = "inep_oral_test", schema = "inep" )
@NamedQueries( {
		@NamedQuery(
				name = InepOralTest.getVarianceOralOnly,
				query = "from InepOralTest o WHERE o.subscription.event = ?1 and o.status.id = 3 order by station " ),
		@NamedQuery(
				name = InepOralTest.getBySubscription,
				query = "from InepOralTest o WHERE o.subscription = ?1 " )
} )
public class InepOralTest implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static final String getVarianceOralOnly = "InepOralTest.getVarianceOralOnly";
	public static final String getBySubscription = "InepOralTest.getBySubscription";

	@EmbeddedId
	private InepOralTestPK id;

	@Column( name = "iot_final_grade_nm" )
	private BigDecimal finalGrade;

	@Column( name = "iot_interviewer_grade_nm" )
	private BigDecimal interviewGrade;

	@Column( name = "iot_observer_grade_nm" )
	private BigDecimal observerGrade;

	@Column( name = "iot_station_ch" )
	private String station;

	@Column( name = "iot_status_ch" )
	private String descStatus;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "ids_id_in", referencedColumnName = "ids_id_in", updatable = true, insertable = true, nullable = false )
	private DistributionStatus status;

	@Column( name = "iot_agreement_grade_in", nullable = true )
	private Integer agreementGrade;

	@Column( name = "iot_variance_in", nullable = true )
	private Integer varianceStatus;

	@Column( name = "iot_written_grade_nm", nullable = true )
	private BigDecimal writtenGrade;

	@Column( name = "iot_agreement2_grade_nm", nullable = true )
	private BigDecimal agreement2Grade;

	@Column( name = "iot_grade_nm", nullable = true )
	private BigDecimal realGrade;

	@ManyToOne
	@JoinColumns( {
			@JoinColumn(
					name = "usr_id_in", referencedColumnName = "usr_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "pct_id_in", referencedColumnName = "pct_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "isc_id_ch", referencedColumnName = "isc_id_ch", updatable = false, insertable = false, nullable = false ) } )
	private InepSubscription subscription;

	public InepOralTest( )
	{
	}

	public InepOralTest( InepPackage item )
	{
		set( item );
	}

	public InepOralTestPK getId( )
	{
		if ( id == null )
			id = new InepOralTestPK( );
		return id;
	}

	public void set( InepPackage item )
	{
		getId( ).setUserId( item.getId( ).getCompanyId( ) );
		getId( ).setEventId( item.getId( ).getId( ) );
	}

	public void setId( InepOralTestPK id )
	{
		this.id = id;
	}

	public BigDecimal getFinalGrade( )
	{
		return finalGrade;
	}

	public void setFinalGrade( BigDecimal iotFinalGradeNm )
	{
		finalGrade = iotFinalGradeNm;
	}

	public BigDecimal getInterviewGrade( )
	{
		return interviewGrade;
	}

	public void setInterviewGrade( BigDecimal iotInterviewerGradeNm )
	{
		interviewGrade = iotInterviewerGradeNm;
	}

	public BigDecimal getObserverGrade( )
	{
		return observerGrade;
	}

	public void setObserverGrade( BigDecimal iotObserverGradeNm )
	{
		observerGrade = iotObserverGradeNm;
	}

	public String getStation( )
	{
		return station;
	}

	public void setStation( String iotStationCh )
	{
		station = iotStationCh;
	}

	public InepSubscription getSubscription( )
	{
		return subscription;
	}

	public void setSubscription( InepSubscription subscription )
	{
		this.subscription = subscription;
	}

	public DistributionStatus getStatus( )
	{
		return status;
	}

	public void setStatus( DistributionStatus status )
	{
		this.status = status;
	}

	public Integer getAgreementGrade( )
	{
		return agreementGrade;
	}

	public void setAgreementGrade( Integer agreementGrade )
	{
		this.agreementGrade = agreementGrade;
	}

	public BigDecimal getWrittenGrade( )
	{
		return writtenGrade;
	}

	public void setWrittenGrade( BigDecimal writtenGrade )
	{
		this.writtenGrade = writtenGrade;
	}

	public BigDecimal getAgreement2Grade( )
	{
		return agreement2Grade;
	}

	public void setAgreement2Grade( BigDecimal agreement2Grade )
	{
		this.agreement2Grade = agreement2Grade;
	}

	public BigDecimal getRealGrade( )
	{
		return realGrade;
	}

	public void setRealGrade( BigDecimal realGrade )
	{
		this.realGrade = realGrade;
	}

	public String getDescStatus( )
	{
		return descStatus;
	}

	public void setDescStatus( String descStatus )
	{
		this.descStatus = descStatus;
	}

	public Integer getVarianceStatus( )
	{
		return varianceStatus;
	}

	public void setVarianceStatus( Integer varianceStatus )
	{
		this.varianceStatus = varianceStatus;
	}
}