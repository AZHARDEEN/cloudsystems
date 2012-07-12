package br.com.mcampos.ejb.fdigital;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the anoto_page database table.
 * 
 */
@Embeddable
public class AnotoPagePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="frm_id_in", unique=true, nullable=false)
	private Integer frmIdIn;

	@Column(name="apg_id_ch", unique=true, nullable=false, length=16)
	private String apgIdCh;

	@Column(name="pad_id_in", unique=true, nullable=false)
	private Integer padIdIn;

    public AnotoPagePK() {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AnotoPagePK)) {
			return false;
		}
		AnotoPagePK castOther = (AnotoPagePK)other;
		return 
			this.frmIdIn.equals(castOther.frmIdIn)
			&& this.apgIdCh.equals(castOther.apgIdCh)
			&& this.padIdIn.equals(castOther.padIdIn);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.frmIdIn.hashCode();
		hash = hash * prime + this.apgIdCh.hashCode();
		hash = hash * prime + this.padIdIn.hashCode();
		
		return hash;
    }
}