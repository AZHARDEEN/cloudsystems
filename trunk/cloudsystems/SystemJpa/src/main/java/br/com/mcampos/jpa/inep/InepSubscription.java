package br.com.mcampos.jpa.inep;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.mcampos.jpa.user.Person;

/**
 * The persistent class for the inep_subscription database table.
 * 
 */
@Entity
@Table( name = "inep_subscription", schema = "inep" )
@NamedQueries( {
		@NamedQuery( name = InepSubscription.getAllEventSubs, query = "select o from InepSubscription o where o.event = ?1" ),
		@NamedQuery( name = InepSubscription.getBySubscriptionId, query = "select o from InepSubscription o where o.id.id = ?1" ),
		@NamedQuery(
				name = InepSubscription.getAllEventSubsById,
				query = "select o from InepSubscription o where o.event = ?1 and o.id.id like ?2" ),
		@NamedQuery(
				name = InepSubscription.getAllEventSubsByIdAndStation,
				query = "select o from InepSubscription o where o.event = ?1 and o.id.id like ?2 and o.stationId in ( ?3 )" )
} )
public class InepSubscription extends BaseInepEvent implements Serializable, Comparable<InepSubscription>
{
	private static final long serialVersionUID = 1L;
	public static final String getAllEventSubs = "InepSubscription.getAllEventSubs";
	public static final String getAllEventSubsById = "InepSubscription.getAllEventSubsById";
	public static final String getAllEventSubsByIdAndStation = "InepSubscription.getAllEventSubsByIdAndStation";
	public static final String getBySubscriptionId = "InepSubscription.getBySubscriptionId";

	@EmbeddedId
	private InepSubscriptionPK id;

	@Column( name = "isc_written_grade_nm" )
	private BigDecimal writtenGrade;

	@Column( name = "isc_oral_grade_nm" )
	private BigDecimal oralGrade;

	@Column( name = "isc_final_grade_nm" )
	private BigDecimal finalGrade;

	@Column( name = "isc_agreement_grade_nm" )
	private BigDecimal agreementGrade;

	// bi-directional many-to-one association to InepMedia
	@OneToMany( mappedBy = "subscription" )
	private List<InepMedia> medias;

	@Column( name = "cli_seq_in" )
	private Integer stationId;

	@Column( name = "isc_special_needs_ch", columnDefinition = "varchar", length = 128, nullable = true )
	private String specialNeeds;

	@Column( name = "isc_citizenship_ch", columnDefinition = "varchar", length = 64, nullable = true )
	private String citizenship;

	@ManyToOne
	@JoinColumn( name = "isc_candidate_in", referencedColumnName = "usr_id_in", nullable = true )
	private Person person;

	@Column( name = "iss_id_in" )
	private Integer status;

	public InepSubscription( )
	{
	}

	@Override
	public InepSubscriptionPK getId( )
	{
		if ( this.id == null ) {
			this.id = new InepSubscriptionPK( );
		}
		return this.id;
	}

	public void setId( InepSubscriptionPK id )
	{
		this.id = id;
	}

	@Override
	public int compareTo( InepSubscription o )
	{
		return this.getId( ).compareTo( o.getId( ) );
	}

	public int compareTo( InepSubscription object, Integer field )
	{
		switch ( field ) {
		case 0:
			return this.getId( ).getId( ).compareTo( object.getId( ).getId( ) );
		default:
			return 0;
		}
	}

	@Override
	public boolean equals( Object obj )
	{
		return this.getId( ).equals( ( (InepSubscription) obj ).getId( ) );
	}

	public String getField( Integer field )
	{
		switch ( field ) {
		case 0:
			return this.getId( ).getId( ).toString( );
		default:
			return "";
		}
	}

	public BigDecimal getWrittenGrade( )
	{
		return this.writtenGrade;
	}

	public void setWrittenGrade( BigDecimal writtenGrade )
	{
		this.writtenGrade = writtenGrade;
	}

	public BigDecimal getOralGrade( )
	{
		return this.oralGrade;
	}

	public void setOralGrade( BigDecimal oralGrade )
	{
		this.oralGrade = oralGrade;
	}

	public BigDecimal getFinalGrade( )
	{
		return this.finalGrade;
	}

	public void setFinalGrade( BigDecimal finalGrade )
	{
		this.finalGrade = finalGrade;
	}

	public List<InepMedia> getMedias( )
	{
		if ( this.medias == null ) {
			this.medias = new ArrayList<InepMedia>( );
		}
		return this.medias;
	}

	public void setMedias( List<InepMedia> inepMedias )
	{
		this.medias = inepMedias;
	}

	public void add( InepMedia media )
	{
		if ( media != null ) {
			if ( this.getMedias( ).contains( media ) == false ) {
				this.getMedias( ).add( media );
				media.setSubscription( this );
			}
		}
	}

	public BigDecimal getAgreementGrade( )
	{
		return this.agreementGrade;
	}

	public void setAgreementGrade( BigDecimal agreementGrade )
	{
		this.agreementGrade = agreementGrade;
	}

	public Integer getStationId( )
	{
		return this.stationId;
	}

	public void setStationId( Integer stationId )
	{
		this.stationId = stationId;
	}

	public String getSpecialNeeds( )
	{
		return this.specialNeeds;
	}

	public void setSpecialNeeds( String specialNeeds )
	{
		this.specialNeeds = specialNeeds;
	}

	public Person getPerson( )
	{
		return this.person;
	}

	public void setPerson( Person person )
	{
		this.person = person;
	}

	public String getCitizenship( )
	{
		return this.citizenship;
	}

	public void setCitizenship( String citizenship )
	{
		this.citizenship = citizenship;
	}

	public Integer getStatus( )
	{
		return this.status;
	}

	public void setStatus( Integer status )
	{
		this.status = status;
		if ( !status.equals( 2 ) ) {
			this.setOralGrade( null );
			this.setWrittenGrade( null );
		}
	}
}