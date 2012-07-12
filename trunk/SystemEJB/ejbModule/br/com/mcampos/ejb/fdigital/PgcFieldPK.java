package br.com.mcampos.ejb.fdigital;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the pgc_field database table.
 * 
 */
@Embeddable
public class PgcFieldPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ppg_book_id", unique=true, nullable=false)
	private Integer ppgBookId;

	@Column(name="ppg_page_id", unique=true, nullable=false)
	private Integer ppgPageId;

	@Column(name="pfl_name_ch", unique=true, nullable=false, length=128)
	private String pflNameCh;

	@Column(name="pgc_id_in", unique=true, nullable=false)
	private Integer pgcIdIn;

    public PgcFieldPK() {
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
	public String getPflNameCh() {
		return this.pflNameCh;
	}
	public void setPflNameCh(String pflNameCh) {
		this.pflNameCh = pflNameCh;
	}
	public Integer getPgcIdIn() {
		return this.pgcIdIn;
	}
	public void setPgcIdIn(Integer pgcIdIn) {
		this.pgcIdIn = pgcIdIn;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PgcFieldPK)) {
			return false;
		}
		PgcFieldPK castOther = (PgcFieldPK)other;
		return 
			this.ppgBookId.equals(castOther.ppgBookId)
			&& this.ppgPageId.equals(castOther.ppgPageId)
			&& this.pflNameCh.equals(castOther.pflNameCh)
			&& this.pgcIdIn.equals(castOther.pgcIdIn);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ppgBookId.hashCode();
		hash = hash * prime + this.ppgPageId.hashCode();
		hash = hash * prime + this.pflNameCh.hashCode();
		hash = hash * prime + this.pgcIdIn.hashCode();
		
		return hash;
    }
}