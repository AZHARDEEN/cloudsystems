package br.com.mcampos.jpa.inep;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the inep_oral_distribution database table.
 * 
 */
@Embeddable
public class InepOralDistributionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="usr_id_in")
	private Integer companyId;

	@Column(name="col_seq_in")
	private Integer collaboratorId;

	@Column(name="pct_id_in")
	private Integer eventId;

	@Column(name="isc_id_ch")
	private String subscriptionId;

	public InepOralDistributionPK() {
	}
	public Integer getCompanyId() {
		return this.companyId;
	}
	public void setCompanyId(Integer usrIdIn) {
		this.companyId = usrIdIn;
	}
	public Integer getCollaboratorId() {
		return this.collaboratorId;
	}
	public void setCollaboratorId(Integer colSeqIn) {
		this.collaboratorId = colSeqIn;
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof InepOralDistributionPK)) {
			return false;
		}
		InepOralDistributionPK castOther = (InepOralDistributionPK)other;
		return 
			this.companyId.equals(castOther.companyId)
			&& this.collaboratorId.equals(castOther.collaboratorId)
			&& this.eventId.equals(castOther.eventId)
			&& this.subscriptionId.equals(castOther.subscriptionId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.companyId.hashCode();
		hash = hash * prime + this.collaboratorId.hashCode();
		hash = hash * prime + this.eventId.hashCode();
		hash = hash * prime + this.subscriptionId.hashCode();
		
		return hash;
	}
}