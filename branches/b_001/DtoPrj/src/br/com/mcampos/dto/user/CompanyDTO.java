package br.com.mcampos.dto.user;

import br.com.mcampos.dto.user.attributes.CompanyTypeDTO;

public class CompanyDTO extends UserDTO
{
    /**
     *
     */
    private static final long serialVersionUID = -7380523821618482931L;
    private CompanyTypeDTO companyType;
    private Boolean isentoInscricaoEstadual;
    private Boolean isentoInscricaoMunicipal;


    public CompanyDTO()
    {
        super();
    }

    public void setCompanyType( CompanyTypeDTO companyType )
    {
        this.companyType = companyType;
    }

    public CompanyTypeDTO getCompanyType()
    {
        return companyType;
    }

    public void setIsentoInscricaoEstadual( Boolean isentoInscricaoEstadual )
    {
        this.isentoInscricaoEstadual = isentoInscricaoEstadual;
    }

    public Boolean getIsentoInscricaoEstadual()
    {
        return isentoInscricaoEstadual;
    }

    public void setIsentoInscricaoMunicipal( Boolean isentoInscricaoMunicipal )
    {
        this.isentoInscricaoMunicipal = isentoInscricaoMunicipal;
    }

    public Boolean getIsentoInscricaoMunicipal()
    {
        return isentoInscricaoMunicipal;
    }
}
