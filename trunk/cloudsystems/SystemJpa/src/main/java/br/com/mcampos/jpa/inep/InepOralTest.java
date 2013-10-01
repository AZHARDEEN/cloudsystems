package br.com.mcampos.jpa.inep;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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
				query = "select o from InepOralTest o WHERE o.subscription.event = ?1 and o.status.id = 3 and o.agreementGrade is null " +
						"and o.subscription.oralGrade < 2 and o.subscription.writtenGrade >= 2 order by o.station " ),
		@NamedQuery(
				name = InepOralTest.getBySubscription,
				query = "select o from InepOralTest o WHERE o.subscription = ?1 " )
} )
public class InepOralTest extends BaseInepSubscription implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static final String getVarianceOralOnly = "InepOralTest.getVarianceOralOnly";
	public static final String getVariance2OralOnly = "InepOralTest.getVariance2OralOnly";
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

	public InepOralTest( )
	{
	}

	public InepOralTest( InepSubscription sub )
	{
		super( sub );
	}

	public InepOralTest( InepEvent item )
	{
		this.set( item );
	}

	@Override
	public InepOralTestPK getId( )
	{
		if ( this.id == null ) {
			this.id = new InepOralTestPK( );
		}
		return this.id;
	}

	public void set( InepEvent item )
	{
		this.getId( ).setCompanyId( item.getId( ).getCompanyId( ) );
		this.getId( ).setEventId( item.getId( ).getId( ) );
	}

	public void setId( InepOralTestPK id )
	{
		this.id = id;
	}

	public BigDecimal getFinalGrade( )
	{
		return this.finalGrade;
	}

	public void setFinalGrade( BigDecimal iotFinalGradeNm )
	{
		this.finalGrade = iotFinalGradeNm;
	}

	public BigDecimal getInterviewGrade( )
	{
		return this.interviewGrade;
	}

	public void setInterviewGrade( BigDecimal iotInterviewerGradeNm )
	{
		this.interviewGrade = iotInterviewerGradeNm;
		this.setSubscriptionOralGrade( );
	}

	public BigDecimal getObserverGrade( )
	{
		return this.observerGrade;
	}

	public void setObserverGrade( BigDecimal iotObserverGradeNm )
	{
		this.observerGrade = iotObserverGradeNm;
		this.setSubscriptionOralGrade( );
	}

	private void setSubscriptionOralGrade( )
	{
		if ( this.getSubscription( ) != null && this.getObserverGrade( ) != null && this.getInterviewGrade( ) != null ) {
			double dValue = 0;
			dValue += this.getObserverGrade( ).doubleValue( );
			dValue += this.getInterviewGrade( ).doubleValue( );
			dValue /= 2.0D;
			BigDecimal value = BigDecimal.valueOf( dValue );
			this.setFinalGrade( value );
			this.getSubscription( ).setOralGrade( value );
		}
	}

	public String getStation( )
	{
		return this.station;
	}

	public void setStation( String iotStationCh )
	{
		this.station = iotStationCh;
	}

	public DistributionStatus getStatus( )
	{
		return this.status;
	}

	public void setStatus( DistributionStatus status )
	{
		this.status = status;
	}

	public Integer getAgreementGrade( )
	{
		return this.agreementGrade;
	}

	public void setAgreementGrade( Integer agreementGrade )
	{
		this.agreementGrade = agreementGrade;
	}

	public BigDecimal getWrittenGrade( )
	{
		return this.writtenGrade;
	}

	public void setWrittenGrade( BigDecimal writtenGrade )
	{
		this.writtenGrade = writtenGrade;
	}

	public BigDecimal getAgreement2Grade( )
	{
		return this.agreement2Grade;
	}

	public void setAgreement2Grade( BigDecimal agreement2Grade )
	{
		this.agreement2Grade = agreement2Grade;
	}

	public BigDecimal getRealGrade( )
	{
		return this.realGrade;
	}

	public void setRealGrade( BigDecimal realGrade )
	{
		this.realGrade = realGrade;
	}

	public String getDescStatus( )
	{
		return this.descStatus;
	}

	public void setDescStatus( String descStatus )
	{
		this.descStatus = descStatus;
	}

	public Integer getVarianceStatus( )
	{
		return this.varianceStatus;
	}

	public void setVarianceStatus( Integer varianceStatus )
	{
		this.varianceStatus = varianceStatus;
	}
}