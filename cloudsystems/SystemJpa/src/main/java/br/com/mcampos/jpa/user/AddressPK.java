package br.com.mcampos.jpa.user;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the address database table.
 * 
 */
@Embeddable
public class AddressPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="usr_id_in", unique=true, nullable=false)
	private Integer userId;

	@Column(name="adt_id_in", unique=true, nullable=false)
	private Integer addressType;

    public AddressPK() {
    }
	public Integer getUserId() {
		return this.userId;
	}
	public void setUserId(Integer usrIdIn) {
		this.userId = usrIdIn;
	}
	public Integer getAddressType() {
		return this.addressType;
	}
	public void setAddressType(Integer adtIdIn) {
		this.addressType = adtIdIn;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AddressPK)) {
			return false;
		}
		AddressPK castOther = (AddressPK)other;
		return 
			this.userId.equals(castOther.userId)
			&& this.addressType.equals(castOther.addressType);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.addressType.hashCode();
		
		return hash;
    }
}