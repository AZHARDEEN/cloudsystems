package br.com.mcampos.ejb.core.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the program_exception_trace database table.
 * 
 */
@Embeddable
public class ProgramExceptionTracePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="pex_id_in")
	private Integer exceptionId;

	@Column(name="pet_id_in")
	private Integer id;

    public ProgramExceptionTracePK() {
    }
	public Integer getExceptionId() {
		return this.exceptionId;
	}
	public void setExceptionId(Integer pexIdIn) {
		this.exceptionId = pexIdIn;
	}
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer petIdIn) {
		this.id = petIdIn;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProgramExceptionTracePK)) {
			return false;
		}
		ProgramExceptionTracePK castOther = (ProgramExceptionTracePK)other;
		return 
			this.exceptionId.equals(castOther.exceptionId)
			&& this.id.equals(castOther.id);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.exceptionId.hashCode();
		hash = hash * prime + this.id.hashCode();
		
		return hash;
    }
}