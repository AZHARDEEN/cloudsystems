package br.com.mcampos.jpa.inep;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
				query = "select o from InepOralTest o WHERE o.subscription.event = ?1 and o.statusId = 2 order by o.subscription " ),
		@NamedQuery(
				name = InepOralTest.getVarianceOralWritten,
				query = "select o from InepOralTest o WHERE o.subscription.event = ?1 and o.statusId = 12 order by o.subscription " ),
		@NamedQuery(
				name = InepOralTest.getBySubscription,
				query = "select o from InepOralTest o WHERE o.subscription = ?1 " )
} )
public class InepOralTest extends BaseInepSubscription implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static final String getVarianceOralOnly = "InepOralTest.getVarianceOralOnly";
	public static final String getVarianceOralWritten = "InepOralTest.getVarianceOralWritten";
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

	@Column( name = "ids_id_in", nullable = true )
	private Integer statusId;

	@Column( name = "iot_agreement_grade_in", nullable = true )
	private Integer agreementGrade;

	@Column( name = "iot_variance_in", nullable = true )
	private Integer varianceStatus;

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
		set( item );
	}

	@Override
	public InepOralTestPK getId( )
	{
		if ( id == null ) {
			id = new InepOralTestPK( );
		}
		return id;
	}

	public void set( InepEvent item )
	{
		getId( ).setCompanyId( item.getId( ).getCompanyId( ) );
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
		setSubscriptionOralGrade( );
	}

	public BigDecimal getObserverGrade( )
	{
		return observerGrade;
	}

	public void setObserverGrade( BigDecimal iotObserverGradeNm )
	{
		observerGrade = iotObserverGradeNm;
		setSubscriptionOralGrade( );
	}

	private void setSubscriptionOralGrade( )
	{
		if ( getSubscription( ) != null && getObserverGrade( ) != null && getInterviewGrade( ) != null ) {
			double dValue = 0;
			dValue += getObserverGrade( ).doubleValue( );
			dValue += getInterviewGrade( ).doubleValue( );
			dValue /= 2.0D;
			BigDecimal value = BigDecimal.valueOf( dValue );
			value.setScale( 2 );
			setFinalGrade( value );
			getSubscription( ).setOralGrade( value );
		}
	}

	public String getStation( )
	{
		return station;
	}

	public void setStation( String iotStationCh )
	{
		station = iotStationCh;
	}

	public Integer getAgreementGrade( )
	{
		return agreementGrade;
	}

	public void setAgreementGrade( Integer agreementGrade )
	{
		this.agreementGrade = agreementGrade;
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

	public Integer getStatusId( )
	{
		return statusId;
	}

	public void setStatusId( Integer statusId )
	{
		this.statusId = statusId;
	}
}