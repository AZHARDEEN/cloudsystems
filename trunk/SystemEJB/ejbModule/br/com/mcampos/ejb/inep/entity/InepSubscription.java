package br.com.mcampos.ejb.inep.entity;

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
 * The persistent class for the inep_subscription database table.
 * 
 */
@Entity
@Table( name = "inep_subscription", schema = "inep" )
@NamedQueries( {
		@NamedQuery( name = InepSubscription.getAllEventSubs, query = "select o from InepSubscription o where o.event = ?1" ),
		@NamedQuery(
				name = InepSubscription.getAllEventSubsById,
				query = "select o from InepSubscription o where o.event = ?1 and o.id.id like ?2" )
} )
public class InepSubscription implements Serializable, Comparable<InepSubscription>
{
	private static final long serialVersionUID = 1L;
	public static final String getAllEventSubs = "InepSubscription.getAllEventSubs";
	public static final String getAllEventSubsById = "InepSubscription.getAllEventSubsById";

	@EmbeddedId
	private InepSubscriptionPK id;

	@ManyToOne
	@JoinColumns( {
			@JoinColumn(
					name = "usr_id_in", referencedColumnName = "usr_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "pct_id_in", referencedColumnName = "pct_id_in", updatable = false, insertable = false, nullable = false ) } )
	private InepPackage event;

	@Column( name = "isc_written_grade_nm" )
	private BigDecimal writtenGrade;

	@Column( name = "isc_oral_grade_nm" )
	private BigDecimal oralGrade;

	@Column( name = "isc_final_grade_nm" )
	private BigDecimal finalGrade;

	public InepSubscription( )
	{
	}

	public InepSubscriptionPK getId( )
	{
		if ( id == null ) {
			id = new InepSubscriptionPK( );
		}
		return id;
	}

	public void setId( InepSubscriptionPK id )
	{
		this.id = id;
	}

	public InepPackage getEvent( )
	{
		return event;
	}

	public void setEvent( InepPackage event )
	{
		this.event = event;
		if ( getEvent( ) != null ) {
			getId( ).set( getEvent( ) );
		}
	}

	@Override
	public int compareTo( InepSubscription o )
	{
		return getId( ).compareTo( o.getId( ) );
	}

	public int compareTo( InepSubscription object, Integer field )
	{
		switch ( field ) {
		case 0:
			return getId( ).getId( ).compareTo( object.getId( ).getId( ) );
		default:
			return 0;
		}
	}

	@Override
	public boolean equals( Object obj )
	{
		return getId( ).equals( ( (InepSubscription) obj ).getId( ) );
	}

	public String getField( Integer field )
	{
		switch ( field ) {
		case 0:
			return getId( ).getId( ).toString( );
		default:
			return "";
		}
	}

	public BigDecimal getWrittenGrade( )
	{
		return writtenGrade;
	}

	public void setWrittenGrade( BigDecimal writtenGrade )
	{
		this.writtenGrade = writtenGrade;
		if ( getOralGrade( ) != null ) {
			BigDecimal result = oralGrade.subtract( getWrittenGrade( ) );
			result = result.abs( );
			if ( result.doubleValue( ) < 2 ) {
				double grade = oralGrade.doubleValue( ) + getWrittenGrade( ).doubleValue( );
				grade /= 2.0;
				setFinalGrade( new BigDecimal( grade ) );
			}
		}
	}

	public BigDecimal getOralGrade( )
	{
		return oralGrade;
	}

	public void setOralGrade( BigDecimal oralGrade )
	{
		this.oralGrade = oralGrade;
		if ( getWrittenGrade( ) != null ) {
			BigDecimal result = oralGrade.subtract( getWrittenGrade( ) );
			result = result.abs( );
			if ( result.doubleValue( ) < 2 ) {
				double grade = oralGrade.doubleValue( ) + getWrittenGrade( ).doubleValue( );
				grade /= 2.0;
				setFinalGrade( new BigDecimal( grade ) );
			}
		}
	}

	public BigDecimal getFinalGrade( )
	{
		return finalGrade;
	}

	public void setFinalGrade( BigDecimal finalGrade )
	{
		this.finalGrade = finalGrade;
	}

}