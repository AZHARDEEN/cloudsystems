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
import javax.persistence.Table;

import br.com.mcampos.ejb.media.Media;

/**
 * The persistent class for the inep_oral_test database table.
 * 
 */
@Entity
@Table( name = "inep_oral_test", schema = "inep" )
public class InepOralTest implements Serializable
{
	private static final long serialVersionUID = 1L;

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

	@ManyToOne
	@JoinColumns( {
			@JoinColumn(
					name = "usr_id_in", referencedColumnName = "usr_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "pct_id_in", referencedColumnName = "pct_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "isc_id_ch", referencedColumnName = "isc_id_ch", updatable = false, insertable = false, nullable = false ) } )
	private InepSubscription subscription;

	@ManyToOne( fetch = FetchType.LAZY, optional = true )
	@JoinColumn( name = "med_id_in", referencedColumnName = "med_id_in", updatable = true, insertable = true, nullable = true )
	private Media media;

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

	public Media getMedia( )
	{
		return media;
	}

	public void setMedia( Media media )
	{
		this.media = media;
	}
}