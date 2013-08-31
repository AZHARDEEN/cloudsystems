package br.com.mcampos.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleTable;

/**
 * The persistent class for the contact_type database table.
 * 
 */
@Entity
@Table(name="contact_type")
public class ContactType extends SimpleTable<ContactType>
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cct_id_in")
	private Integer id;

	@Column(name="cct_allow_duplicate_bt")
	private Boolean duplicate;

	@Column(name="cct_description_ch")
	private String description;

	@Column(name="cct_mask_ch")
	private String mask;

	public ContactType() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer cctIdIn) {
		this.id = cctIdIn;
	}

	public Boolean getDuplicate() {
		return this.duplicate;
	}

	public void setDuplicate(Boolean cctAllowDuplicateBt) {
		this.duplicate = cctAllowDuplicateBt;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String cctDescriptionCh) {
		this.description = cctDescriptionCh;
	}

	public String getMask() {
		return this.mask;
	}

	public void setMask(String cctMaskCh) {
		this.mask = cctMaskCh;
	}

}