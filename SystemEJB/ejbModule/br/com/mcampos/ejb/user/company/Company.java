package br.com.mcampos.ejb.user.company;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.mcampos.ejb.user.Users;

/**
 * The persistent class for the company database table.
 * 
 */
@Entity
@Table(name="company")
@DiscriminatorValue( "2" )
public class Company extends Users implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Column(name="ctp_id_in", nullable=false)
	private Integer ctpIdIn;

	@Column(name="usr_isento_ie_bt")
	private Boolean ieIsento;

	@Column(name="usr_isento_im_bt")
	private Boolean imIsento;

	@Column(name="usr_optante_simples_bt")
	private Boolean optanteSimples;

	//bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name="usr_holding_id")
	private Company holding;

	//bi-directional many-to-one association to Company
	@OneToMany( mappedBy = "holding" )
	private List<Company> companies;

	public Company() {
	}

	public Integer getCtpIdIn() {
		return this.ctpIdIn;
	}

	public void setCtpIdIn(Integer ctpIdIn) {
		this.ctpIdIn = ctpIdIn;
	}

	public Boolean getIeIsento() {
		return this.ieIsento;
	}

	public void setIeIsento(Boolean usrIsentoIeBt) {
		this.ieIsento = usrIsentoIeBt;
	}

	public Boolean getImIsento() {
		return this.imIsento;
	}

	public void setImIsento(Boolean usrIsentoImBt) {
		this.imIsento = usrIsentoImBt;
	}

	public Boolean getOptanteSimples() {
		return this.optanteSimples;
	}

	public void setOptanteSimples(Boolean usrOptanteSimplesBt) {
		this.optanteSimples = usrOptanteSimplesBt;
	}

	public Company getHolding() {
		return this.holding;
	}

	public void setHolding(Company company) {
		this.holding = company;
	}

	public List<Company> getCompanies() {
		return this.companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

}