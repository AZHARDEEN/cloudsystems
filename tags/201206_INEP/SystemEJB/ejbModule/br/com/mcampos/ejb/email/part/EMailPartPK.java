package br.com.mcampos.ejb.email.part;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.com.mcampos.ejb.email.EMail;

/**
 * The primary key class for the e_mail_part database table.
 * 
 */
@Embeddable
public class EMailPartPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="eml_id_in")
	private Integer emailId;

	@Column(name="emp_seq_in")
	private Integer id;

	public EMailPartPK() {
	}

	public void set( EMail email )
	{
		if ( email != null ) {
			setEmailId( email.getId( ) );
		}
	}
	public Integer getEmailId() {
		return this.emailId;
	}
	public void setEmailId(Integer emlIdIn) {
		this.emailId = emlIdIn;
	}
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer empSeqIn) {
		this.id = empSeqIn;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EMailPartPK)) {
			return false;
		}
		EMailPartPK castOther = (EMailPartPK)other;
		return
				this.emailId.equals(castOther.emailId)
				&& this.id.equals(castOther.id);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.emailId.hashCode();
		hash = hash * prime + this.id.hashCode();

		return hash;
	}
}