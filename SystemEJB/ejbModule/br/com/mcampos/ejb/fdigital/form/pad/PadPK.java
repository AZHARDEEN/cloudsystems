package br.com.mcampos.ejb.fdigital.form.pad;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the pad database table.
 * 
 */
@Embeddable
public class PadPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="frm_id_in", unique=true, nullable=false)
	private Integer formId;

	@Column(name="pad_id_in", unique=true, nullable=false)
	private Integer id;

    public PadPK() {
    }
	public Integer getFormId() {
		return this.formId;
	}
	public void setFormId(Integer frmIdIn) {
		this.formId = frmIdIn;
	}
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer padIdIn) {
		this.id = padIdIn;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PadPK)) {
			return false;
		}
		PadPK castOther = (PadPK)other;
		return 
			this.formId.equals(castOther.formId)
			&& this.id.equals(castOther.id);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.formId.hashCode();
		hash = hash * prime + this.id.hashCode();
		
		return hash;
    }
}