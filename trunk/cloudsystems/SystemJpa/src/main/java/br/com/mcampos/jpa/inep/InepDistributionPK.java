package br.com.mcampos.jpa.inep;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the inep_distribution database table.
 * 
 */
@Embeddable
public class InepDistributionPK implements Serializable
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "usr_id_in" )
	private Integer companyId;

	@Column( name = "pct_id_in" )
	private Integer eventId;

	@Column( name = "isc_id_ch" )
	private String subscriptionId;

	@Column( name = "tsk_id_in" )
	private Integer taskId;

	@Column( name = "col_seq_in" )
	private Integer collaboratorId;

	public InepDistributionPK( )
	{
	}

	public InepDistributionPK( InepRevisor r, InepTest t )
	{
		this.set( r, t );
	}

	public void set( InepRevisor r, InepTest t )
	{
		this.set( r );
		this.set( t );
	}

	public void set( InepRevisor c )
	{
		if ( c != null )
		{
			this.setCompanyId( c.getId( ).getCompanyId( ) );
			this.setEventId( c.getId( ).getEventId( ) );
			this.setCollaboratorId( c.getId( ).getSequence( ) );
			this.setTaskId( c.getTaskId( ) );
		}
	}

	public void set( InepTest c )
	{
		if ( c != null ) {
			this.setCompanyId( c.getId( ).getCompanyId( ) );
			this.setEventId( c.getId( ).getEventId( ) );
			this.setSubscriptionId( c.getId( ).getSubscriptionId( ) );
			this.setTaskId( c.getId( ).getTaskId( ) );
		}
	}

	public Integer getCompanyId( )
	{
		return this.companyId;
	}

	public void setCompanyId( Integer usrIdIn )
	{
		this.companyId = usrIdIn;
	}

	public Integer getEventId( )
	{
		return this.eventId;
	}

	public void setEventId( Integer pctIdIn )
	{
		this.eventId = pctIdIn;
	}

	public String getSubscriptionId( )
	{
		return this.subscriptionId;
	}

	public void setSubscriptionId( String iscIdCh )
	{
		this.subscriptionId = iscIdCh;
	}

	public Integer getTaskId( )
	{
		return this.taskId;
	}

	public void setTaskId( Integer tskIdIn )
	{
		this.taskId = tskIdIn;
	}

	public Integer getCollaboratorId( )
	{
		return this.collaboratorId;
	}

	public void setCollaboratorId( Integer colSeqIn )
	{
		this.collaboratorId = colSeqIn;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof InepDistributionPK ) ) {
			return false;
		}
		InepDistributionPK castOther = (InepDistributionPK) other;
		return this.companyId.equals( castOther.companyId )
				&& this.eventId.equals( castOther.eventId )
				&& this.subscriptionId.equals( castOther.subscriptionId )
				&& this.taskId.equals( castOther.taskId )
				&& this.collaboratorId.equals( castOther.collaboratorId );

	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.getCompanyId( ).hashCode( );
		hash = hash * prime + this.getEventId( ).hashCode( );
		hash = hash * prime + this.getSubscriptionId( ).hashCode( );
		hash = hash * prime + this.getTaskId( ).hashCode( );
		hash = hash * prime + this.getCollaboratorId( ).hashCode( );

		return hash;
	}

	public int compareTo( InepDistributionPK id )
	{
		int nRet;

		nRet = this.getCompanyId( ).compareTo( id.getCompanyId( ) );
		if ( nRet == 0 ) {
			nRet = this.getEventId( ).compareTo( id.getEventId( ) );
		}
		if ( nRet == 0 ) {
			nRet = this.getSubscriptionId( ).compareTo( id.getSubscriptionId( ) );
		}
		if ( nRet == 0 ) {
			nRet = this.getTaskId( ).compareTo( id.getTaskId( ) );
		}
		if ( nRet == 0 ) {
			nRet = this.getCollaboratorId( ).compareTo( id.getCollaboratorId( ) );
		}
		return nRet;
	}
}