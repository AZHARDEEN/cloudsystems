package br.com.mcampos.entity.user;

import javax.persistence.*;

import br.com.mcampos.ejb.core.SimpleTable;


/**
 * The persistent class for the company_type database table.
 * 
 */
@Entity
@Table(name="company_type")
public class CompanyType extends SimpleTable<CompanyType> {
	private static final long serialVersionUID = 1L;
	
	public static Integer defaultType = 224;

	@Id
	@Column(name="ctp_id_in")
	private Integer id;

	@Column(name="ctp_description_ch")
	private String description;

	public CompanyType() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer ctpIdIn) {
		this.id = ctpIdIn;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String ctpDescriptionCh) {
		this.description = ctpDescriptionCh;
	}
}