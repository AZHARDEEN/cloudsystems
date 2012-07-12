package br.com.mcampos.ejb.fdigital;

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
	private Integer pgcIdIn;

	@Column(name="pgp_id_in", unique=true, nullable=false)
	private Integer pgpIdIn;

	@Column(name="pgp_seq_in", unique=true, nullable=false)
	private Integer pgpSeqIn;

    public PgcPropertyPK() {
    }
	public Integer getPgcIdIn() {
		return this.pgcIdIn;
	}
	public void setPgcIdIn(Integer pgcIdIn) {
		this.pgcIdIn = pgcIdIn;
	}
	public Integer getPgpIdIn() {
		return this.pgpIdIn;
	}
	public void setPgpIdIn(Integer pgpIdIn) {
		this.pgpIdIn = pgpIdIn;
	}
	public Integer getPgpSeqIn() {
		return this.pgpSeqIn;
	}
	public void setPgpSeqIn(Integer pgpSeqIn) {
		this.pgpSeqIn = pgpSeqIn;
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
			this.pgcIdIn.equals(castOther.pgcIdIn)
			&& this.pgpIdIn.equals(castOther.pgpIdIn)
			&& this.pgpSeqIn.equals(castOther.pgpSeqIn);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.pgcIdIn.hashCode();
		hash = hash * prime + this.pgpIdIn.hashCode();
		hash = hash * prime + this.pgpSeqIn.hashCode();
		
		return hash;
    }
}