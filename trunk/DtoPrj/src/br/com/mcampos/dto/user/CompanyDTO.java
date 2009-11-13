package br.com.mcampos.dto.user;

import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.dto.user.attributes.CompanyTypeDTO;

public class CompanyDTO extends UserDTO
{
    private CompanyTypeDTO companyType;
    
    

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
}
