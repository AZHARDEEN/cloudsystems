package br.com.mcampos.jpa.fdigital;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the pgc_processed_image database table.
 * 
 */
@Embeddable
public class PgcProcessedImagePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="med_id_in", unique=true, nullable=false)
	private Integer medIdIn;

	@Column(name="pgc_id_in", unique=true, nullable=false)
	private Integer pgcIdIn;

	@Column(name="ppg_book_id", unique=true, nullable=false)
	private Integer ppgBookId;

	@Column(name="ppg_page_id", unique=true, nullable=false)
	private Integer ppgPageId;

    public PgcProcessedImagePK() {
    }
	public Integer getMedIdIn() {
		return this.medIdIn;
	}
	public void setMedIdIn(Integer medIdIn) {
		this.medIdIn = medIdIn;
	}
	public Integer getPgcIdIn() {
		return this.pgcIdIn;
	}
	public void setPgcIdIn(Integer pgcIdIn) {
		this.pgcIdIn = pgcIdIn;
	}
	public Integer getPpgBookId() {
		return this.ppgBookId;
	}
	public void setPpgBookId(Integer ppgBookId) {
		this.ppgBookId = ppgBookId;
	}
	public Integer getPpgPageId() {
		return this.ppgPageId;
	}
	public void setPpgPageId(Integer ppgPageId) {
		this.ppgPageId = ppgPageId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PgcProcessedImagePK)) {
			return false;
		}
		PgcProcessedImagePK castOther = (PgcProcessedImagePK)other;
		return 
			this.medIdIn.equals(castOther.medIdIn)
			&& this.pgcIdIn.equals(castOther.pgcIdIn)
			&& this.ppgBookId.equals(castOther.ppgBookId)
			&& this.ppgPageId.equals(castOther.ppgPageId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.medIdIn.hashCode();
		hash = hash * prime + this.pgcIdIn.hashCode();
		hash = hash * prime + this.ppgBookId.hashCode();
		hash = hash * prime + this.ppgPageId.hashCode();
		
		return hash;
    }
}