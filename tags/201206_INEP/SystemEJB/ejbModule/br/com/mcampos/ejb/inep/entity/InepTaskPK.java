package br.com.mcampos.ejb.inep.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * The primary key class for the inep_task database table.
 * 
 */
@Embeddable
public class InepTaskPK implements Serializable, Comparable<InepTaskPK>
{
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="usr_id_in")
	private Integer companyId;

	@Column(name="pct_id_in")
	private Integer eventId;

	@Column(name="tsk_id_in")
	private Integer id;

	public InepTaskPK() {
	}

	public void set( InepPackagePK key )
	{
		setCompanyId( key.getCompanyId( ) );
		setEventId( key.getId( ) );
	}

	public Integer getCompanyId() {
		if ( this.companyId == null ) {
			this.companyId = 0;
		}
		return this.companyId;
	}

	public void setCompanyId(Integer usrIdIn) {
		this.companyId = usrIdIn;
	}

	public Integer getEventId() {
		if ( this.eventId == null ) {
			this.eventId = 0;
		}
		return this.eventId;
	}

	public void setEventId(Integer pctIdIn) {
		this.eventId = pctIdIn;
	}

	public Integer getId() {
		return this.id;
	}
	public void setId(Integer tskIdIn) {
		this.id = tskIdIn;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof InepTaskPK)) {
			return false;
		}
		InepTaskPK castOther = (InepTaskPK)other;
		return
				this.companyId.equals(castOther.companyId)
				&& this.eventId.equals(castOther.eventId)
				&& this.id.equals(castOther.id);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.companyId.hashCode();
		hash = hash * prime + this.eventId.hashCode();
		hash = hash * prime + this.id.hashCode();

		return hash;
	}

	@Override
	public int compareTo( InepTaskPK o )
	{
		int nRet;

		nRet = getCompanyId( ).compareTo( o.getCompanyId( ) );
		if ( nRet == 0 ) {
			nRet = getEventId( ).compareTo( o.getEventId( ) );
		}
		if ( nRet == 0 ) {
			nRet = getId( ).compareTo( o.getId( ) );
		}
		return nRet;
	}
}