package br.com.mcampos.ejb.inep.packs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.com.mcampos.dto.Authentication;

/**
 * The primary key class for the inep_package database table.
 * 
 */
@Embeddable
public class InepPackagePK implements Serializable, Comparable<InepPackagePK>
{
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="usr_id_in")
	private Integer companyId;

	@Column(name="pct_id_in")
	private Integer id;

	public InepPackagePK() {
	}
	public Integer getCompanyId() {
		return this.companyId;
	}
	public void setCompanyId(Integer usrIdIn) {
		this.companyId = usrIdIn;
	}
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer pctIdIn) {
		this.id = pctIdIn;
	}

	public void setCompanyId( Authentication auth )
	{
		setCompanyId( auth.getCompanyId( ) );
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof InepPackagePK)) {
			return false;
		}
		InepPackagePK castOther = (InepPackagePK)other;
		return
				this.companyId.equals(castOther.companyId)
				&& this.id.equals(castOther.id);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.companyId.hashCode();
		hash = hash * prime + this.id.hashCode();

		return hash;
	}

	@Override
	public int compareTo( InepPackagePK o )
	{
		int nRet;

		nRet = getCompanyId( ).compareTo( o.getCompanyId( ) );
		if ( nRet == 0 ) {
			nRet = getId( ).compareTo( o.getId( ) );
		}
		return nRet;
	}
}