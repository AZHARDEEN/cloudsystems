package br.com.mcampos.entity.fdigital;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the pgc_page_attachment database table.
 * 
 */
@Embeddable
public class PgcPageAttachmentPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="pat_seq_in", unique=true, nullable=false)
	private Integer patSeqIn;

	@Column(name="pgc_id_in", unique=true, nullable=false)
	private Integer pgcIdIn;

	@Column(name="ppg_book_id", unique=true, nullable=false)
	private Integer ppgBookId;

	@Column(name="ppg_page_id", unique=true, nullable=false)
	private Integer ppgPageId;

    public PgcPageAttachmentPK() {
    }
	public Integer getPatSeqIn() {
		return this.patSeqIn;
	}
	public void setPatSeqIn(Integer patSeqIn) {
		this.patSeqIn = patSeqIn;
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
		if (!(other instanceof PgcPageAttachmentPK)) {
			return false;
		}
		PgcPageAttachmentPK castOther = (PgcPageAttachmentPK)other;
		return 
			this.patSeqIn.equals(castOther.patSeqIn)
			&& this.pgcIdIn.equals(castOther.pgcIdIn)
			&& this.ppgBookId.equals(castOther.ppgBookId)
			&& this.ppgPageId.equals(castOther.ppgPageId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.patSeqIn.hashCode();
		hash = hash * prime + this.pgcIdIn.hashCode();
		hash = hash * prime + this.ppgBookId.hashCode();
		hash = hash * prime + this.ppgPageId.hashCode();
		
		return hash;
    }
}