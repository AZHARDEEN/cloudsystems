package br.com.mcampos.ejb.inep.entity;

import java.io.Serializable;

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
@NamedQueries( { @NamedQuery( name = InepSubscription.getAllEventSubs, query = "select o from InepSubscription o where o.event = ?1" ) } )
public class InepSubscription implements Serializable, Comparable<InepSubscription>
{
	private static final long serialVersionUID = 1L;
	public static final String getAllEventSubs = "InepSubscription.getAllEventSubs";

	@EmbeddedId
	private InepSubscriptionPK id;

	@ManyToOne
	@JoinColumns( {
		@JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", updatable = false, insertable = false, nullable = false ),
		@JoinColumn( name = "pct_id_in", referencedColumnName = "pct_id_in", updatable = false, insertable = false, nullable = false ) } )
	private InepPackage event;

	public InepSubscription() {
	}

	public InepSubscriptionPK getId() {
		if ( this.id == null ) {
			this.id = new InepSubscriptionPK();
		}
		return this.id;
	}

	public void setId(InepSubscriptionPK id) {
		this.id = id;
	}

	public InepPackage getEvent( )
	{
		return this.event;
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
		return getId( ).equals( ( (InepTask) obj ).getId( ) );
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

}