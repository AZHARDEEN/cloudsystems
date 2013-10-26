package br.com.mcampos.jpa.fdigital;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the pgc_attachment database table.
 * 
 */
@Embeddable
public class PgcAttachmentPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="pgc_id_in", unique=true, nullable=false)
	private Integer pgcIdIn;

	@Column(name="med_id_in", unique=true, nullable=false)
	private Integer medIdIn;

    public PgcAttachmentPK() {
    }
	public Integer getPgcIdIn() {
		return this.pgcIdIn;
	}
	public void setPgcIdIn(Integer pgcIdIn) {
		this.pgcIdIn = pgcIdIn;
	}
	public Integer getMedIdIn() {
		return this.medIdIn;
	}
	public void setMedIdIn(Integer medIdIn) {
		this.medIdIn = medIdIn;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PgcAttachmentPK)) {
			return false;
		}
		PgcAttachmentPK castOther = (PgcAttachmentPK)other;
		return 
			this.pgcIdIn.equals(castOther.pgcIdIn)
			&& this.medIdIn.equals(castOther.medIdIn);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.pgcIdIn.hashCode();
		hash = hash * prime + this.medIdIn.hashCode();
		
		return hash;
    }
}