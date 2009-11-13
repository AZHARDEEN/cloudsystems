package br.com.mcampos.ejb.entity.user;

import br.com.mcampos.ejb.entity.user.attributes.CompanyType;
import br.com.mcampos.ejb.entity.user.attributes.UserType;

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

@Entity
@NamedQueries({
  @NamedQuery(name = "Company.findAll", query = "select o from Company o")
})
@Table( name = "\"company\"" )
@DiscriminatorValue( "2" )
public class Company extends Users implements Serializable
{

    public static final Integer userTypeIdentification = 2;

    private CompanyType companyType;
    private Boolean isentoInscricaoEstadual;
    private Boolean isentoInscricaoMunicipal;
    private Boolean optanteSimples;
    private Company company;
    private List<Company> companyList;

    public Company()
    {
        setUserType( new UserType ( userTypeIdentification ) );
    }

    @ManyToOne( optional = false )
    @JoinColumn( name = "ctp_id_in", nullable = false, referencedColumnName = "ctp_id_in", columnDefinition = "Integer" )
    public CompanyType getCompanyType()
    {
        return companyType;
    }

    public void setCompanyType( CompanyType companyType )
    {
        this.companyType = companyType;
    }



    @Column( name="usr_isento_ie_bt" )
    public Boolean getIsentoInscricaoEstadual()
    {
        return isentoInscricaoEstadual;
    }

    public void setIsentoInscricaoEstadual( Boolean isento )
    {
        this.isentoInscricaoEstadual = isento;
    }

    @Column( name="usr_isento_im_bt" )
    public Boolean getIsentoInscricaoMunicipal()
    {
        return isentoInscricaoMunicipal;
    }

    public void setIsentoInscricaoMunicipal( Boolean isento )
    {
        this.isentoInscricaoMunicipal = isento;
    }

    @Column( name="usr_optante_simples_bt" )
    public Boolean getOptanteSimples()
    {
        return optanteSimples;
    }

    public void setOptanteSimples( Boolean optanteSimples )
    {
        this.optanteSimples = optanteSimples;
    }

    @ManyToOne
    @JoinColumn( name = "usr_holding_id" )
    public Company getCompany()
    {
        return company;
    }

    public void setCompany( Company company )
    {
        this.company = company;
    }

    @OneToMany( mappedBy = "company" )
    public List<Company> getCompanyList()
    {
        return companyList;
    }

    public void setCompanyList( List<Company> companyList )
    {
        this.companyList = companyList;
    }

    public Company addCompany( Company company )
    {
        getCompanyList().add(company);
        company.setCompany(this);
        return company;
    }

    public Company removeCompany( Company company )
    {
        getCompanyList().remove(company);
        company.setCompany(null);
        return company;
    }
}
