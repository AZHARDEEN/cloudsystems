package br.com.mcampos.jpa.user;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the company database table.
 * 
 */
@Entity
@Table( name = "company", schema = "public" )
@DiscriminatorValue( "2" )
@NamedQueries( {
		@NamedQuery( name = Company.getAllWithUploadJobEnabled, query = "select o from Company o where o.hasUploadJob = true" )
} )
public class Company extends Users implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final String getAllWithUploadJobEnabled = "Company.getAllWithUploadJobEnabled";

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

	@Column( name = "cpn_upload_job_bt", nullable = true, columnDefinition = "boolean" )
	private Boolean hasUploadJob;

	public Company( )
	{
	}

	public Boolean getIeIsento( )
	{
		return this.ieIsento;
	}

	public void setIeIsento( Boolean usrIsentoIeBt )
	{
		this.ieIsento = usrIsentoIeBt;
	}

	public Boolean getImIsento( )
	{
		return this.imIsento;
	}

	public void setImIsento( Boolean usrIsentoImBt )
	{
		this.imIsento = usrIsentoImBt;
	}

	public Boolean getOptanteSimples( )
	{
		return this.optanteSimples;
	}

	public void setOptanteSimples( Boolean usrOptanteSimplesBt )
	{
		this.optanteSimples = usrOptanteSimplesBt;
	}

	public Company getHolding( )
	{
		return this.holding;
	}

	public void setHolding( Company company )
	{
		this.holding = company;
	}

	public List<Company> getCompanies( )
	{
		return this.companies;
	}

	public void setCompanies( List<Company> companies )
	{
		this.companies = companies;
	}

	public CompanyType getType( )
	{
		return this.type;
	}

	public void setType( CompanyType type )
	{
		this.type = type;
	}

	public Boolean hasUploadJob( )
	{
		return this.hasUploadJob;
	}

	public void setHasUploadJob( Boolean hasUploadJob )
	{
		this.hasUploadJob = hasUploadJob;
	}

}