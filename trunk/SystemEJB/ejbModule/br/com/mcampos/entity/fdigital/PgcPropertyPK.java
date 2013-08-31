package br.com.mcampos.entity.fdigital;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the pgc_property database table.
 * 
 */
@Embeddable
public class PgcPropertyPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="pgc_id_in", unique=true, nullable=false)
	private Integer pgcId;

	@Column(name="pgp_id_in", unique=true, nullable=false)
	private Integer id;

	@Column(name="pgp_seq_in", unique=true, nullable=false)
	private Integer sequence;

    public PgcPropertyPK() {
    }
	public Integer getPgcId() {
		return this.pgcId;
	}
	public void setPgcId(Integer pgcIdIn) {
		this.pgcId = pgcIdIn;
	}
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer pgpIdIn) {
		this.id = pgpIdIn;
	}
	public Integer getSequence() {
		return this.sequence;
	}
	public void setSequence(Integer pgpSeqIn) {
		this.sequence = pgpSeqIn;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PgcPropertyPK)) {
			return false;
		}
		PgcPropertyPK castOther = (PgcPropertyPK)other;
		return 
			this.pgcId.equals(castOther.pgcId)
			&& this.id.equals(castOther.id)
			&& this.sequence.equals(castOther.sequence);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.pgcId.hashCode();
		hash = hash * prime + this.id.hashCode();
		hash = hash * prime + this.sequence.hashCode();
		
		return hash;
    }
}