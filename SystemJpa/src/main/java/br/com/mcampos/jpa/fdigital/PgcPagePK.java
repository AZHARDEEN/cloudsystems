package br.com.mcampos.jpa.fdigital;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the pgc_page database table.
 * 
 */
@Embeddable
public class PgcPagePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="pgc_id_in", unique=true, nullable=false)
	private Integer pgcId;

	@Column(name="ppg_book_id", unique=true, nullable=false)
	private Integer bookId;

	@Column(name="ppg_page_id", unique=true, nullable=false)
	private Integer id;

    public PgcPagePK() {
    }
	public Integer getPgcId() {
		return this.pgcId;
	}
	public void setPgcId(Integer pgcIdIn) {
		this.pgcId = pgcIdIn;
	}
	public Integer getBookId() {
		return this.bookId;
	}
	public void setBookId(Integer ppgBookId) {
		this.bookId = ppgBookId;
	}
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer ppgPageId) {
		this.id = ppgPageId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PgcPagePK)) {
			return false;
		}
		PgcPagePK castOther = (PgcPagePK)other;
		return 
			this.pgcId.equals(castOther.pgcId)
			&& this.bookId.equals(castOther.bookId)
			&& this.id.equals(castOther.id);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.pgcId.hashCode();
		hash = hash * prime + this.bookId.hashCode();
		hash = hash * prime + this.id.hashCode();
		
		return hash;
    }
}