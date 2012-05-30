package br.com.mcampos.ejb.user.client;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the client database table.
 * 
 */
@Embeddable
public class ClientPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="usr_id_in", unique=true, nullable=false)
	private Integer companyId;

	@Column(name="cli_seq_in", unique=true, nullable=false)
	private Integer sequence;

    public ClientPK() {
    }
	public Integer getCompanyId() {
		return this.companyId;
	}
	public void setCompanyId(Integer usrIdIn) {
		this.companyId = usrIdIn;
	}
	public Integer getSequence() {
		return this.sequence;
	}
	public void setSequence(Integer cliSeqIn) {
		this.sequence = cliSeqIn;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ClientPK)) {
			return false;
		}
		ClientPK castOther = (ClientPK)other;
		return 
			this.companyId.equals(castOther.companyId)
			&& this.sequence.equals(castOther.sequence);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.companyId.hashCode();
		hash = hash * prime + this.sequence.hashCode();
		
		return hash;
    }
}