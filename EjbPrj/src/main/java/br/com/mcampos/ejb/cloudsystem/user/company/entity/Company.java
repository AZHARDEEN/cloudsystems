package br.com.mcampos.ejb.cloudsystem.user.company.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.ejb.cloudsystem.user.Users;
import br.com.mcampos.ejb.cloudsystem.user.attribute.companytype.CompanyType;
import br.com.mcampos.ejb.cloudsystem.user.attribute.usertype.entity.entity.UserType;

@Entity
@NamedQueries( { @NamedQuery( name = Company.getAll, query = "select o from Company o" ) } )
@Table( name = "company" )
@DiscriminatorValue( "2" )
public class Company extends Users implements Serializable
{

	public static final Integer userTypeIdentification = 2;
	public static final String getAll = "Company.findAll";

	@ManyToOne( optional = false )
	@JoinColumn( name = "ctp_id_in", nullable = false, referencedColumnName = "ctp_id_in", columnDefinition = "Integer" )
	private CompanyType companyType;

	@Column( name = "usr_isento_ie_bt" )
	private Boolean isentoInscricaoEstadual;

	@Column( name = "usr_isento_im_bt" )
	private Boolean isentoInscricaoMunicipal;

	@Column( name = "usr_optante_simples_bt" )
	private Boolean optanteSimples;

	@ManyToOne
	@JoinColumn( name = "usr_holding_id" )
	private Company holding;

	/*
	@OneToMany( mappedBy = "holding", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY )
	private List<Company> companyList;
	*/

	public Company( )
	{
		setUserType( new UserType( userTypeIdentification ) );
	}

	public CompanyType getCompanyType( )
	{
		return companyType;
	}

	public void setCompanyType( CompanyType companyType )
	{
		this.companyType = companyType;
	}

	public Boolean getIsentoInscricaoEstadual( )
	{
		return isentoInscricaoEstadual;
	}

	public void setIsentoInscricaoEstadual( Boolean isento )
	{
		isentoInscricaoEstadual = isento;
	}

	public Boolean getIsentoInscricaoMunicipal( )
	{
		return isentoInscricaoMunicipal;
	}

	public void setIsentoInscricaoMunicipal( Boolean isento )
	{
		isentoInscricaoMunicipal = isento;
	}

	public Boolean getOptanteSimples( )
	{
		return optanteSimples;
	}

	public void setOptanteSimples( Boolean optanteSimples )
	{
		this.optanteSimples = optanteSimples;
	}

	public Company getHolding( )
	{
		return holding;
	}

	public void setHolding( Company company )
	{
		holding = company;
	}

	public List<Company> getCompanyList( )
	{
		return null; // companyList;
	}

	public void setCompanyList( List<Company> companyList )
	{
		// this.companyList = companyList;
	}

	public Company addCompany( Company company )
	{
		getCompanyList( ).add( company );
		company.setHolding( this );
		return company;
	}

	public Company removeCompany( Company company )
	{
		getCompanyList( ).remove( company );
		company.setHolding( null );
		return company;
	}
}
