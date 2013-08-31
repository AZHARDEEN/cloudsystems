package br.com.mcampos.entity.fdigital;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the pgc_pen_page database table.
 * 
 */
@Embeddable
public class PgcPenPagePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="pgc_id_in")
	private Integer pgcIdIn;

	@Column(name="frm_id_in")
	private Integer frmIdIn;

	@Column(name="apg_id_ch")
	private String apgIdCh;

	@Column(name="pad_id_in")
	private Integer padIdIn;

	@Column(name="pdp_seq_in")
	private Integer pdpSeqIn;

    public PgcPenPagePK() {
    }
	public Integer getPgcIdIn() {
		return this.pgcIdIn;
	}
	public void setPgcIdIn(Integer pgcIdIn) {
		this.pgcIdIn = pgcIdIn;
	}
	public Integer getFrmIdIn() {
		return this.frmIdIn;
	}
	public void setFrmIdIn(Integer frmIdIn) {
		this.frmIdIn = frmIdIn;
	}
	public String getApgIdCh() {
		return this.apgIdCh;
	}
	public void setApgIdCh(String apgIdCh) {
		this.apgIdCh = apgIdCh;
	}
	public Integer getPadIdIn() {
		return this.padIdIn;
	}
	public void setPadIdIn(Integer padIdIn) {
		this.padIdIn = padIdIn;
	}
	public Integer getPdpSeqIn() {
		return this.pdpSeqIn;
	}
	public void setPdpSeqIn(Integer pdpSeqIn) {
		this.pdpSeqIn = pdpSeqIn;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PgcPenPagePK)) {
			return false;
		}
		PgcPenPagePK castOther = (PgcPenPagePK)other;
		return 
			this.pgcIdIn.equals(castOther.pgcIdIn)
			&& this.frmIdIn.equals(castOther.frmIdIn)
			&& this.apgIdCh.equals(castOther.apgIdCh)
			&& this.padIdIn.equals(castOther.padIdIn)
			&& this.pdpSeqIn.equals(castOther.pdpSeqIn);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.pgcIdIn.hashCode();
		hash = hash * prime + this.frmIdIn.hashCode();
		hash = hash * prime + this.apgIdCh.hashCode();
		hash = hash * prime + this.padIdIn.hashCode();
		hash = hash * prime + this.pdpSeqIn.hashCode();
		
		return hash;
    }
}