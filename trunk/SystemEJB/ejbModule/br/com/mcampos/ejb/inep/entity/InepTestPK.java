package br.com.mcampos.ejb.inep.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * The primary key class for the inep_test database table.
 * 
 */
@Embeddable
public class InepTestPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="usr_id_in")
	private Integer companyId;

	@Column(name="pct_id_in")
	private Integer eventId;

	@Column(name="isc_id_ch")
	private String subscriptionId;

	@Column(name="tsk_id_in")
	private Integer taskId;

	public InepTestPK() {
	}

	public InepTestPK( InepTask task, InepSubscription subscription )
	{
		set( task, subscription );
	}

	public void set ( InepTask task, InepSubscription subscription )
	{
		set( task );
		set( subscription );
	}

	public void set( InepTask task )
	{
		if ( task != null ) {
			setCompanyId( task.getId( ).getCompanyId( ) );
			setEventId( task.getId( ).getEventId( ) );
			setTaskId( task.getId( ).getId( ) );
		}
	}

	public void set( InepSubscription subscription )
	{
		if ( subscription != null ) {
			setSubscriptionId( subscription.getId( ).getId( ) );
			setCompanyId( subscription.getId( ).getCompanyId( ) );
			setEventId( subscription.getId( ).getEventId( ) );
		}
	}

	public Integer getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Integer usrIdIn) {
		this.companyId = usrIdIn;
	}

	public Integer getEventId() {
		return this.eventId;
	}

	public void setEventId(Integer pctIdIn) {
		this.eventId = pctIdIn;
	}

	public String getSubscriptionId() {
		return this.subscriptionId;
	}
	public void setSubscriptionId(String iscIdCh) {
		this.subscriptionId = iscIdCh;
	}

	public Integer getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Integer tskIdIn) {
		this.taskId = tskIdIn;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof InepTestPK)) {
			return false;
		}
		InepTestPK castOther = (InepTestPK)other;
		return getCompanyId( ).equals( castOther.getCompanyId( ) ) && getEventId( ).equals( castOther.getEventId( ) )
				&& getSubscriptionId( ).equals( castOther.getSubscriptionId( ) ) && getTaskId( ).equals( castOther.getTaskId( ) );

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + getCompanyId( ).hashCode( );
		hash = hash * prime + getEventId( ).hashCode( );
		hash = hash * prime + getSubscriptionId( ).hashCode( );
		hash = hash * prime + getTaskId( ).hashCode( );

		return hash;
	}
}