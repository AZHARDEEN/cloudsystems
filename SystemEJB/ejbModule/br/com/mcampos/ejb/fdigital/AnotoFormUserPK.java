package br.com.mcampos.ejb.fdigital;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the anoto_form_user database table.
 * 
 */
@Embeddable
public class AnotoFormUserPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="frm_id_in", unique=true, nullable=false)
	private Integer frmIdIn;

	@Column(name="afu_seq_in", unique=true, nullable=false)
	private Integer afuSeqIn;

    public AnotoFormUserPK() {
    }
	public Integer getFrmIdIn() {
		return this.frmIdIn;
	}
	public void setFrmIdIn(Integer frmIdIn) {
		this.frmIdIn = frmIdIn;
	}
	public Integer getAfuSeqIn() {
		return this.afuSeqIn;
	}
	public void setAfuSeqIn(Integer afuSeqIn) {
		this.afuSeqIn = afuSeqIn;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AnotoFormUserPK)) {
			return false;
		}
		AnotoFormUserPK castOther = (AnotoFormUserPK)other;
		return 
			this.frmIdIn.equals(castOther.frmIdIn)
			&& this.afuSeqIn.equals(castOther.afuSeqIn);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.frmIdIn.hashCode();
		hash = hash * prime + this.afuSeqIn.hashCode();
		
		return hash;
    }
}