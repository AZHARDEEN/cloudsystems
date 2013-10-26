package br.com.mcampos.ejb.cloudsystem.anoto.pen.user.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;

@Entity
@NamedQueries( {
		@NamedQuery( name = AnotoPenUser.getAll, query = "select o from AnotoPenUser o" ),
		@NamedQuery( name = AnotoPenUser.getCurrentUser, query = "select o from AnotoPenUser o where o.pen.id = ?1 and o.toDate is null" ),
		@NamedQuery( name = AnotoPenUser.nextSequence, query = "select max (o.sequence) from AnotoPenUser o where o.pen = ?1" ),
		@NamedQuery(
				name = AnotoPenUser.getUser,
				query = "select o from AnotoPenUser o where o.pen.id = ?1 AND ?2 BETWEEN o.fromDate and coalesce ( o.toDate, CURRENT_TIMESTAMP )" ),
		@NamedQuery(
				name = AnotoPenUser.getPenByUser,
				query = "select o from AnotoPenUser o where o.person.id = ?1 AND ?2 BETWEEN o.fromDate and coalesce ( o.toDate, CURRENT_TIMESTAMP )" )
} )
@Table( name = "anoto_pen_user" )
@IdClass( AnotoPenUserPK.class )
public class AnotoPenUser implements Serializable
{
	public static final String getAll = "AnotoPenUser.findAll";
	public static final String getCurrentUser = "AnotoPenUser.currentPenUser";
	public static final String getUser = "AnotoPenUser.penenUser";
	public static final String getPenByUser = "AnotoPenUser.getPenByUser";
	public static final String nextSequence = "AnotoPenUser.nextSequence";

	private static final long serialVersionUID = 8392633043435076273L;

	@Column( name = "apu_from_dt", nullable = false )
	@Temporal( value = TemporalType.TIMESTAMP )
	private Date fromDate;

	@Id
	@Column( name = "apu_seq_in", nullable = false )
	private Integer sequence;

	@Column( name = "apu_to_dt", nullable = true )
	@Temporal( value = TemporalType.TIMESTAMP )
	private Date toDate;

	@Id
	@Column( name = "pen_id_ch", nullable = false )
	private String penId;

	@Column( name = "usr_id_in", nullable = false )
	private Integer personId;

	@ManyToOne( optional = false )
	@JoinColumn( name = "usr_id_in", columnDefinition = "Integer", nullable = false, insertable = false, updatable = false )
	private Person person;

	@ManyToOne( optional = false )
	@JoinColumn( name = "pen_id_ch", referencedColumnName = "pen_id_ch", insertable = false, updatable = false )
	private AnotoPen pen;

	public AnotoPenUser( )
	{
	}

	public AnotoPenUser( AnotoPen pen, Person person )
	{
		this.setPen( pen );
		this.setPerson( person );
	}

	public Date getFromDate( )
	{
		return this.fromDate;
	}

	public void setFromDate( Date apu_from_dt )
	{
		this.fromDate = apu_from_dt;
	}

	public Integer getSequence( )
	{
		return this.sequence;
	}

	public void setSequence( Integer apu_seq_in )
	{
		this.sequence = apu_seq_in;
	}

	public Date getToDate( )
	{
		return this.toDate;
	}

	public void setToDate( Date apu_to_dt )
	{
		this.toDate = apu_to_dt;
	}

	public String getPenId( )
	{
		return this.penId;
	}

	public void setPenId( String pen_id_ch )
	{
		this.penId = pen_id_ch;
	}

	public Integer getPersonId( )
	{
		return this.personId;
	}

	public void setPersonId( Integer usr_id_in )
	{
		this.personId = usr_id_in;
	}

	public void setPerson( Person person )
	{
		this.person = person;
		this.setPersonId( person != null ? person.getId( ) : null );
	}

	public Person getPerson( )
	{
		return this.person;
	}

	public void setPen( AnotoPen pen )
	{
		this.pen = pen;
		this.setPenId( pen != null ? pen.getId( ) : null );
	}

	public AnotoPen getPen( )
	{
		return this.pen;
	}
}
