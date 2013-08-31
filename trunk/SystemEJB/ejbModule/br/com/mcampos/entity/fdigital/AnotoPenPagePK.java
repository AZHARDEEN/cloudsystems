package br.com.mcampos.entity.fdigital;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the anoto_pen_page database table.
 * 
 */
@Embeddable
public class AnotoPenPagePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="frm_id_in", unique=true, nullable=false)
	private Integer formId;

	@Column(name="apg_id_ch", unique=true, nullable=false, length=16)
	private String pageId;

	@Column(name="pad_id_in", unique=true, nullable=false)
	private Integer padId;

	@Column(name="pdp_seq_in", unique=true, nullable=false)
	private Integer sequence;

    public AnotoPenPagePK() {
    }
	public Integer getFormId() {
		return this.formId;
	}
	public void setFormId(Integer frmIdIn) {
		this.formId = frmIdIn;
	}
	public String getPageId() {
		return this.pageId;
	}
	public void setPageId(String apgIdCh) {
		this.pageId = apgIdCh;
	}
	public Integer getPadId() {
		return this.padId;
	}
	public void setPadId(Integer padIdIn) {
		this.padId = padIdIn;
	}
	public Integer getSequence() {
		return this.sequence;
	}
	public void setSequence(Integer pdpSeqIn) {
		this.sequence = pdpSeqIn;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AnotoPenPagePK)) {
			return false;
		}
		AnotoPenPagePK castOther = (AnotoPenPagePK)other;
		return 
			this.formId.equals(castOther.formId)
			&& this.pageId.equals(castOther.pageId)
			&& this.padId.equals(castOther.padId)
			&& this.sequence.equals(castOther.sequence);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.formId.hashCode();
		hash = hash * prime + this.pageId.hashCode();
		hash = hash * prime + this.padId.hashCode();
		hash = hash * prime + this.sequence.hashCode();
		
		return hash;
    }
}