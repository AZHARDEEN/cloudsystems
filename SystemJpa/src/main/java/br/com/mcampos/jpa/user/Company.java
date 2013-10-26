package br.com.mcampos.jpa.user;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the company database table.
 * 
 */
@Entity
@Table( name = "company", schema = "public" )
@DiscriminatorValue( "2" )
public class Company extends Users implements Serializable
{
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn( name = "ctp_id_in", nullable = false )
	private CompanyType type;

	@Column( name = "usr_isento_ie_bt" )
	private Boolean ieIsento;

	@Column( name = "usr_isento_im_bt" )
	private Boolean imIsento;

	@Column( name = "usr_optante_simples_bt" )
	private Boolean optanteSimples;

	// bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn( name = "usr_holding_id" )
	private Company holding;

	// bi-directional many-to-one association to Company
	@OneToMany( mappedBy = "holding" )
	private List<Company> companies;

	public Company( )
	{
	}

	public Boolean getIeIsento( )
	{
		return ieIsento;
	}

	public void setIeIsento( Boolean usrIsentoIeBt )
	{
		ieIsento = usrIsentoIeBt;
	}

	public Boolean getImIsento( )
	{
		return imIsento;
	}

	public void setImIsento( Boolean usrIsentoImBt )
	{
		imIsento = usrIsentoImBt;
	}

	public Boolean getOptanteSimples( )
	{
		return optanteSimples;
	}

	public void setOptanteSimples( Boolean usrOptanteSimplesBt )
	{
		optanteSimples = usrOptanteSimplesBt;
	}

	public Company getHolding( )
	{
		return holding;
	}

	public void setHolding( Company company )
	{
		holding = company;
	}

	public List<Company> getCompanies( )
	{
		return companies;
	}

	public void setCompanies( List<Company> companies )
	{
		this.companies = companies;
	}

	public CompanyType getType( )
	{
		return type;
	}

	public void setType( CompanyType type )
	{
		this.type = type;
	}

}