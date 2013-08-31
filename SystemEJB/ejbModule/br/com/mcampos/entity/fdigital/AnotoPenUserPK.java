package br.com.mcampos.entity.fdigital;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the anoto_pen_user database table.
 * 
 */
@Embeddable
public class AnotoPenUserPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="pen_id_ch", unique=true, nullable=false, length=16)
	private String penIdCh;

	@Column(name="apu_seq_in", unique=true, nullable=false)
	private Integer apuSeqIn;

    public AnotoPenUserPK() {
    }
	public String getPenIdCh() {
		return this.penIdCh;
	}
	public void setPenIdCh(String penIdCh) {
		this.penIdCh = penIdCh;
	}
	public Integer getApuSeqIn() {
		return this.apuSeqIn;
	}
	public void setApuSeqIn(Integer apuSeqIn) {
		this.apuSeqIn = apuSeqIn;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AnotoPenUserPK)) {
			return false;
		}
		AnotoPenUserPK castOther = (AnotoPenUserPK)other;
		return 
			this.penIdCh.equals(castOther.penIdCh)
			&& this.apuSeqIn.equals(castOther.apuSeqIn);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.penIdCh.hashCode();
		hash = hash * prime + this.apuSeqIn.hashCode();
		
		return hash;
    }
}